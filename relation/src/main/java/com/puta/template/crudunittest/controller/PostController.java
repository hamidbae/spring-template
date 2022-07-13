package com.puta.template.crudunittest.controller;

import com.puta.template.crudunittest.domain.dao.Post;
import com.puta.template.crudunittest.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<Object> getAll() {
        return postService.getAllPosts();
    }

    @PostMapping("/posts")
    public ResponseEntity<Object> add(@Valid @RequestBody Post post) {
        return postService.addPost(post);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}