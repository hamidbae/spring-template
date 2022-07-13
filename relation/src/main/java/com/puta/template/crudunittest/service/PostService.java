package com.puta.template.crudunittest.service;

import com.puta.template.crudunittest.domain.dao.Post;
import com.puta.template.crudunittest.repository.PostRepository;
import com.puta.template.crudunittest.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> getAllPosts(){
        List<Post> daoList = postRepository.findAll();

        return Response.build(Response.get("posts"), daoList, null, HttpStatus.OK);
    }

    public ResponseEntity<Object> addPost(Post requestBody){
        Post post = mapper.map(requestBody, Post.class);

        postRepository.save(post);

        return Response.build(Response.create("post"), post, null, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deletePost(Long id){
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
            return Response.build(Response.notFound("Post"), null, null, HttpStatus.BAD_REQUEST);
        }

        postRepository.deleteById(id);

        return Response.build(Response.delete("post"), null, null, HttpStatus.CREATED);
    }
}
