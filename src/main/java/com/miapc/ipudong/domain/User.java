package com.miapc.ipudong.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miapc.ipudong.constant.Gender;
import com.miapc.ipudong.constant.UserStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wangwei on 2016/10/29.
 */
@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Where(clause = "deleted = 0")
public class User extends BaseBean {
    private String userName;
    private String idCard;
    private String address;
    private Gender gender = Gender.UNKOWN;
    private String email;
    private String mobile;
    @JsonIgnore
    private String password;
    private UserStatus status;
    @OneToMany(cascade=CascadeType.REFRESH,fetch= FetchType.EAGER)
    @JsonIgnore
    private List<Role> roles=new ArrayList<Role>();
    @OneToMany(cascade= CascadeType.REFRESH,fetch=FetchType.EAGER)
    @JsonIgnore
    private List<Permission> permissions=new ArrayList<Permission>();

    /**
     * Instantiates a new User.
     */
    public User() {
        super();
    }

    /**
     * Instantiates a new User.
     *
     * @param userName the user name
     * @param idCard   the id card
     * @param address  the address
     * @param gender   the gender
     * @param email    the email
     * @param mobile   the mobile
     * @param password the password
     * @param status   the status
     */
    public User(String userName, String idCard, String address, Gender gender, String email, String mobile, String password, UserStatus status) {
        this.userName = userName;
        this.idCard = idCard;
        this.address = address;
        this.gender = gender;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ", userName='" + userName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status=" + status +
                '}';
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets id card.
     *
     * @return the id card
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * Sets id card.
     *
     * @param idCard the id card
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets mobile.
     *
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Gets roles.
     *
     * @return the roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Sets roles.
     *
     * @param roles the roles
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * Gets permissions.
     *
     * @return the permissions
     */
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Sets permissions.
     *
     * @param permissions the permissions
     */
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Sets mobile.
     *
     * @param mobile the mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets roles name.
     *
     * @return the roles name
     */
    public Set<String> getRolesName() {
        Set rolesName = new HashSet();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                rolesName.add(role.getName());
            }
        }
        return rolesName;
    }
}
