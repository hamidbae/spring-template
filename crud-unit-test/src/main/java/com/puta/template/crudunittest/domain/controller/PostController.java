package com.puta.template.crudunittest.domain.controller;

import com.puta.template.crudunittest.domain.dto.PostDto;
import com.puta.template.crudunittest.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/posts/{id}")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
        return postService.getOnePost(id);
    }

    @PostMapping("/posts")
    public ResponseEntity<Object> add(@RequestBody PostDto postDto) {
        return postService.addPost(postDto);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return postService.deletePost(id);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody PostDto postDto) {
        return postService.updatePost(id, postDto);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<Object> getAllDetail(@PathVariable String keyword) {
        return postService.searchPosts(keyword);
    }
}