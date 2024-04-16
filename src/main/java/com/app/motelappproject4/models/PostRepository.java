// PostRepository.java
package com.app.motelappproject4.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @Query("SELECT p FROM Post p WHERE (:createdBy is null or p.createdBy.id = :createdBy)")
    List<Post> getListPostFromCreatedBy(@Param("createdBy") Integer createdBy);}