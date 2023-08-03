package com.datamanagerapi.datamanagerapi.Responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ErrorFeedback
{
    private boolean success;
    private Error error;
}
