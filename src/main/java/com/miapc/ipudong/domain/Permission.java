package com.miapc.ipudong.domain;

/**
 * Created by wangwei on 2016/10/30.
 */

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The type Permission.
 */
@Entity
@Table(name = "permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Where(clause = "deleted = 0")
public class Permission extends BaseBean {
    private String name;
    private String description;
    private String appId;
    private String permissionCode;

    /**
     * Gets app id.
     *
     * @return the app id
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Sets app id.
     *
     * @param appId the app id
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Gets permission code.
     *
     * @return the permission code
     */
    public String getPermissionCode() {
        return permissionCode;
    }

    /**
     * Sets permission code.
     *
     * @param permissionCode the permission code
     */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
