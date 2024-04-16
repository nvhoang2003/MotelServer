// PostsController.java
package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.auth.JwtUntil;
import com.app.motelappproject4.models.Post;
import com.app.motelappproject4.models.PostRepository;
import com.app.motelappproject4.models.User;
import com.app.motelappproject4.models.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
public class PostsController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private JwtUntil jwtUtil;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/api/myposts")
    public List<Post> index(HttpServletRequest request) {
        String accessToken = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.resolveClaims(request);

        Integer userId = claims.get("userid", Integer.class);
        System.out.println(userId);
        return (List<Post>) postRepository.getListPostFromCreatedBy(userId);
    }

    @GetMapping("/api/posts/{id}")
    public Optional<Post> find(@PathVariable int id) {
        return postRepository.findById(id);
    }

    @PostMapping("/api/posts")
    public Post create(@RequestBody Post post, HttpServletRequest request) {

        String accessToken = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.resolveClaims(request);

        Integer userId = claims.get("userid", Integer.class);
        System.out.println(userId);

        User u = usersRepository.findUserById(userId);
        System.out.println("olala" + u);
        post.setCreatedBy(u);

        return postRepository.save(post);
    }

    @PutMapping("/api/posts/{id}")
    public int update(@PathVariable int id, @RequestBody Post updatedPost) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setContent(updatedPost.getContent());
            try {
                postRepository.save(existingPost);
                return 1; // Success
            }catch (Exception e){
                System.out.println(e);
                return 0;
            }

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