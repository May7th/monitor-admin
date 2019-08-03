package com.zy.admin.apimonitor.job;


import com.zy.admin.apimonitor.http.HttpRequestHandler;
import com.zy.admin.apimonitor.utils.BeanProvider;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;


/**
 * @program: api-monitor
 * @description:
 * @author: changzhen
 * @create: 2019-06-04 11:08
 **/

@Slf4j
@DisallowConcurrentExecution
public class HttpMonitorJob implements Job {

    public static final String APPLICATION_INSTANCE_ID = "instanceId";

    private transient HttpRequestHandler httpRequestHandler = BeanProvider.getBean(HttpRequestHandler.class);



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

        Long id = jobExecutionContext.getMergedJobDataMap().getLong(APPLICATION_INSTANCE_ID);

        httpRequestHandler.doHandler(id);

    }
}
