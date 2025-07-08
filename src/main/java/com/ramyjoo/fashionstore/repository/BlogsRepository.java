package com.ramyjoo.fashionstore.repository;

import com.ramyjoo.fashionstore.model.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogsRepository extends JpaRepository<Blogs, Long> {


}
