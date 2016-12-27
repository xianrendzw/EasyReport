package com.easytoolsoft.easyreport.scheduler;

public class Response {
    private final String status;
    private final String message;
    private final Object data;
    public Response(String status, String message,Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public String getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public Object getData() {
        return data;
    }

}
