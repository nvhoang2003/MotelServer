// LikesController.java
package com.app.motelappproject4.controllers.api;

import com.app.motelappproject4.models.Like;
import com.app.motelappproject4.models.LikeRepository;
import com.app.motelappproject4.models.PostRepository;
import com.app.motelappproject4.models.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class LikesController {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/api/likes")
    public ResponseEntity<List<Like>> getAllLikes() {
        List<Like> likes = (List<Like>) likeRepository.findAll();
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

    @GetMapping("/api/likes/{id}")
    public ResponseEntity<Like> getLikeById(@PathVariable int id) {
        Optional<Like> optionalLike = likeRepository.findById(id);
        if (optionalLike.isPresent()) {
            return new ResponseEntity<>(optionalLike.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/likes")
    public ResponseEntity<Like> createLike(@RequestBody Like like) {
        Like savedLike = likeRepository.save(like);
        return new ResponseEntity<>(savedLike, HttpStatus.CREATED);
    }

    @PutMapping("/api/likes/{id}")
    public ResponseEntity<Like> updateLike(@PathVariable int id, @RequestBody Like updatedLike) {
        Optional<Like> optionalLike = likeRepository.findById(id);
        if (optionalLike.isPresent()) {
            Like existingLike = optionalLike.get();
            existingLike.setValue(updatedLike.getValue());
            existingLike.setIsDeleted(updatedLike.getIsDeleted());
            likeRepository.save(existingLike);
            return new ResponseEntity<>(existingLike, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/likes/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable int id) {
        if (likeRepository.existsById(id)) {
            likeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
