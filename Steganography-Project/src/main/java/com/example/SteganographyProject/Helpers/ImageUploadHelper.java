package com.example.SteganographyProject.Helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUploadHelper {
   public final String UPLOAD_DIR = "src//main//resources//static//Images";

    public boolean uploadFile(MultipartFile multipartFile){
        boolean f = false;

        try{

            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIR+ File.separator+ multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

            f = true;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }

}
