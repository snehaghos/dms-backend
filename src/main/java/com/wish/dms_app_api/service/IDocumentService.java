package com.wish.dms_app_api.service;


import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.wish.dms_app_api.dto.DocumentRequestDto;
import com.wish.dms_app_api.dto.DocumentResponseDto;

public interface IDocumentService {
//    DocumentResponseDto createDocument(DocumentRequestDto documentRequestDto);
    DocumentResponseDto getDocumentById(Long id);
    List<DocumentResponseDto> getAllDocuments();
    void deleteDocument(Long id);
    List<DocumentResponseDto> getDocumentsByUserId(Long userId);

	List<DocumentResponseDto> createDocument(DocumentRequestDto documentRequestDto) throws IOException;

    List<DocumentResponseDto> getDocumentsByType(String extension);  
    List<DocumentResponseDto> getDocumentsByTagName(String tagName);

	List<DocumentResponseDto> getDocumentByCurrentDate();

    List<DocumentResponseDto> getDocumentsByUser();
}
