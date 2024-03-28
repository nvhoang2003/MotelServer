

// LikesController.java
package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.Like;
import com.app.motelappproject4.models.LikeRepository;
import com.app.motelappproject4.models.Post;
import com.app.motelappproject4.models.PostRepository;
import com.app.motelappproject4.models.User;
import com.app.motelappproject4.models.UsersRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation .*;

        import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
public class LikesController {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/api/likes")
    public List<Like> index() {
        return (List<Like>) likeRepository.findAll();
    }

    @GetMapping("/api/likes/{id}")
    public Optional<Like> find(@PathVariable int id) {
        return likeRepository.findById(id);
    }

    @PostMapping("/api/likes")
    public Like create(@RequestBody Like like) {
        return likeRepository.save(like);
    }

    @PutMapping("/api/likes/{id}")
    public int update(@PathVariable int id, @RequestBody Like updatedLike) {
        Optional<Like> optionalLike = likeRepository.findById(id);
        if (optionalLike.isPresent()) {
            Like existingLike = optionalLike.get();
            // Update fields here
            // For example: existingLike.setValue(updatedLike.getValue());
            likeRepository.save(existingLike);
            return 1; // Success
        }
        return 0; // Failed to update
    }

    @DeleteMapping("/api/likes/{id}")
    public int delete(@PathVariable int id) {
        if (likeRepository.existsById(id)) {
            likeRepository.deleteById(id);
            return 1; // Success
        }
        return 0; // Failed to delete
    }

    @GetMapping("/api/seed/likes")
    public List<Like> seedLikesData() {
        Faker faker = new Faker();
        List<User> users = (List<User>) usersRepository.findAll();
        List<Post> posts = (List<Post>) postRepository.findAll();
        List<Like> list = new ArrayList<Like>();
        for (int i = 0; i < 10; i++) {
            Like like = new Like();
            like.setValue(faker.number().numberBetween(-1, 2)); // Assuming -1: Dislike, 0: Neutral, 1: Like
            like.setIsDeleted(faker.number().numberBetween(0, 1)); // Assuming 0: Not Deleted, 1: Deleted
            if (!users.isEmpty()) {
                like.setUser(users.get(faker.number().numberBetween(0, users.size())));
            }
            if (!posts.isEmpty()) {
                like.setPost(posts.get(faker.number().numberBetween(0, posts.size())));
            }
            likeRepository.save(like);
            list.add(like);
        }
        likeRepository.saveAll(list);
        return list;
    }
}
