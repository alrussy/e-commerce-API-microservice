package com.alrussy.file_service.controller;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alrussy.file_service.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FileController {
	private final FileService fileService; 
	
	
	
	@PostMapping("/upload/{dir}")
	public ResponseEntity<String> uploadFile(@PathVariable String dir,@RequestParam MultipartFile file) throws IOException{
		return ResponseEntity.ok(fileService.uploadFile(dir, file));
	}
	
	
	@GetMapping("/images/{dir}/{filename}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename,@PathVariable("dir") String dir ) throws IOException{		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(fileService.getFile(filename, dir));						
	}
	

}
