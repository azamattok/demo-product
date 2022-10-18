package kz.test.good.service;

import kz.test.good.dto.FileInfoDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileInfoDto upload(MultipartFile file);
}
