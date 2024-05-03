package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.*;
import com.app.motelappproject4.auth.JwtUntil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostsController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private JwtUntil jwtUtil;
    @Autowired
    private UsersRepository usersRepository;

    // GET all posts by a user created by
    @GetMapping("/myposts")
    public List<Post> index(HttpServletRequest request) {
        String accessToken = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.resolveClaims(request);
        Integer userId = claims.get("userid", Integer.class);
        System.out.println(userId);
        return (List<Post>) postRepository.getListPostFromCreatedBy(userId);
    }

    // GET all posts
    @GetMapping
    public ResponseEntity<List<Post>> getAllPost() {
        List<Post> posts = (List<Post>) postRepository.findAll();
        return ResponseEntity.ok(posts);
    }

    // GET a single post by ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable int id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST a new post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, HttpServletRequest request) {
        String accessToken = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.resolveClaims(request);
        Integer userId = claims.get("userid", Integer.class);
        User user = usersRepository.findById(userId).orElse(null);
        if (user != null) {
            post.setCreatedBy(user);
            Post savedPost = postRepository.save(post);
            return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // PUT update a post
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable int id, @RequestBody Post updatedPost) {
        return postRepository.findById(id).map(existingPost -> {
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setContent(updatedPost.getContent());
            existingPost.setStatus(updatedPost.getStatus());
            existingPost.setIsDeleted(updatedPost.getIsDeleted());
            Post savedPost = postRepository.save(existingPost);
            return ResponseEntity.ok(savedPost);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE a post
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable int id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
