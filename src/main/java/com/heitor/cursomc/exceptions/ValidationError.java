package com.heitor.cursomc.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandartError{

    private List<FieldMessage> list = new ArrayList<>();

    public ValidationError(List<FieldMessage> list) {
        this.list = list;
    }

    public ValidationError(Integer status, String cause, String msg) {
        super(status, cause, msg);
    }

    public List<FieldMessage> getErrors() {
        return list;
    }

    public void setList(String fieldName, String message ) {
        this.list.add(new FieldMessage(fieldName, message));
    }
}
