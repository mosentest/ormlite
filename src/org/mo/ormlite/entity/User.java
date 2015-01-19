package org.mo.ormlite.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moziqi on 2015/1/18 0018.
 */
@DatabaseTable(tableName = "tb_user")
public class User extends BaseColumn {
    @DatabaseField(index = true, columnName = "username", unique = true, canBeNull = false)
    private String username;
    @DatabaseField(canBeNull = false, columnName = "password")
    private String password;
    @DatabaseField(columnName = "login_time")
    private String loginTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", loginTime='").append(loginTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
