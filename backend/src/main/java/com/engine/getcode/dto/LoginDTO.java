package com.engine.getcode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by HaiND on 3/1/2020 12:22 AM.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 3609012100049495828L;

    @NotEmpty
    @JsonProperty("username")
    private String username;

    @NotEmpty
    @JsonProperty("password")
    private String password;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("LoginDTO = [")
                .append("username = ").append(username).append("]");
//                .append("password = ").append(password).append("]");

        return sb.toString();
    }
}
