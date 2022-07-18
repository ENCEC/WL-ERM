package com.share.support.util.validation;

import lombok.Data;

@Data
public class CustomBean {
    private Boolean isSuccess = true;
    private Object returnResult;
    private String setValue;
    private Class setType;
    private String errorMessage;
}
