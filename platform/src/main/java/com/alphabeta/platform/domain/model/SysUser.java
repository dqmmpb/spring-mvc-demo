package com.alphabeta.platform.domain.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_user")
public class SysUser implements Serializable {
    @Id
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐值
     */
    private String salt;

    /**
     * 姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门
     */
    private String department;

    /**
     * 禁用状态：1启用，2禁用，默认1
     */
    @Column(name = "disable_status")
    private Integer disableStatus;

    /**
     * A-可用;X-不可用
     */
    private String state;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名
     *
     * @return phone - 用户名
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置用户名
     *
     * @param phone 用户名
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取密码盐值
     *
     * @return salt - 密码盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置密码盐值
     *
     * @param salt 密码盐值
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取部门
     *
     * @return department - 部门
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 设置部门
     *
     * @param department 部门
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 获取禁用状态：1启用，2禁用，默认1
     *
     * @return disable_status - 禁用状态：1启用，2禁用，默认1
     */
    public Integer getDisableStatus() {
        return disableStatus;
    }

    /**
     * 设置禁用状态：1启用，2禁用，默认1
     *
     * @param disableStatus 禁用状态：1启用，2禁用，默认1
     */
    public void setDisableStatus(Integer disableStatus) {
        this.disableStatus = disableStatus;
    }

    /**
     * 获取A-可用;X-不可用
     *
     * @return state - A-可用;X-不可用
     */
    public String getState() {
        return state;
    }

    /**
     * 设置A-可用;X-不可用
     *
     * @param state A-可用;X-不可用
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取最后登录时间
     *
     * @return last_login_time - 最后登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最后登录时间
     *
     * @param lastLoginTime 最后登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
