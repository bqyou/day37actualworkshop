package tfip.nus.iss.day37actual.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Post {

    private String postId;

    private String comments;

    private byte[] image;

    private String imageType;

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public static Post populate(ResultSet rs) throws SQLException {
        final Post post = new Post();
        post.setPostId(rs.getString("post_id"));
        post.setComments(rs.getString("comments"));
        post.setImage(rs.getBytes("picture"));
        post.setImageType(rs.getString("image_type"));
        return post;
    }

}
