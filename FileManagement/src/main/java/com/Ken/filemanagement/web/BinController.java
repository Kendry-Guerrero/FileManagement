package com.Ken.filemanagement.web;

import com.Ken.filemanagement.file.FileEntry;
import com.Ken.filemanagement.file.FileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bin")
public class BinController {
    private final FileService fileService;


    public BinController( FileService fileService){
        this.fileService = fileService;
    }

    @GetMapping
    public Page<FileEntry> listBin(Pageable pageable){
        return fileService.listBin(pageable);
    }
}
