package com.web.flowershopping.manager.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachedFIlePhoto {

    private Integer attachedFileId;
    private String attachedFilePath;
    private LocalDateTime entryDate;
    private LocalDateTime updateDate;

    public Integer getAttachedFileId() {
        return attachedFileId;
    }

    public void setAttachedFileId(Integer attachedFileId) {
        this.attachedFileId = attachedFileId;
    }

    public String getAttachedFilePath() {
        return attachedFilePath;
    }

    public void setAttachedFilePath(String attachedFilePath) {
        this.attachedFilePath = attachedFilePath;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "AttachedFile{" +
                "attachedFileId=" + attachedFileId +
                ", attachedFilePath='" + attachedFilePath + '\'' +
                ", entryDate=" + entryDate +
                ", updateDate=" + updateDate +
                '}';
    }
}