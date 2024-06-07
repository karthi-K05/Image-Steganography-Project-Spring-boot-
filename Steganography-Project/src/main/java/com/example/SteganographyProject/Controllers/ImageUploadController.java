package com.example.SteganographyProject.Controllers;

import com.example.SteganographyProject.Helpers.ImageUploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageUploadController {

    @Autowired
    private ImageUploadHelper imageUploadHelper;
    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){

        try{

            if(file.isEmpty()){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Field cannot be empty");
            }
            boolean f = imageUploadHelper.uploadFile(file);
            if(f){
                return ResponseEntity.ok("Image has been successfully uploaded");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some went wrong");
    }

}
