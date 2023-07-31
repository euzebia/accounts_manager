package com.datamanagerapi.datamanagerapi.Models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Account {
    private int account_id;
    private String first_name,last_name,user_name,password,email_address;
    private String creation_date;
}
