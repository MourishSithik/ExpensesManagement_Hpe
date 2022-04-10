package com.expenses.model.dao;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
    int errorCode = 0;
    String errorMessage = "";

    public int getErroCode() {
        return errorCode;
    }

    public void setErroCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErroMessage() {
        return errorMessage;
    }

    public void setErroMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
