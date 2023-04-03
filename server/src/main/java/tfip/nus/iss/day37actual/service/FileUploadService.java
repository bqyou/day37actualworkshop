package tfip.nus.iss.day37actual.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tfip.nus.iss.day37actual.model.Post;
import tfip.nus.iss.day37actual.repo.FileUploadRepository;

@Service
public class FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;

    public String uploadFile(MultipartFile file, String comments) throws SQLException, IOException {
        String postId = UUID.randomUUID().toString().substring(0, 8);
        fileUploadRepository.uploadBlob(file, postId, comments);
        return postId;
    }

    public Optional<Post> getPost(String postId) throws SQLException {
        return fileUploadRepository.getPostById(postId);
    }

    public List<String> getPostIds() {
        return fileUploadRepository.getPostIds();
    }
}
