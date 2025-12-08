package com.Ken.filemanagement.file;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name="file_entries",
indexes = {
        @Index(name = "idx_file_uploaded_at", columnList = "uploadedAt"),
        @Index(name = "idx_file_size_bytes", columnList = "sizeBytes"),
        @Index(name = "idx_file_deleted", columnList = "deleted"),
        @Index(name = "idx_file_original_name", columnList= "originalName")
})
public class FileEntry {

    @Id
    @Column(columnDefinition = "uuid", nullable=false, updatable=false)
    public UUID id;

    @Column(name = "original_name", nullable= false, length = 255)
    public String originalName;

    @Column(name= "storage_key", nullable = false, unique = true, length = 300)
    public String contentType;

    @Column(name = "size_bytes, nullable = false")
    public  long sizeBytes;

    @Column(name = "storage_key", nullable = false)
    public String storageKey;

    @Column(name = "uploaded_at", nullable = false)
    public Instant uploadedAt;

    @Column(name = "deleted", nullable = false)
    public boolean deleted = false;

    @Column(name = "deleted_at")
    public Instant deletedAt;

    @Column(name = "checksum", length = 64)
    public String checksum;




    //Getters

    public UUID getId() {
        return id;
    }

    public String getOriginalName(){
        return originalName;
    }

    public String getContentType(){
        return contentType;
    }

    public long getSizeBytes(){
        return sizeBytes;
    }
    public String getStorageKey(){
        return storageKey;
    }

    public Instant getUploadedAt(){
        return uploadedAt;
    }

    public Boolean getDeleted(){
        return deleted;
    }

    public Instant GetDeletedAt(){
        return deletedAt;
    }

    public String GetChecksum(){
        return checksum;
    }


    //Setters

    public void setId(UUID id) {
        this.id= id;
    }

    public void setOriginalName(String originalName){
        this.originalName= originalName;
    }

    public void setContentType(String contentType){
        this.contentType= contentType;
    }

    public void setSizeBytes(long sizeBytes){
        this.sizeBytes= sizeBytes;
    }
    public void setStorageKey(String storageKey){
        this.storageKey= storageKey;
    }

    public void setUploadedAt(Instant uploadedAt){
        this.uploadedAt = uploadedAt;
    }

    public void  setDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public void setDeletedAt(Instant deletedAt){
        this.deletedAt = deletedAt;
    }

    public void setChecksum(String checksum){
        this.checksum = checksum;
    }




}
