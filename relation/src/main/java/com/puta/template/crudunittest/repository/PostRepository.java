package com.puta.template.crudunittest.repository;


import com.puta.template.crudunittest.domain.dao.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
