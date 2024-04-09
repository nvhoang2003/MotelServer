//PostService.java
package com.app.motelappproject4.services;

import com.app.motelappproject4.models.Comment;
import com.app.motelappproject4.models.CommentRepository;
import com.app.motelappproject4.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    public List<CommentsOfPost> getCommentsOfPost(int postId){
        List<Comment> comments = commentRepository.findAllByCommentTypeAndCommentValueAndIsDeleted("post", postId, 0);
        List<CommentsOfPost> commentsOfPost = new ArrayList<>();
        for (Comment comment : comments) {
            CommentsOfPost commentOfPost = new CommentsOfPost();
            commentOfPost.comment = comment;
            commentOfPost.reply_comment = getReplyComments(comment.getId());
            commentsOfPost.add(commentOfPost);
        }
        return commentsOfPost;
    }

    private CommentsOfPost getReplyComments(int commentId) {
        List<Comment> replyComments = commentRepository.findAllByCommentTypeAndCommentValueAndIsDeleted("reply", commentId, 0);
        if (replyComments.isEmpty()) {
            return null;
        } else {
            CommentsOfPost replyCommentOfPost = new CommentsOfPost();
            replyCommentOfPost.comment = replyComments.get(0);
            replyCommentOfPost.reply_comment = getReplyComments(replyComments.get(0).getId());
            return replyCommentOfPost;
        }
    }
}
