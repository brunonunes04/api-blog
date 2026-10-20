package com.api.api_blog.repository;

import com.api.api_blog.model.ComentarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioModel, UUID>{


}
