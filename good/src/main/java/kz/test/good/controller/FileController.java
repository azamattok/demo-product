package kz.test.good.controller;

import io.swagger.annotations.ApiModel;
import kz.test.good.dto.FileInfoDto;
import kz.test.good.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@ApiModel(description = "Контроллер для работы с товарами")
@CrossOrigin
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileInfoDto> uploadFile(@RequestPart MultipartFile file) {
        return ResponseEntity.ok(fileService.upload(file));
    }
}
