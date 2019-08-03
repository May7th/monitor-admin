package com.zy.admin.system.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@TableName("sys_user")
public class User implements UserDetails {
    private static final long serialVersionUID = 242146703513492331L;
    @TableId
    private Integer userId;

    private String username;

    private String password;

    private String nickName;

    private String avatar;

    private String sex;

    private String phone;

    private String email;

    private Integer emailVerified;

    private String personId;

    private Integer state;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private List<Role> roles;  //角色
    
    @TableField(exist = false)
    private Collection<? extends GrantedAuthority> authorities;  //权限
    

    public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Integer emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	
	
	@Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  //账户是否未过期
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.state != 1;  //账户是否未锁定
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  //凭证(密码)是否未过期
    }

    @Override
    public boolean isEnabled() {
        return true;  //用户是否启用
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return  this.authorities;
	}

	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof User) {
			return username.equals(((User) rhs).username);
		}
		return false;
	}

	/**
	 * Returns the hashcode of the {@code username}.
	 */
	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", nickName=" + nickName
				+ ", avatar=" + avatar + ", sex=" + sex + ", phone=" + phone + ", email=" + email + ", emailVerified="
				+ emailVerified + ", personId=" + personId + ", state=" + state + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", roles=" + roles + ", authorities=" + authorities + "]";
	}
	
	
	
	
	
}
