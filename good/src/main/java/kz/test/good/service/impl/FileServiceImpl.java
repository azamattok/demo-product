package kz.test.good.service.impl;

import kz.test.good.domain.FileInfo;
import kz.test.good.dto.FileInfoDto;
import kz.test.good.repository.FileInfoRepository;
import kz.test.good.service.FileMinioService;
import kz.test.good.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileInfoRepository fileInfoRepository;
    private final FileMinioService fileMinioService;

    @Override
    public FileInfoDto upload(MultipartFile file) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getOriginalFilename());
        fileInfo.setContentType(file.getContentType());


        byte[] data = null;
        try {
            data = file.getBytes();
            fileInfo.setFileSize(data.length);

        } catch (IOException e) {
            log.error("Error saving file", e);
        }
        fileInfo = fileInfoRepository.save(fileInfo);

        String uuid = fileInfo.getId().toString();
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String url = uuid + "." + extension.replace(" ", "");
        fileMinioService.storeFile(url,  (long) data.length, new ByteArrayInputStream(data), fileInfo.getContentType());
        fileInfo.setUrl(url);
        fileInfoRepository.save(fileInfo);
        return convertToFileInfoDto(fileInfo);
    }



    private FileInfoDto convertToFileInfoDto(FileInfo fileInfo) {
        FileInfoDto res = new FileInfoDto();
        res.setUuid(fileInfo.getId().toString());
        res.setName(fileInfo.getFileName());
        res.setType(fileInfo.getContentType());
        res.setSize(fileInfo.getFileSize());
        return res;
    }



    private Path buildPath(FileInfo fileInfo, String uuid) {
        return Paths.get(
                DateTimeFormatter.ofPattern("yyyy/MM/dd").format(fileInfo.getCreateDate()));
    }
}
