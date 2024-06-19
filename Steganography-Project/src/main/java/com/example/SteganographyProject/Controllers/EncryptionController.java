package com.example.SteganographyProject.Controllers;

import com.example.SteganographyProject.Helpers.CustomMultipartFile;
import com.example.SteganographyProject.Helpers.ImageUploadHelper;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EncryptionController {

    @Autowired
    private ImageUploadHelper imageUploadHelper;
    @PostMapping("/encrypt")
    @ResponseBody
     public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam String message) {
        Map<String, String> response = new HashMap<>();
        try {
            if (file.isEmpty()) {
                response.put("error", "Please select a file to upload");
                return ResponseEntity.badRequest().body(response);
            }

            BufferedImage img = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            MultipartFile embeddedFile = embedMessage(img, message);

            if (embeddedFile != null) {
                byte[] imageBytes = embeddedFile.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                response.put("base64Image", base64Image);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "An error occurred while processing the image");
        }
        return ResponseEntity.status(500).body(response);
    }
    private MultipartFile embedMessage(BufferedImage img, String mess) throws IOException {
        int messageLength = mess.length();

        int imageWidth = img.getWidth(), imageHeight = img.getHeight(), imageSize = imageWidth * imageHeight;
        if (messageLength * 8 + 32 > imageSize) {
            return null;
        }
        embedInteger(img, messageLength, 0, 0);

        byte[] b = mess.getBytes();
        for (int i = 0; i < b.length; i++)
            embedByte(img, b[i], i * 8 + 32, 0);

        // Convert the modified BufferedImage to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(img, "png", outputStream);
        byte[] imageByteArray = outputStream.toByteArray();

        String originalFilename = "my-image.png"; // Set the original filename
        String contentType = "image/png"; // Set the content type (or "image/jpeg" for JPG)

        // Create a MultipartFile instance from the byte array
        MultipartFile multipartFile = new CustomMultipartFile(imageByteArray, originalFilename, contentType);
        return multipartFile;
    }

    private void embedInteger(BufferedImage img, int n, int start, int storageBit) {
        int maxX = img.getWidth(), maxY = img.getHeight(),
                startX = start/maxY, startY = start - startX*maxY, count=0;
        for(int i=startX; i<maxX && count<32; i++) {
            for(int j=startY; j<maxY && count<32; j++) {
                int rgb = img.getRGB(i, j), bit = getBitValue(n, count);
                rgb = setBitValue(rgb, storageBit, bit);
                img.setRGB(i, j, rgb);
                count++;
            }
        }
    }

//     embedByte(BufferedImage img, byte b, int start, int storageBit) method embeds a single byte (b) into the image (img) starting at a specific pixel (start) and using a specific bit position (storageBit) in the RGB values.
//     It iterates over the pixels of the image, modifying one bit of one color channel per pixel to encode each bit of the byte
    private void embedByte(BufferedImage img, byte b, int start, int storageBit) {
        int maxX = img.getWidth(), maxY = img.getHeight(),
                startX = start/maxY, startY = start - startX*maxY, count=0;
        for(int i=startX; i<maxX && count<8; i++) {
            for(int j=startY; j<maxY && count<8; j++) {
                int rgb = img.getRGB(i, j), bit = getBitValue(b, count);
                rgb = setBitValue(rgb, storageBit, bit);
                img.setRGB(i, j, rgb);
                count++;
            }
        }
    }
    
//     getBitValue(int n, int location) utility method retrieves the value (0 or 1) of a specific bit from an integer (n) at a given location (location).
    private int getBitValue(int n, int location) {
        int v = n & (int) Math.round(Math.pow(2, location));
        return v==0?0:1;
    }

//     setBitValue(int n, int location, int bit) utility method sets a specific bit in an integer (n) at a given location (location) to a new value (bit).
    private int setBitValue(int n, int location, int bit) {
        int toggle = (int) Math.pow(2, location), bv = getBitValue(n, location);
        if(bv == bit)
            return n;
        if(bv == 0 && bit == 1)
            n |= toggle;
        else if(bv == 1 && bit == 0)
            n ^= toggle;
        return n;
    }
}



