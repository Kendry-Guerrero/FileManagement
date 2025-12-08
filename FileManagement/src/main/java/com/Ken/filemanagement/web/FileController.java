package com.Ken.filemanagement.web;

import com.Ken.filemanagement.file.FileEntry;
import com.Ken.filemanagement.file.FileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileEntry> upload (@RequestPart("file") MultipartFile file) throws IOException {
        FileEntry e = fileService.upload(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(e);
    }

    @GetMapping
    public Page<FileEntry> list(Pageable pageable){
        return fileService.list(pageable);
    }

    @GetMapping("/search")
    public Page<FileEntry> search(@RequestParam("q") String q, Pageable pageable){
        return fileService.search(q, pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws IOException{
        fileService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/restore")
    public ResponseEntity<FileEntry> restore(@PathVariable UUID id) throws IOException{
        fileService.restore(id);
        return ResponseEntity.ok(fileService.getOrThrow(id));
    }
}
