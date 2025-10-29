package com.bokachan.controller;

import com.bokachan.service.CloudinaryService;
import com.bokachan.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    private final CloudinaryService cloudinaryService;

    public FileUploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File không được để trống");
            }

            if (!FileUtils.isImageFile(file)) {
                return ResponseEntity.badRequest().body("Chỉ chấp nhận file ảnh (jpg, jpeg, png, gif, webp)");
            }

            String imageUrl = cloudinaryService.uploadImage(file, "truyen_images");
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Upload ảnh thành công");
            response.put("url", imageUrl);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Lỗi upload ảnh: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Lỗi upload ảnh: " + e.getMessage());
        }
    }

    @PostMapping("/story-cover")
    public ResponseEntity<?> uploadStoryCover(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File không được để trống");
            }

            if (!FileUtils.isImageFile(file)) {
                return ResponseEntity.badRequest().body("Chỉ chấp nhận file ảnh (jpg, jpeg, png, gif, webp)");
            }

            String imageUrl = cloudinaryService.uploadImage(file, "story_covers");
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Upload ảnh bìa thành công");
            response.put("url", imageUrl);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Lỗi upload ảnh bìa: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Lỗi upload ảnh bìa: " + e.getMessage());
        }
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File không được để trống");
            }

            if (!FileUtils.isImageFile(file)) {
                return ResponseEntity.badRequest().body("Chỉ chấp nhận file ảnh (jpg, jpeg, png, gif, webp)");
            }

            String imageUrl = cloudinaryService.uploadImage(file, "user_avatars");
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Upload avatar thành công");
            response.put("url", imageUrl);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Lỗi upload avatar: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Lỗi upload avatar: " + e.getMessage());
        }
    }
}