package com.datamanagerapi.datamanagerapi.Responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LoginResponse
{
    private String firstName,lastName,emailAddress,roleCode,institutionId;
}
