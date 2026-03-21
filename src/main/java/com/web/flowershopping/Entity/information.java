package com.web.flowershopping.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class information {
    private Integer informationId;
    private String informationTitle;
    private String informationContent;
    private LocalDateTime publishStartDate;
    private LocalDateTime publishEndDate;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    public Integer getInformationId() {
        return informationId;
    }
    public void setInformationId(Integer informationId) {
        this.informationId = informationId;
    }
    public String getInformationTitle() {
        return informationTitle;
    }
    public void setInformationTitle(String informationTitle) {
        this.informationTitle = informationTitle;
    }
    public String getInformationContent() {
        return informationContent;
    }
    public void setInformationContent(String informationContent) {
        this.informationContent = informationContent;
    }
    public LocalDateTime getPublishStartDate() {
        return publishStartDate;
    }
    public void setPublishStartDate(LocalDateTime publishStartDate) {
        this.publishStartDate = publishStartDate;
    }
    public LocalDateTime getPublishEndDate() {
        return publishEndDate;
    }
    public void setPublishEndDate(LocalDateTime publishEndDate) {
        this.publishEndDate = publishEndDate;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
    @Override
    public String toString() {
        return "information [informationId=" + informationId + ", informationTitle=" + informationTitle
                + ", informationContent=" + informationContent + ", publishStartDate=" + publishStartDate
                + ", publishEndDate=" + publishEndDate + ", createdDate=" + createdDate + ", updateDate=" + updateDate
                + "]";
    }
    
    
}
