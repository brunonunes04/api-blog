package com.api.api_blog.repository;
import com.api.api_blog.model.PostModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostModel, UUID> {

    Page<PostModel> findByTituloContainingIgnoreCase(
            String titulo,
            Pageable pageable
    );
}
