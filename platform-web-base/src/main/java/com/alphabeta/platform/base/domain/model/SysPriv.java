package com.alphabeta.platform.base.domain.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_priv")
public class SysPriv implements Serializable {
    @Id
    @Column(name = "priv_id")
    private Long privId;

    /**
     * 父节点id
     */
    @Column(name = "parent_priv_id")
    private Long parentPrivId;

    /**
     * 权限code
     */
    @Column(name = "priv_code")
    private String privCode;

    /**
     * 权限名称
     */
    @Column(name = "priv_name")
    private String privName;

    /**
     * 0-目录;1-菜单;2-数据
     */
    private Integer type;

    /**
     * 菜单url路径
     */
    private String url;

    /**
     * 菜单path路径
     */
    private String path;

    /**
     * 权限描述
     */
    private String description;

    /**
     * A-可用;X-不可用
     */
    private String state;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return priv_id
     */
    public Long getPrivId() {
        return privId;
    }

    /**
     * @param privId
     */
    public void setPrivId(Long privId) {
        this.privId = privId;
    }

    /**
     * 获取父节点id
     *
     * @return parent_priv_id - 父节点id
     */
    public Long getParentPrivId() {
        return parentPrivId;
    }

    /**
     * 设置父节点id
     *
     * @param parentPrivId 父节点id
     */
    public void setParentPrivId(Long parentPrivId) {
        this.parentPrivId = parentPrivId;
    }

    /**
     * 获取权限code
     *
     * @return priv_code - 权限code
     */
    public String getPrivCode() {
        return privCode;
    }

    /**
     * 设置权限code
     *
     * @param privCode 权限code
     */
    public void setPrivCode(String privCode) {
        this.privCode = privCode;
    }

    /**
     * 获取权限名称
     *
     * @return priv_name - 权限名称
     */
    public String getPrivName() {
        return privName;
    }

    /**
     * 设置权限名称
     *
     * @param privName 权限名称
     */
    public void setPrivName(String privName) {
        this.privName = privName;
    }

    /**
     * 获取0-目录;1-菜单;2-数据
     *
     * @return type - 0-目录;1-菜单;2-数据
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置0-目录;1-菜单;2-数据
     *
     * @param type 0-目录;1-菜单;2-数据
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取菜单url路径
     *
     * @return url - 菜单url路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置菜单url路径
     *
     * @param url 菜单url路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取菜单path路径
     *
     * @return path - 菜单path路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置菜单path路径
     *
     * @param path 菜单path路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取权限描述
     *
     * @return description - 权限描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置权限描述
     *
     * @param description 权限描述
     */
    public void setDescription(String description) {
        this.description = description;
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
}