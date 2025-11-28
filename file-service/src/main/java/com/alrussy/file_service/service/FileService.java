package com.alrussy.file_service.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    @Value("${file.upload.base.url}")
    private String baseUrl;

    public String uploadFile(String dir, MultipartFile file) throws IOException {

        Path fileStorageLocation =
                Paths.get(baseUrl + File.separator + dir).toAbsolutePath().normalize();
        Files.createDirectories(fileStorageLocation);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains("..")) {
            throw new FileUploadException("file name contains ivalid path sequence " + fileName);
        }

        Path targetLocation = fileStorageLocation.resolve(fileName);

        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        // Files.delete(targetLocation);
        log.info(targetLocation.toString());
        return "/images/" + dir + "/" + fileName;
    }

    public Resource getFile(String fileName, String dir) throws IOException {

        Path fileStorageLocation =
                Paths.get(baseUrl + File.separator + dir).toAbsolutePath().normalize();
        Path targetLocation = fileStorageLocation.resolve(fileName);
        return new InputStreamResource(Files.newInputStream(targetLocation));

        //
        //
        //		 List<Path> paths=new ArrayList<>();
        //
        //		 Files.list(fileStorageLocation)
        //				 .forEach(t -> {
        //
        //					 if(t.getFileName().toString().startsWith(imageName)) {
        //						resource = new InputStreamResource(Files.newInputStream(paths.get(0)));
        //
        //						 paths.add(t);
        //						 log.info(t.toString());
        //					 };
        //
        //				 });

    }
}