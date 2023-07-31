package com.datamanagerapi.datamanagerapi.Requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BankUserRegistrationRequest {
    private String firstName,lastName,userName,password,email,branchId,address;
}
