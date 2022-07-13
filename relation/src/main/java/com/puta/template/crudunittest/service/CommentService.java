package com.puta.template.crudunittest.service;

import com.puta.template.crudunittest.domain.dao.Comment;
import com.puta.template.crudunittest.domain.dao.Post;
import com.puta.template.crudunittest.domain.dto.CommentDto;
import com.puta.template.crudunittest.repository.CommentRepository;
import com.puta.template.crudunittest.repository.PostRepository;
import com.puta.template.crudunittest.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    public ResponseEntity<Object> getAllComment(){
        List<Comment> daoList = commentRepository.findAll();

        return Response.build(Response.get("comment"), daoList, null, HttpStatus.OK);
    }

    public ResponseEntity<Object> addComment(CommentDto requestBody){

        Optional<Post> post = postRepository.findById(requestBody.getPostId());
        if(post.isEmpty()){
            return Response.build(Response.notFound("Post"), null, null, HttpStatus.BAD_REQUEST);
        }

        Comment comment = commentRepository.save(Comment.builder().post(post.get()).text(requestBody.getText()).build());

        return Response.build(Response.create("comment"), comment, null, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deleteComment(Long id){
        Optional<Comment> comment = commentRepository.findById(id);
        if(comment.isEmpty()){
            return Response.build(Response.notFound("Comment"), null, null, HttpStatus.BAD_REQUEST);
        }

        postRepository.deleteById(id);

        return Response.build(Response.delete("post"), null, null, HttpStatus.CREATED);
    }
}
