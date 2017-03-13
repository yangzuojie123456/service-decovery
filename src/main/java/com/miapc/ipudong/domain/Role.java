package com.miapc.ipudong.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.*;

/**
 * Created by wangwei on 2016/10/30.
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Where(clause = "deleted = 0")
public class Role extends BaseBean {

    private String name;
    private String description;
    @ManyToMany(cascade= CascadeType.REFRESH,fetch= FetchType.LAZY)
    private Set<Permission> permissions=new HashSet<Permission>();

    /**
     * Gets permissions.
     *
     * @return the permissions
     */
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Sets permissions.
     *
     * @param permissions the permissions
     */
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
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

    /**
     * Gets permissions code.
     *
     * @return the permissions code
     */
    public Collection<String> getPermissionsCode() {
        List permissionsCode = new ArrayList();
        if (permissions != null && permissions.size() > 0) {
            for (Permission permission : permissions) {
                permissionsCode.add(permission.getPermissionCode());
            }
        }
        return permissionsCode;
    }
}
