package kz.test.good.config;

import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class StorageConfiguration {
    @Value("${storage.endpoint}")
    private String endpoint;


    @Value("${storage.login}")
    private String login;


    @Value("${storage.password}")
    private String password;

    @Value("${storage.bucket}")
    private String bucket;
    @Bean
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException, IOException, InvalidKeyException,
            NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException, InternalException, NoResponseException,
            InvalidBucketNameException, XmlPullParserException, ErrorResponseException, InvalidObjectPrefixException, RegionConflictException {
        MinioClient client = new MinioClient(endpoint, login, password);
        if (!client.bucketExists(bucket)) {
            client.makeBucket(bucket);
            client.setBucketPolicy(bucket, bucketPublicPolice());
        }
        return client;
    }

    private String bucketPublicPolice() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("    \"Statement\": [\n");
        builder.append("        {\n");
        builder.append("            \"Action\": \"s3:GetObject\",\n");
        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::" + bucket + "/*\"\n");
        builder.append("        }\n");
        builder.append("    ],\n");
        builder.append("    \"Version\": \"2012-10-17\"\n");
        builder.append("}\n");

        return builder.toString();
    }
}
