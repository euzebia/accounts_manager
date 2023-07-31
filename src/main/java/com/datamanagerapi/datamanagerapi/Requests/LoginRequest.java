package com.datamanagerapi.datamanagerapi.Requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LoginRequest
{
    private String userName,password;
}
