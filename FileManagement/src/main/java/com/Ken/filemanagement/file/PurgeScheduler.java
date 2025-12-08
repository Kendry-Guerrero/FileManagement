package com.Ken.filemanagement.file;

import com.Ken.filemanagement.storage.StorageProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
public class PurgeScheduler {
    private final FileService fileService;
    private final StorageProperties storageProperties;

    public PurgeScheduler(FileService fileService, StorageProperties storageProperties){
        this.fileService = fileService;
        this.storageProperties = storageProperties;
    }



    @Scheduled(cron = " 0 0 3 * * *") // daily at 03:00
    public void purge() throws IOException {
        fileService.purgeExpired(Duration.ofDays(storageProperties.getRetentionDays()));
    }
}
