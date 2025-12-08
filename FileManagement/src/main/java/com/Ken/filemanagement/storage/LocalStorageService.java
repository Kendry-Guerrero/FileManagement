package com.Ken.filemanagement.storage;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;


@Service
public class LocalStorageService implements StorageService{
    private final Path basePath;

    public LocalStorageService(StorageProperties storageProperties) {
        this.basePath = Paths.get(storageProperties.getBasePath()).toAbsolutePath().normalize();
    }

    @Override
    public String buildKey(java.util.UUID id, boolean bin){
        return (bin? "bin/" : "files/") + id.toString();
    }

    @Override
    public String save(InputStream in, long size, String contentType, java.util.UUID id) throws IOException{
        String key = buildKey(id, false);;
        Path target = basePath.resolve(key);
        Files.createDirectories(target.getParent());
        try(OutputStream out = Files.newOutputStream(target, StandardOpenOption.CREATE)){
            in.transferTo(out);
        }
        return key;
    }

    @Override
    public InputStream read(String storageKey) throws IOException {
        Path path = basePath.resolve(storageKey);
        if(!Files.exists(path)){
            throw new NoSuchFileException("Storage key not found: " + storageKey);
        }
        return Files.newInputStream(path,StandardOpenOption.READ);

    }
    @Override
    public void delete(String storageKey) throws IOException {
        Path path = basePath.resolve(storageKey);
        Files.deleteIfExists(path);
    }

    @Override
    public void move(String fromKey, String toKey) throws IOException{
        Path src = basePath.resolve(fromKey);
        Path dest = basePath.resolve(toKey);

        Files.createDirectories(dest.getParent());
        Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }
}
