package tfip.nus.iss.day37actual.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import tfip.nus.iss.day37actual.model.Post;
import tfip.nus.iss.day37actual.service.FileUploadService;

@Controller
@RequestMapping(path = "/api")
public class PhotoController {

    @Autowired
    private FileUploadService fileUploadService;

    private static final String BASE64_PREFIX_DECODER = "data:%s;base64,";

    @GetMapping(path = "/getIds", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getAllIds() {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (String id : fileUploadService.getPostIds()) {
            arrBuilder.add(Json.createObjectBuilder().add("postId", id).build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(arrBuilder.build().toString());
    }

    @PostMapping(path = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> uploadPhoto(
            @RequestPart MultipartFile file,
            @RequestPart String comments) throws SQLException {
        String postId = "";

        try {
            postId = fileUploadService.uploadFile(file, comments);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("postId", postId)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString());
    }

    @GetMapping(path = "/get/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getPhoto(@PathVariable String postId) throws SQLException {
        Optional<Post> opt = fileUploadService.getPost(postId);
        Post p = opt.get();
        String encodedString = Base64.getEncoder().encodeToString(p.getImage());
        JsonObject payload = Json.createObjectBuilder()
                .add("picture", BASE64_PREFIX_DECODER.formatted(p.getImageType()) + encodedString)
                .add("comments", p.getComments())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload.toString());
    }

}
