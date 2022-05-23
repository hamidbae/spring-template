package com.puta.template.crudunittest.service;

import com.puta.template.crudunittest.domain.dao.Post;
import com.puta.template.crudunittest.domain.dto.PostDto;
import com.puta.template.crudunittest.domain.util.Response;
import com.puta.template.crudunittest.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public ResponseEntity<Object> getAllPosts(){
        List<Post> daoList = postRepository.findAllSorted();
        List<PostDto> dtoList = new ArrayList<>();

        for(Post dao:daoList){
            dtoList.add(PostDto.builder()
                    .id(dao.getId())
                    .content(dao.getContent())
                    .createdAt(dao.getCreatedAt())
                    .build());
        }

        return Response.build(Response.get("posts"), dtoList, null, HttpStatus.OK);
    }

    public ResponseEntity<Object> getOnePost(Long id){
        Optional<Post> post = postRepository.findById(id);

        if(post.isEmpty()){
            return Response.build("post not found", null, null, HttpStatus.BAD_REQUEST);
        }

        PostDto postDto = PostDto.builder()
                .id(post.get().getId())
                .content(post.get().getContent())
                .createdAt(post.get().getCreatedAt())
                .build();

        return Response.build(Response.get("post"), postDto, null, HttpStatus.OK);
    }

    public ResponseEntity<Object> addPost(PostDto requestBody){
        Post post = Post.builder()
                .id(requestBody.getId())
                .content(requestBody.getContent()).build();

        postRepository.save(post);

        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();

        return Response.build(Response.create("post"), postDto, null, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> deletePost(Long id){
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
            return Response.build("post not found", null, null, HttpStatus.BAD_REQUEST);
        }

        postRepository.deleteById(id);

        return Response.build(Response.delete("post"), null, null, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updatePost(Long id, PostDto requestBody){
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
            return Response.build("post not found", null, null, HttpStatus.BAD_REQUEST);
        }

        if(!requestBody.getContent().isBlank()){
            post.get().setContent(requestBody.getContent());
        }

        postRepository.save(post.get());

        PostDto postDto = PostDto.builder()
                .id(post.get().getId())
                .content(post.get().getContent())
                .createdAt(post.get().getCreatedAt())
                .build();

        return Response.build(Response.update("post"), postDto, null, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> searchPosts(String keyword){
        List<Post> daoList = postRepository.searchPost(keyword);
        List<PostDto> dtoList = new ArrayList<>();

        daoList.forEach(post -> {
            PostDto postDto = PostDto.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .build();

            dtoList.add(postDto);
        });

        return Response.build("Search posts success", dtoList, null, HttpStatus.OK);
    }
}
