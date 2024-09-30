package com.wish.dms_app_api.service.implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import com.wish.dms_app_api.dto.DocumentRequestDto;
import com.wish.dms_app_api.dto.DocumentResponseDto;
import com.wish.dms_app_api.entity.Document;
import com.wish.dms_app_api.entity.DocumentTags;
import com.wish.dms_app_api.entity.User;
import com.wish.dms_app_api.repository.IDocumentRepository;
import com.wish.dms_app_api.repository.ITagRepository;
import com.wish.dms_app_api.repository.IUserRepository;
import com.wish.dms_app_api.security.UserSingleton;
import com.wish.dms_app_api.service.IDocumentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentService implements IDocumentService {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads";

    @Autowired
    private IDocumentRepository documentRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ITagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${document.path}")
    private String DOCUMENT_PATH;


    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public List<DocumentResponseDto> createDocument(DocumentRequestDto documentRequestDto) throws IOException {

        MultipartFile[] files = documentRequestDto.getFiles();
        Path uploadPath = Paths.get(UPLOAD_DIR);
        Files.createDirectories(uploadPath);


        List<DocumentResponseDto> documentResponseDtos = new ArrayList<>();

        for (MultipartFile file : files) {

            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            Files.write(filePath, file.getBytes());
            Document document = new Document();
            document.setName(file.getOriginalFilename());
            document.setFilename(file.getOriginalFilename());
            document.setPath(filePath.toString());
            document.setExtension(getFileExtension(file.getOriginalFilename()));
            document.setMime_type(file.getContentType());
            document.setCreatedAt(LocalDate.now());
            document.setUpdated_at(new Date());


            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserSingleton) {
                UserSingleton customUserDetails = (UserSingleton) principal;
                User user = userRepository.findById(customUserDetails.getId()).orElseThrow();
                document.setUser(user);
            } else if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
                throw new RuntimeException("Unauthorized access");
            } else {
                throw new IllegalStateException("Expected UserSingleton but found " + principal.getClass().getName());
            }
            Document savedDocument = documentRepository.save(document);

            DocumentResponseDto responseDto = modelMapper.map(savedDocument, DocumentResponseDto.class);
            String fullUrl = baseUrl + "/uploads/" + file.getOriginalFilename();
            responseDto.setUrl(fullUrl);
            documentResponseDtos.add(responseDto);
        }


        return documentResponseDtos;
    }

    //        Set<DocumentTags> tags = documentRequestDto.getTags().stream().map(tagDto -> {
//            DocumentTags tag = tagRepository.findByName(tagDto.getName()).orElseGet(() -> {
//                DocumentTags newTag = new DocumentTags();
//                newTag.setName(tagDto.getName());
//                return tagRepository.save(newTag);
//            });
//            return tag;
//        }).collect(Collectors.toSet());
//        document.setTags(tags);


    private String getFileExtension(String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        return index == -1 ? "" : originalFilename.substring(index + 1);
    }


@Override
public DocumentResponseDto getDocumentById(Long id) {
    Document document = documentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Document not found"));
    DocumentResponseDto documentResponseDto = modelMapper.map(document, DocumentResponseDto.class);

    String fullUrl = baseUrl + "/uploads/" + document.getFilename();
    documentResponseDto.setUrl(fullUrl);

    return documentResponseDto;
}

    @Override
    public List<DocumentResponseDto> getAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        return documents.stream()
                .map(doc -> {
                    DocumentResponseDto responseDto = modelMapper.map(doc, DocumentResponseDto.class);
                    String fullUrl = baseUrl + "/uploads/" + doc.getFilename();
                    responseDto.setUrl(fullUrl);
                    return responseDto;
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<DocumentResponseDto> getDocumentsByUserId(Long userId) {
        List<Document> documents = documentRepository.findByUserId(userId);
        return documents.stream()
                .map(doc -> {
                    DocumentResponseDto responseDto = modelMapper.map(doc, DocumentResponseDto.class);
                    String fullUrl = baseUrl + "/uploads/" + doc.getFilename();
                    responseDto.setUrl(fullUrl);
                    return responseDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        documentRepository.delete(document);
    }

    @Override
    public List<DocumentResponseDto> getDocumentsByType(String extension) {
        List<Document> documents = documentRepository.findByExtension(extension);
        return documents.stream()
                .map(doc -> {
                    DocumentResponseDto responseDto = modelMapper.map(doc, DocumentResponseDto.class);
                    String fullUrl = baseUrl + "/uploads/" + doc.getFilename();
                    responseDto.setUrl(fullUrl);
                    return responseDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentResponseDto> getDocumentsByTagName(String tagName) {
        List<Document> documents = documentRepository.findDocumentsByTagName(tagName);
        return documents.stream()
                .map(doc -> {
                    DocumentResponseDto responseDto = modelMapper.map(doc, DocumentResponseDto.class);
                    String fullUrl = baseUrl + "/uploads/" + doc.getFilename();
                    responseDto.setUrl(fullUrl);
                    return responseDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentResponseDto> getDocumentByCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        List<Document> documents = documentRepository.findByCreatedAt(currentDate);
        return documents.stream()
                .map(doc -> {
                    DocumentResponseDto responseDto = modelMapper.map(doc, DocumentResponseDto.class);
                    String fullUrl = baseUrl + "/uploads/" + doc.getFilename();
                    responseDto.setUrl(fullUrl);
                    return responseDto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<DocumentResponseDto> getDocumentsByUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserSingleton) {
            UserSingleton currentUser = (UserSingleton) principal;
            Long userId = currentUser.getId();
            List<Document> documents = documentRepository.findByUserId(userId);

            return documents.stream()
                    .map(doc -> {
                        DocumentResponseDto responseDto = modelMapper.map(doc, DocumentResponseDto.class);
                        String fullUrl = baseUrl + "/uploads/" + doc.getFilename();
                        responseDto.setUrl(fullUrl);
                        return responseDto;
                    })
                    .collect(Collectors.toList());

        } else if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
            throw new RuntimeException("Unauthorized access");
        } else {
            throw new IllegalStateException("Expected UserSingleton but found " + principal.getClass().getName());
        }
    }
}
