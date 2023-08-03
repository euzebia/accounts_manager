package com.datamanagerapi.datamanagerapi.Responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Error
{
    private int code;
    private String type;
}
