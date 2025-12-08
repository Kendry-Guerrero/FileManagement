package com.Ken.filemanagement.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface FileEntryRepository extends JpaRepository<FileEntry, UUID> {
    Page<FileEntry> findByDeletedFalse(Pageable pageable);

    Page<FileEntry> findByDeletedTrue(Pageable pageable);

    Page<FileEntry> findByDeletedFalseAndOriginalNameContainingIgnoreCase(String q, Pageable pageable);

    Page<FileEntry> findByDeletedTrueAndDeletedAtBefore(Instant cutoff, Pageable pageable);

}
