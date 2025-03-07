package tn.esprit.powerHR.utils.User;

import io.minio.*;
import io.minio.errors.MinioException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class MinIOUtils {

    private static final String ENDPOINT = "http://localhost:9000";
    private static final String ACCESS_KEY = "minioadmin";
    private static final String SECRET_KEY = "minioadmin";

    private static MinioClient minioClient;

    static {
        try {
            minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize MinIO client", e);
        }
    }

    @lombok.SneakyThrows
    public static String uploadCV(String bucketName, String filePath, String fileName) {
        try {
            // ✅ Replace spaces with underscores BEFORE uploading
            String safeFileName = fileName.replace(" ", "_");

            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            Path path = Paths.get(filePath);
            minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(safeFileName)
                    .filename(path.toString())
                    .build());

            String fileUrl = ENDPOINT + "/" + bucketName + "/" + safeFileName;
            return fileUrl;

        } catch (MinioException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @lombok.SneakyThrows
    public static void deleteCV(String bucketName, String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
        } catch (MinioException | IOException e) {
            e.printStackTrace();
        }
    }
}
