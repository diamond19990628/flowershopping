package com.web.flowershopping.manager.Entity;

public class Status {
    private int status_id;
    private String status_name;
    public void setStatusId(int status_id){
        this.status_id = status_id;
    }
    public int getStatusId(){
        return status_id;
    }
    public void setStatusName(String status_name){
        this.status_name = status_name;
    }
    public String getStatusName(){
        return status_name;
    }
    @Override
    public String toString(){
        return "Status [status_id=" + status_id + ", status_name=" + status_name + "]";
    }
}
