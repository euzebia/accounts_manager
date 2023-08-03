package com.datamanagerapi.datamanagerapi.Responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OnlineEmailValidationResponse
{
    private String status,message;
    private String email;
    private String did_you_mean;
    private String user;
    private String domain;
    private boolean format_valid;
    private boolean mx_found;
    private boolean smtp_check;
    private boolean catch_all;
    private boolean role;
    private boolean disposable;
    private boolean free;
    private double score;
}
