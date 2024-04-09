package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.Comment;
import com.app.motelappproject4.models.CommentRepository;
import com.app.motelappproject4.models.User;
import com.app.motelappproject4.models.UsersRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/api/comments")
    public List<Comment> index() {
        return (List<Comment>) commentRepository.findAll();
    }

    @GetMapping("/api/comments/{id}")
    public Optional<Comment> find(@PathVariable int id) {
        return commentRepository.findById(id);
    }

    @PostMapping("/api/comments")
    public Comment create(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    @PutMapping("/api/comments/{id}")
    public int update(@PathVariable int id, @RequestBody Comment updatedComment) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment existingComment = optionalComment.get();
            // Update fields here
            // For example: existingComment.setContent(updatedComment.getContent());
            commentRepository.save(existingComment);
            return 1; // Success
        }
        return 0; // Failed to update
    }

    @DeleteMapping("/api/comments/{id}")
    public int delete(@PathVariable int id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return 1; // Success
        }
        return 0; // Failed to delete
    }

    @GetMapping("/api/seed/comments")
    public List<Comment> seedCommentsData() {
        Faker faker = new Faker();
        List<User> users = (List<User>) usersRepository.findAll();
        List<Comment> list = new ArrayList<Comment>();
        for (int i = 0; i < 10; i++) {
            Comment comment = new Comment();
            comment.setContent(faker.lorem().sentence());
            comment.setCommentType(faker.lorem().word());
            comment.setIsDeleted(faker.number().numberBetween(0, 1)); // Assuming 0: Not Deleted, 1: Deleted
            if (!users.isEmpty()) {
                comment.setCreatedBy(users.get(faker.number().numberBetween(0, users.size())));
            }
            commentRepository.save(comment);
            list.add(comment);
        }
        commentRepository.saveAll(list);
        return list;
    }
}
