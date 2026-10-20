package com.api.api_blog.service;

import com.api.api_blog.dto.request.ComentarioRequestDto;
import com.api.api_blog.dto.request.PostRequestDto;
import com.api.api_blog.dto.response.ComentarioResponseDto;
import com.api.api_blog.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Page<PostResponseDto> findAll(Pageable pageable);

    Page<PostResponseDto> findAll(Pageable pageable, String titulo);

    PostResponseDto findById(UUID id);

    PostResponseDto createPost(PostRequestDto dto);

    PostResponseDto updatePost(UUID id, PostRequestDto dto);

    void deletePost(UUID id);

    ComentarioResponseDto addComentario(UUID postId, ComentarioRequestDto dto);

    void deleteComentario(UUID comentarioId);

    ComentarioResponseDto updateComentario(UUID id, ComentarioRequestDto dto);
}
