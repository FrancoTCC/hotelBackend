package com.francode.hotelBackend.business.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.francode.hotelBackend.business.services.interfaces.CloudinaryService;
import com.francode.hotelBackend.exceptions.custom.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String[] ALLOWED_CONTENT_TYPES = {"image/jpeg", "image/png", "image/gif"};

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile file, String folder) throws IOException, ValidationException {
        validateImageFormat(file);

        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", folder)
            );
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            throw new IOException(e.getMessage().contains("invalid image") ?
                    "La imagen no es válida para ser subida a Cloudinary." :
                    "Error al subir la imagen a Cloudinary.");
        }
    }

    @Override
    public void deleteImage(String imageUrl, String folder) throws IOException {
        try {
            String publicId = extractPublicId(imageUrl);
            cloudinary.uploader().destroy(folder + publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new IOException(e.getMessage().contains("Not Found") ?
                    "La imagen no fue encontrada en Cloudinary." :
                    "No se pudo eliminar la imagen de Cloudinary. Inténtalo de nuevo más tarde.");
        }
    }

    private void validateImageFormat(MultipartFile file) throws ValidationException {
        if (file == null || file.isEmpty()) {
            throw new ValidationException("El archivo de imagen no puede estar vacío.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !Arrays.asList(ALLOWED_CONTENT_TYPES).contains(contentType)) {
            throw new ValidationException("Solo se permiten imágenes con los formatos: JPG, JPEG, PNG, GIF.");
        }

        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new ValidationException("El archivo no es una imagen válida.");
            }
        } catch (IOException e) {
            throw new ValidationException("Error al procesar el archivo de imagen.");
        }
    }

    private String extractPublicId(String imageUrl) {
        int lastSlashIndex = imageUrl.lastIndexOf("/");
        int lastDotIndex = imageUrl.lastIndexOf(".");
        return imageUrl.substring(lastSlashIndex + 1, lastDotIndex);
    }
}