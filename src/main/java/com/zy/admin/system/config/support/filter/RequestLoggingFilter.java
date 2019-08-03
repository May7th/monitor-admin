package com.zy.admin.system.config.support.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

public class RequestLoggingFilter extends OncePerRequestFilter implements Ordered {
	
	private static Log LOG = LogFactory.getLog(RequestLoggingFilter.class);
	
	private boolean includeQueryString = false;
	private boolean includeClientInfo = false;
	private boolean includeHeaders = false;
	private boolean includePayload = false;
	private boolean includeResponse = false;
	private boolean logEnabled = true;
	private int maxPayloadLength = 1000;
	
	
	private volatile long currentRequestId = 0;
	
	private MultipartResolver multipartResolver = new StandardServletMultipartResolver();
	
	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

	public boolean isIncludeQueryString() {
		return includeQueryString;
	}

	public void setIncludeQueryString(boolean includeQueryString) {
		this.includeQueryString = includeQueryString;
	}

	public boolean isIncludeClientInfo() {
		return includeClientInfo;
	}

	public void setIncludeClientInfo(boolean includeClientInfo) {
		this.includeClientInfo = includeClientInfo;
	}

	public boolean isIncludeHeaders() {
		return includeHeaders;
	}

	public void setIncludeHeaders(boolean includeHeaders) {
		this.includeHeaders = includeHeaders;
	}

	public boolean isIncludePayload() {
		return includePayload;
	}

	public void setIncludePayload(boolean includePayload) {
		this.includePayload = includePayload;
	}
	
	
	
	public boolean isIncludeResponse() {
		return includeResponse;
	}

	public void setIncludeResponse(boolean includeResponse) {
		this.includeResponse = includeResponse;
	}

	protected boolean shouldLog(HttpServletRequest request) {
		return logEnabled;
	}
	
	

	public int getMaxPayloadLength() {
		return maxPayloadLength;
	}

	public void setMaxPayloadLength(int maxPayloadLength) {
		this.maxPayloadLength = maxPayloadLength;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		boolean isFirstRequest = !isAsyncDispatch(request);
		HttpServletRequest requestToUse = request;
		HttpServletResponse responseToUse = response;
		boolean shouldLog = shouldLog(requestToUse);
		long reqId = ++currentRequestId;
		long reqStartMilliseconds = System.currentTimeMillis(); 
		if(shouldLog) {
			if (isIncludePayload() && isFirstRequest && !multipartResolver.isMultipart(request) && !(request instanceof ContentCachingRequestWrapper)) {
				requestToUse = new ContentCachingRequestWrapper(request, getMaxPayloadLength());
			}
			if (isIncludeResponse() && isFirstRequest && !(response instanceof ContentCachingResponseWrapper)) {
				responseToUse = new ContentCachingResponseWrapper(response);
			}
		}
		try {
			filterChain.doFilter(requestToUse, responseToUse);
		}
		finally {
			if (shouldLog && !isAsyncStarted(requestToUse)) {
				logRequest(requestToUse, responseToUse,reqId,reqStartMilliseconds);
			}
			if (responseToUse instanceof ContentCachingResponseWrapper) {
				((ContentCachingResponseWrapper)responseToUse).copyBodyToResponse();
			}
		}
	}
	
	private void logRequest(HttpServletRequest requestToUse,HttpServletResponse response,long requestId,long requestStartMilliseconds) {
		LOG.info(new StringBuffer("[")
		.append(requestId).append("]").append(createMessage(requestToUse,response)).append(" done in ")
		.append(String.valueOf( System.currentTimeMillis()-requestStartMilliseconds))
		.append("ms.").toString());
	}

	private String createMessage(HttpServletRequest request,HttpServletResponse response) {
		StringBuilder msg = new StringBuilder();
		msg.append("Request [")
		.append(request.getMethod())
		.append(" uri=").append(request.getRequestURI());
		if (isIncludeQueryString()) {
			String queryString = request.getQueryString();
			if (queryString != null) {
				msg.append('?').append(queryString);
			}
		}
		if (isIncludeClientInfo()) {
			String client = request.getRemoteAddr();
			if (StringUtils.hasLength(client)) {
				msg.append(";client=").append(client);
			}
			HttpSession session = request.getSession(false);
			if (session != null) {
				msg.append(";session=").append(session.getId());
			}
			String user = request.getRemoteUser();
			if (user != null) {
				msg.append(";user=").append(user);
			}
		}
		if (isIncludeHeaders()) {
			msg.append(";headers=").append(new ServletServerHttpRequest(request).getHeaders());
		}
		if (isIncludePayload()) {
			ContentCachingRequestWrapper wrapper =
					WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
			if (wrapper != null) {
				byte[] buf = wrapper.getContentAsByteArray();
				if (buf.length > 0) {
					int length = Math.min(buf.length, getMaxPayloadLength());
					String payload;
					try {
						payload = new String(buf, 0, length, wrapper.getCharacterEncoding());
					}
					catch (UnsupportedEncodingException ex) {
						payload = "[unknown]";
					}
					msg.append(";payload=").append(payload);
				}else {
					msg.append(";payload=ZERO");
				}
			}else if(multipartResolver.isMultipart(request)){
				msg.append(";payload=multipart");
			}
		}

		msg.append("],Response[status=").append(response.getStatus());
		if (isIncludeResponse()) {
			ContentCachingResponseWrapper responseWraper =
					WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
			if (responseWraper != null) {
				if(isImageMedia(responseWraper.getContentType())) {
					msg.append(";content=image");
				}else {
					byte[] buf = responseWraper.getContentAsByteArray();
					if (buf.length > 0) {
						int length = Math.min(buf.length, getMaxPayloadLength());
						String payload;
						try {
							payload = new String(buf, 0, length, responseWraper.getCharacterEncoding());
						}
						catch (UnsupportedEncodingException ex) {
							payload = "[unknown]";
						}
						msg.append(";content=").append(payload);
					}else {
						msg.append(";content=ZERO");
					}
				}
				
			}
		}
		msg.append("]");
		return msg.toString();
	}
	
	private boolean isImageMedia(String contentType) {
		return (contentType != null && contentType.toLowerCase().startsWith("image/"));
	}

	public boolean isLogEnabled() {
		return logEnabled;
	}

	public void setLogEnabled(boolean logEnabled) {
		this.logEnabled = logEnabled;
	}
	
	

}
