package com.bokachan.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileUtils {

    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList(
        "jpg", "jpeg", "png", "gif", "bmp", "webp"
    );

    private static final List<String> ALLOWED_DOCUMENT_EXTENSIONS = Arrays.asList(
        "txt", "pdf", "doc", "docx"
    );

    public static boolean isImageFile(MultipartFile file) {
        String extension = getFileExtension(file.getOriginalFilename());
        return ALLOWED_IMAGE_EXTENSIONS.contains(extension.toLowerCase());
    }

    public static boolean isDocumentFile(MultipartFile file) {
        String extension = getFileExtension(file.getOriginalFilename());
        return ALLOWED_DOCUMENT_EXTENSIONS.contains(extension.toLowerCase());
    }

    public static String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public static String generateFileName(String originalFilename, String prefix) {
        String extension = getFileExtension(originalFilename);
        String timestamp = String.valueOf(System.currentTimeMillis());
        return prefix + "_" + timestamp + "." + extension;
    }
}