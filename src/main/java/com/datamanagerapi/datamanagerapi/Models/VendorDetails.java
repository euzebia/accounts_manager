package com.datamanagerapi.datamanagerapi.Models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@ToString
@Entity
public class VendorDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //private int vendor_id;
    private int account_id;
    private String first_name,last_name,user_name,password,email_address;
    private String creation_date;
    private int  institution_id,role_id;
    private String institution_name,institution_type, role_name,role_code;
}
