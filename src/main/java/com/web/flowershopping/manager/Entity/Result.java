package com.web.flowershopping.manager.Entity;

public class Result {
    Object data;
	String msg;
	int status;
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
    @Override
	public String toString() {
		return "Resultmsg [data=" + data + ", msg=" + msg + ", status="
				+ status + "]";
	}
}
