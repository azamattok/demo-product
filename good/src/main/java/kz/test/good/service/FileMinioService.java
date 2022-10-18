package kz.test.good.service;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface FileMinioService {
    void storeFile(String fileName, MultipartFile file);

    void storeFile(String fileName, Long fileSize, InputStream fileInputStream, String  fileContentType );

    String getRelativePathFromName(String relativePath);

    boolean delete(String prefixName);

    String getPresignedUrl(String bucket, String objectName);

    boolean bucketExists(String bucketName);

    void makeBucket(String bucketName);

    InputStream getObject(String bucketName, String objectName)  throws InvalidBucketNameException, NoSuchAlgorithmException, InsufficientDataException, IOException,
            InvalidKeyException, NoResponseException, XmlPullParserException, ErrorResponseException,
            InternalException, InvalidArgumentException, InvalidResponseException;
}
