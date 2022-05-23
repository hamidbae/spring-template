package com.puta.template.crudunittest.repository;


import com.puta.template.crudunittest.domain.dao.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    public List<Post> findAllSorted();

    @Query("SELECT p FROM Post p WHERE p.content LIKE %?1%"
            + " ORDER BY p.createdAt DESC")
    public List<Post> searchPost(String keyword);
}
