package com.Ken.filemanagement.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface StorageService {
    String buildKey(UUID id, boolean bin);
    String save(InputStream in, long size, String contentType, UUID id) throws IOException;
    InputStream read(String storageKey) throws IOException;
    void delete(String storageKey) throws IOException;
    void move(String fromKey, String toKey) throws IOException;
}
