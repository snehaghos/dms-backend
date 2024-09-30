package com.wish.dms_app_api.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponseDto {
	 	private Long id;
	    private String name;
	    private String filename;
	    private String path;
	    private String mime_type;
	    private String extension;
	    private LocalDate createdAt;
	    private Date updated_at;
		private String url;
	    private Long userId; 
	    private List<String> tags;
}