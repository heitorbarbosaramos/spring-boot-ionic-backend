package com.heitor.cursomc.exceptions;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StandartError implements Serializable {

    private Integer status;
    private String cause;
    private String msg;
    private Long timeStamp;
    private String data;

    public StandartError(){
    }

    public StandartError(Integer status, String cause, String msg) {
        this.status = status;
        this.cause = cause;
        this.msg = msg;
        this.timeStamp = System.currentTimeMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:ss");
        this.data = LocalDateTime.now().format(formatter);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
