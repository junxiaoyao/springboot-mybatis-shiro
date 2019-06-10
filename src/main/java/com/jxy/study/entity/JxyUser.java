package com.jxy.study.entity;

import javax.validation.constraints.NotNull;


/**
 * @Auther: jxy
 * @Date: 2019/3/11 10:28
 * @Description:
 */

public class JxyUser extends AbstractEntity {

    @NotNull
    private String userName;

    @NotNull
    private String userPass;

    private String userRole;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
