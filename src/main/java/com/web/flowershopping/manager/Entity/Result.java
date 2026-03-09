package com.web.flowershopping.manager.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    Object data;
	String msg;
	int status;
	String token;
    public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getToken(){
		return token;
	}
	public void setToken(String token){
		this.token = token;
	}
    @Override
	public String toString() {
		return "Resultmsg [data=" + data + ", msg=" + msg + ", status="
				+ status + ", token="+token+"]";
	}
}
