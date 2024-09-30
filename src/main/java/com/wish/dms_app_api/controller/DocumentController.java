package com.wish.dms_app_api.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.wish.dms_app_api.dto.DocumentRequestDto;
import com.wish.dms_app_api.dto.DocumentResponseDto;
import com.wish.dms_app_api.service.IDocumentService;
@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private IDocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<List<DocumentResponseDto>> createDocument(
            @ModelAttribute DocumentRequestDto documentRequestDto,
            @RequestParam("files") List<MultipartFile> files) {

        if (files.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            List<DocumentResponseDto> createdDocument = documentService.createDocument(documentRequestDto);
            return new ResponseEntity<>(createdDocument, HttpStatus.CREATED);
        } catch (Exception e) {
            //for exceptiom
        	e.printStackTrace();
        	System.out.println("NotFound");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



 
    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponseDto> getDocumentById(@PathVariable Long id) {
        try {
            DocumentResponseDto document = documentService.getDocumentById(id);
            return new ResponseEntity<>(document, HttpStatus.OK);
        } catch (Exception e) {

        	e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

  
    @GetMapping
    public ResponseEntity<List<DocumentResponseDto>> getAllDocuments() {
        List<DocumentResponseDto> documents = documentService.getAllDocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DocumentResponseDto>> getDocumentsByUserId(@PathVariable Long userId) {
        try {
            List
            <DocumentResponseDto> documents = documentService.getDocumentsByUserId(userId);
            return new ResponseEntity<>(documents, HttpStatus.OK);
        } catch (Exception e) {
//            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/type/{extension}")
    public ResponseEntity<List<DocumentResponseDto>> getDocumentsByType(@PathVariable String extension) {
        List<DocumentResponseDto> documents = documentService.getDocumentsByType(extension);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    
    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<DocumentResponseDto>> getDocumentsByTag(@PathVariable String tagName) {
        List<DocumentResponseDto> documents = documentService.getDocumentsByTagName(tagName);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }
    
    
    @GetMapping("/by-current-date")
    public ResponseEntity<List<DocumentResponseDto>> getDocumentByCurrentDate() {
        List<DocumentResponseDto> documents = documentService.getDocumentByCurrentDate();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/userToken")
    public ResponseEntity<List<DocumentResponseDto>> getDocumentsByUser() {
        try {
            List<DocumentResponseDto> documents = documentService.getDocumentsByUser();
            return new ResponseEntity<>(documents, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}