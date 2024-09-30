package com.wish.dms_app_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequestDto {
    private MultipartFile[] files;
//    private String name;
//    private long userId;
//
//    private Set<TagDto> tags;
}
