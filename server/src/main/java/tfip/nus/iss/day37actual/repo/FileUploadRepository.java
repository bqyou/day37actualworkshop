package tfip.nus.iss.day37actual.repo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import tfip.nus.iss.day37actual.model.Post;

@Repository
public class FileUploadRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_UPLOAD = "INSERT INTO posts (post_id, comments , picture, image_type ) VALUES(?, ?, ?, ?)";

    private static final String SQL_GET_POST = "select post_id, comments, picture, image_type from posts where post_id = ?";

    private static final String SQL_POST_ID = "select post_id from posts";

    @Autowired
    private DataSource dataSource;

    public void uploadBlob(MultipartFile file, String postId, String comments)
            throws SQLException, IOException {

        String imageType = file.getContentType();

        try (Connection con = dataSource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SQL_UPLOAD)) {
            InputStream is = file.getInputStream();
            pstmt.setString(1, postId);
            pstmt.setString(2, comments);
            pstmt.setBinaryStream(3, is, file.getSize());
            pstmt.setString(4, imageType);
            pstmt.executeUpdate();
        }
    }

    public Optional<Post> getPostById(String postId) {
        return jdbcTemplate.query(
                SQL_GET_POST,
                (ResultSet rs) -> {
                    if (!rs.next())
                        return Optional.empty();
                    final Post post = Post.populate(rs);
                    return Optional.of(post);
                },
                postId);
    }

    public List<String> getPostIds() {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_POST_ID);
        List<String> postIds = new LinkedList<String>();
        while (rs.next()) {
            postIds.add(rs.getString("post_id"));
        }
        return postIds;
    }

}
