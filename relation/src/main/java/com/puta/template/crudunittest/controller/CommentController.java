package com.puta.template.crudunittest.controller;

import com.puta.template.crudunittest.domain.dao.Comment;
import com.puta.template.crudunittest.domain.dao.Post;
import com.puta.template.crudunittest.domain.dto.CommentDto;
import com.puta.template.crudunittest.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<Object> getAll() {
        return commentService.getAllComment();
    }

    @PostMapping("/comments")
    public ResponseEntity<Object> add(@Valid @RequestBody CommentDto commentDto) {
        return commentService.addComment(commentDto);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

}
