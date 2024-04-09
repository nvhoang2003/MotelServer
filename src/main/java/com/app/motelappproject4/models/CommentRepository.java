//CommentRepository.java
package com.app.motelappproject4.models;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findAllByCommentTypeAndCommentValueAndIsDeleted(String post, int postId, int i);
}
