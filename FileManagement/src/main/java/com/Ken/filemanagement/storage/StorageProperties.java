package com.Ken.filemanagement.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "file.storage")
public class StorageProperties {
    private String basePath = "./storage";
    private int retentionDays = 30;
    private List<String> allowedContentTypes = List.of();






    //---- GETTERS ----
    public String getBasePath(){
        return basePath;
    }

    public int getRetentionDays(){
        return retentionDays;
    }

    public List<String> getAllowedConteTypes(){
        return allowedContentTypes;
    }

    // ----SETTERS ---
    public void setBasePAth(String basePath){
        this.basePath = basePath;
    }

    public void setRetentionDays(int retentionDays){
        this.retentionDays = retentionDays;;
    }


    public void setAllowedConteTypes(List<String> allowedConteTypes){
        this.allowedContentTypes = allowedConteTypes;
    }
}
