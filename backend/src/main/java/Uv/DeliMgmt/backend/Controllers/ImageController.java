package Uv.DeliMgmt.backend.Controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final String uploadDir = System.getProperty("user.dir") + "/DeliMgmt_WebSite/RESURCES";

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        if (!file.getContentType().startsWith("image/")) {
            return "El archivo no es una imagen.";
        }

        // Crear la carpeta si no existe
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // Crear los directorios necesarios
        }

        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File imageFile = new File(directory, uniqueFileName);

        try {
            file.transferTo(imageFile);
            return "/api/images/" + uniqueFileName; // Devolver URL relativa
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al subir la imagen: " + e.getMessage();
        }
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Cambiar según el tipo de imagen
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
