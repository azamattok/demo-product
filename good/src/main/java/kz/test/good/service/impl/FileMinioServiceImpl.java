package kz.test.good.service.impl;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import kz.test.good.service.FileMinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class FileMinioServiceImpl implements FileMinioService {
    private final MinioClient minioClient;
    private final String bucket;

    public FileMinioServiceImpl(MinioClient minioClient,
                                @Value("${storage.bucket}") String bucket) {
        this.minioClient = minioClient;
        this.bucket = bucket;
    }

    @Override
    public void storeFile(String fileName, MultipartFile file) {
        try {
            minioClient.putObject(bucket, fileName, file.getInputStream(), file.getSize(), headerMap(), null, file.getContentType());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("Could not store file. Please try again!", ex);
        }
    }

    public void storeFile(String fileName,  Long fileSize, InputStream fileInputStream, String fileContentType) {
        try {
            minioClient.putObject(bucket, fileName, fileInputStream, fileSize, headerMap(), null, fileContentType);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("Could not store file. Please try again!", ex);
        }
    }


    @Override
    public String getRelativePathFromName(String relativePath) {
        return bucket + "/" + relativePath;
    }

    @Override
    public boolean delete(String prefixName) {
        Iterable<Result<Item>> listObjects = minioClient.listObjects(bucket, prefixName, true);
        AtomicBoolean operationRes = new AtomicBoolean(true);
        listObjects.forEach(itemResult -> {
            try {
                minioClient.removeObject(bucket, itemResult.get().objectName());
            } catch (Exception e) {
                operationRes.set(false);
            }
        });
        return operationRes.get();
    }

    @Override
    public String getPresignedUrl(String bucket, String objectName) {
        try {
            return minioClient.presignedGetObject(bucket, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not get presigned url. Please try again!", e);
        }
    }

    @Override
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not check if bucket exists. Please try again!", e);
        }
    }

    @Override
    public void makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not create bucket. Please try again!", e);
        }
    }

    @Override
    public InputStream getObject(String bucketName, String objectName) throws InvalidBucketNameException, NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, NoResponseException, XmlPullParserException, ErrorResponseException, InternalException, InvalidArgumentException, InvalidResponseException {
        return minioClient.getObject(bucketName, objectName);
    }

    private Map<String, String> headerMap() {
        Map<String, String> header = new HashMap<>();
        header.put("x-amz-acl", "public-read");
        return header;
    }
}
