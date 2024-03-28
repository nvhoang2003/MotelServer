// PostsController.java
package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.Post;
import com.app.motelappproject4.models.PostRepository;
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
public class PostsController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/api/posts")
    public List<Post> index() {
        return (List<Post>) postRepository.findAll();
    }

    @GetMapping("/api/posts/{id}")
    public Optional<Post> find(@PathVariable int id) {
        return postRepository.findById(id);
    }

    @PostMapping("/api/posts")
    public Post create(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/api/posts/{id}")
    public int update(@PathVariable int id, @RequestBody Post updatedPost) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            // Update fields here
            // For example: existingPost.setTitle(updatedPost.getTitle());
            postRepository.save(existingPost);
            return 1; // Success
        }
        return 0; // Failed to update
    }

    @DeleteMapping("/api/posts/{id}")
    public int delete(@PathVariable int id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return 1; // Success
        }
        return 0; // Failed to delete
    }

    @GetMapping("/api/seed/posts")
    public List<Post> seedPostsData() {
        Faker faker = new Faker();
        List<User> users = (List<User>) usersRepository.findAll();
        List<Post> list = new ArrayList<Post>();
        for (int i = 0; i < 10; i++) {
            Post post = new Post();
            post.setTitle(faker.lorem().sentence());
            post.setContent(faker.lorem().paragraph());
            post.setStatus(faker.lorem().word());
            post.setIsDeleted(faker.number().numberBetween(0, 1)); // Assuming 0: Not Deleted, 1: Deleted
            if (!users.isEmpty()) {
                post.setCreatedBy(users.get(faker.number().numberBetween(0, users.size())));
            }
            list.add(post);
        }
        postRepository.saveAll(list);
        return list;
    }
}