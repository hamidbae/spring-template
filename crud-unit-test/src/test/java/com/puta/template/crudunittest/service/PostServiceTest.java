package com.puta.template.crudunittest.service;

import com.puta.template.crudunittest.domain.common.ApiResponse;
import com.puta.template.crudunittest.domain.dao.Post;
import com.puta.template.crudunittest.domain.dto.PostDto;
import com.puta.template.crudunittest.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PostService.class)
public class PostServiceTest {
    @MockBean
    private PostRepository postRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private PostService postService;

    @Test
    void getAllPostSuccess_Test(){
        Post post = Post.builder()
                .id(1L)
                .content("New post 1")
                .createdAt(LocalDateTime.now())
                .build();

        List<Post> posts = new ArrayList<>();
        posts.add(post);

        when(postRepository.findAllSorted()).thenReturn(posts);

        ResponseEntity<Object> response = postService.getAllPosts();

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        List<PostDto> data = (List<PostDto>) Objects.requireNonNull(apiResponse).getData();

        assertEquals(1, data.size());
        assertEquals("Get posts success", apiResponse.getMessage());
        assertEquals("200", apiResponse.getCode());
        assertNotNull(apiResponse.getTimestamp());
        assertNull(apiResponse.getErrors());
    }

    @Test
    void getOnePostSuccess_Test(){
        Post post = Post.builder()
                .id(1L)
                .content("New post 1")
                .createdAt(LocalDateTime.now())
                .build();

        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(post));

        ResponseEntity<Object> response = postService.getOnePost(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        PostDto data = (PostDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals("Get post success", apiResponse.getMessage());
        assertEquals("200", apiResponse.getCode());
        assertNotNull(apiResponse.getTimestamp());
        assertNull(apiResponse.getErrors());
        assertNotNull(apiResponse.getData());
        assertEquals("New post 1", data.getContent());
        assertEquals(1L, data.getId());
        assertNotNull(data.getCreatedAt());

    }


    @Test
    void getOnePostNotFound_Test(){
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = postService.getOnePost(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        PostDto data = (PostDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals("Post not found", apiResponse.getMessage());
        assertEquals("400", apiResponse.getCode());
        assertNotNull(apiResponse.getTimestamp());
        assertNull(apiResponse.getErrors());
        assertNull(apiResponse.getData());
    }

    @Test
    void createPostSuccess_Test(){
        Post post = Post.builder()
                .id(1L)
                .content("New post 1")
                .createdAt(LocalDateTime.now())
                .build();

        PostDto postDto = PostDto.builder()
                .id(1L)
                .content("New post 1")
                .createdAt(LocalDateTime.now())
                .build();

        when(modelMapper.map(any(), eq(Post.class))).thenReturn(post);
        when(modelMapper.map(any(), eq(PostDto.class))).thenReturn(postDto);
        when(postRepository.save(any())).thenReturn(post);

        ResponseEntity<Object> response = postService.addPost(PostDto.builder().content("New post 1").build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        PostDto data = (PostDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals("Create post success", apiResponse.getMessage());
        assertEquals("201", apiResponse.getCode());
        assertNotNull(apiResponse.getTimestamp());
        assertNull(apiResponse.getErrors());
        assertNotNull(apiResponse.getData());
        assertEquals("New post 1", data.getContent());
        assertEquals(1L, data.getId());
        assertNotNull(data.getCreatedAt());
    }

    @Test
    void deletePostSuccess_Test(){
        Post post = Post.builder()
                .id(1L)
                .content("New post 1")
                .createdAt(LocalDateTime.now())
                .build();

        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(post));

        ResponseEntity<Object> response = postService.deletePost(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals("Delete post success", apiResponse.getMessage());
        assertEquals("201", apiResponse.getCode());
        assertNotNull(apiResponse.getTimestamp());
        assertNull(apiResponse.getErrors());
        assertNull(apiResponse.getData());
    }

    @Test
    void deletePostNotFound_Test(){
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = postService.deletePost(1L);

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals("Post not found", apiResponse.getMessage());
        assertEquals("400", apiResponse.getCode());
        assertNotNull(apiResponse.getTimestamp());
        assertNull(apiResponse.getErrors());
        assertNull(apiResponse.getData());
    }

    @Test
    void updatePostSuccess_Test(){
        Post post = Post.builder()
                .id(1L)
                .content("New post 1")
                .createdAt(LocalDateTime.now())
                .build();

        PostDto postDto = PostDto.builder()
                .id(1L)
                .content("Updated post 1")
                .createdAt(LocalDateTime.now())
                .build();


        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(post));
        when(modelMapper.map(any(), eq(PostDto.class))).thenReturn(postDto);

        ResponseEntity<Object> response = postService.updatePost(1L, PostDto.builder().content("Updated post 1").build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        PostDto data = (PostDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals("Update post success", apiResponse.getMessage());
        assertEquals("201", apiResponse.getCode());
        assertNotNull(apiResponse.getTimestamp());
        assertNull(apiResponse.getErrors());
        assertNotNull(apiResponse.getData());
        assertEquals("Updated post 1", data.getContent());
        assertEquals(1L, data.getId());
        assertNotNull(data.getCreatedAt());
    }


    @Test
    void updatePostNoContent_Test(){
        Post post = Post.builder()
                .id(1L)
                .content("New post 1")
                .createdAt(LocalDateTime.now())
                .build();

        PostDto postDto = PostDto.builder()
                .id(1L)
                .content("New post 1")
                .createdAt(LocalDateTime.now())
                .build();

        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(post));
        when(modelMapper.map(any(), eq(PostDto.class))).thenReturn(postDto);

        ResponseEntity<Object> response = postService.updatePost(1L, PostDto.builder().build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        PostDto data = (PostDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals("Update post success", apiResponse.getMessage());
        assertEquals("201", apiResponse.getCode());
        assertNotNull(apiResponse.getTimestamp());
        assertNull(apiResponse.getErrors());
        assertNotNull(apiResponse.getData());
        assertEquals("New post 1", data.getContent());
        assertEquals(1L, data.getId());
        assertNotNull(data.getCreatedAt());
    }


    @Test
    void updatePostNotFound_Test(){
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = postService.updatePost(1L, PostDto.builder().content("Updated post 1").build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        PostDto data = (PostDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals("Post not found", apiResponse.getMessage());
        assertEquals("400", apiResponse.getCode());
        assertNotNull(apiResponse.getTimestamp());
        assertNull(apiResponse.getErrors());
        assertNull(apiResponse.getData());
    }


    @Test
    void searchPostsSuccess_Test(){
        Post post = Post.builder()
                .id(1L)
                .content("New post 1")
                .createdAt(LocalDateTime.now())
                .build();

        List<Post> posts = new ArrayList<>();
        posts.add(post);

        Mockito.when(postRepository.searchPost("post")).thenReturn(posts);

        ResponseEntity<Object> response = postService.searchPosts("post");

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        List<PostDto> data = (List<PostDto>) Objects.requireNonNull(apiResponse).getData();

        assertEquals(1, data.size());
        assertEquals("Search posts success", apiResponse.getMessage());
        assertEquals("200", apiResponse.getCode());
        assertNotNull(apiResponse.getTimestamp());
        assertNull(apiResponse.getErrors());
    }
}
