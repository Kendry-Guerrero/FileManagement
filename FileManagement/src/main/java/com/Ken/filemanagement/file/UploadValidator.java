package com.Ken.filemanagement.file;

import com.Ken.filemanagement.storage.StorageProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
public class UploadValidator {

    private final StorageProperties props;

    public UploadValidator(StorageProperties props) {
        this.props = props;
    }

    public void validate(MultipartFile file){

        if(file == null || file.isEmpty()){
            throw new IllegalArgumentException("File is required and must not be empty");

        }

        String contentType = normalizeContentType(file.getContentType());
        var allowList = props.getAllowedConteTypes();

        if(allowList != null && !allowList.isEmpty()){
            boolean allowed = allowList.stream().map(this::normalizeContentType).anyMatch(alloweedType -> Objects.equals(alloweedType,contentType));
            if(!allowed){
                throw new IllegalArgumentException("Unsupported content Type: !" + contentType);
            }
        }
    }

    private String normalizeContentType(String contentType){
        if(contentType == null || contentType.isBlank()) return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        int semicolon = contentType.indexOf(';');
        String base = semicolon >= 0 ?contentType.substring(0, semicolon) : contentType;
        return base.trim().toLowerCase();

    }
}
