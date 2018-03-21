package com.alphabeta.platform.base.domain.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user_session")
public class SysUserSession implements Serializable {
    @Id
    @Column(name = "user_session_id")
    private Long userSessionId;

    /**
     * 用户token
     */
    private String token;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * A-可用;X-不可用
     */
    private String state;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    /**
     * useragent
     */
    private String ua;

    private static final long serialVersionUID = 1L;

    /**
     * @return user_session_id
     */
    public Long getUserSessionId() {
        return userSessionId;
    }

    /**
     * @param userSessionId
     */
    public void setUserSessionId(Long userSessionId) {
        this.userSessionId = userSessionId;
    }

    /**
     * 获取用户token
     *
     * @return token - 用户token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置用户token
     *
     * @param token 用户token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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

    /**
     * @return last_update_time
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * 获取useragent
     *
     * @return ua - useragent
     */
    public String getUa() {
        return ua;
    }

    /**
     * 设置useragent
     *
     * @param ua useragent
     */
    public void setUa(String ua) {
        this.ua = ua;
    }
}