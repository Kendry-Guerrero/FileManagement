package com.Ken.filemanagement.file;

import com.Ken.filemanagement.storage.StorageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class FileService {
    private final StorageService storage;
    private final UploadValidator validator;
    private final FileEntryRepository repo;


    public FileService(StorageService storage, UploadValidator validator, FileEntryRepository repo) {
        this.storage = storage;
        this.validator = validator;
        this.repo = repo;
    }

    public FileEntry upload(MultipartFile file) throws IOException{
        validator.validate(file);

        UUID id = UUID.randomUUID();
        String normalizedType = normalize(file.getContentType());
        String key = storage.save(file.getInputStream(), file.getSize(), normalizedType, id);

        FileEntry e = new FileEntry();
        e.setId(id);
        e.setOriginalName(safeName(file.getOriginalFilename()));
        e.setContentType(normalizedType);
        e.setSizeBytes(file.getSize());
        e.setStorageKey(key);
        e.setUploadedAt(Instant.now());
        e.setDeleted(false);
        return repo.save(e);

    }
    @Transactional
    public void softDelete(UUID id) throws IOException{
        FileEntry e = getOrThrow(id);

        if(!e.getDeleted()) return;
        String toKey = storage.buildKey(id, true); //bin path
        storage.move(e.getStorageKey(),toKey);

        e.setStorageKey(toKey);
        e.setDeleted(true);
        e.setUploadedAt(Instant.now());
        repo.save(e);
    }

    @Transactional
    public void restore(UUID id) throws IOException{
        FileEntry e = getOrThrow(id);
        if(!e.getDeleted()) return;

        String toKey = storage.buildKey(id,false); // files Path
        storage.move(e.getStorageKey(),toKey);

        e.setStorageKey(toKey);
        e.setDeleted(false);
        e.setDeletedAt(null);
        repo.save(e);;
    }

    @Transactional
    public void purgeExpired(Duration retention) throws IOException{
        Instant cutoff = Instant.now().minus(retention);
        //using paging to avoid loading huge sets in memory
        Pageable page = Pageable.ofSize(500);
        Page<FileEntry> batch = repo.findByDeletedTrueAndDeletedAtBefore(cutoff,page);
        while (!batch.isEmpty()){
            for(FileEntry e : batch){
                storage.delete(e.getStorageKey());
                repo.delete(e);
            }
            if (!batch.hasNext()) break;
            batch = repo.findByDeletedTrueAndDeletedAtBefore(cutoff, batch.nextPageable());
        }
    }

    @Transactional
    public Page<FileEntry> list (Pageable pageable){
        return repo.findByDeletedFalse(pageable);
    }

    @Transactional
    public Page<FileEntry> listBin(Pageable pageable){
        return repo.findByDeletedTrue(pageable);
    }

    @Transactional
    public Page<FileEntry> search(String q, Pageable pageable){
        return repo.findByDeletedFalseAndOriginalNameContainingIgnoreCase(q, pageable);
    }

    public FileEntry getOrThrow(UUID id){
        return repo.findById(id).orElseThrow(()-> new EntityNotFoundException("File not found"));
    }

    private String normalize(String contentType) {
        if(contentType == null) return "application/Octet-stream";
        int semicolon = contentType.indexOf(';');
        return(semicolon>=0?contentType.substring(0,semicolon) : contentType).trim().toLowerCase();

    }

    private String safeName(String name) {
        if (name == null || name.isBlank()) return "unnamed ";
        return name.replaceAll("[\\r\\n\\t]", " ").trim();
    }
}
