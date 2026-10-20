
package com.api.api_blog.service;

import com.api.api_blog.exceptions.RecursoNaoEncontradoException;
import com.api.api_blog.dto.request.ComentarioRequestDto;
import com.api.api_blog.dto.request.PostRequestDto;
import com.api.api_blog.dto.response.ComentarioResponseDto;
import com.api.api_blog.dto.response.PostResponseDto;
import com.api.api_blog.mapper.ComentarioMapper;
import com.api.api_blog.mapper.PostMapper;
import com.api.api_blog.model.ComentarioModel;
import com.api.api_blog.model.PostModel;
import com.api.api_blog.repository.ComentarioRepository;
import com.api.api_blog.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;
    private final MessageService messageService;

    public PostServiceImpl(
            PostRepository postRepository,
            PostMapper postMapper,
            ComentarioRepository comentarioRepository,
            ComentarioMapper comentarioMapper,
            MessageService messageService) {

        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.comentarioRepository = comentarioRepository;
        this.comentarioMapper = comentarioMapper;
        this.messageService = messageService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(post -> postMapper.toDto(post));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAll(
            Pageable pageable, String titulo) {

        Page<PostModel> posts;

        if (titulo == null || titulo.isBlank()) {
            posts = postRepository.findAll(pageable);
        } else {
            posts = postRepository
                    .findByTituloContainingIgnoreCase(titulo, pageable);
        }

        return posts.map(postMapper::toDto);
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(UUID id) {
        PostModel post = postRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        messageService.getMessage(
                                "post.nao.encontrado", id)
                ));

        return postMapper.toDto(post);
    }

    @Transactional
    public PostResponseDto createPost(PostRequestDto dto) {
        PostModel post = postMapper.toEntity(dto);
        PostModel saved = postRepository.save(post);

        return postMapper.toDto(saved);
    }

    @Transactional
    public PostResponseDto updatePost(
            UUID id, PostRequestDto dto) {

        PostModel post = postRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        messageService.getMessage(
                                "post.nao.encontrado", id)
                ));

        postMapper.updateEntityFromDto(dto, post);

        PostModel updatedPost = postRepository.save(post);

        return postMapper.toDto(updatedPost);
    }

    @Transactional
    public void deletePost(UUID id) {
        PostModel post = postRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        messageService.getMessage(
                                "post.nao.encontrado", id)
                ));

        postRepository.delete(post);
    }

    @Transactional
    public ComentarioResponseDto addComentario(
            UUID postId, ComentarioRequestDto dto) {

        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        messageService.getMessage(
                                "post.nao.encontrado", postId)
                ));

        ComentarioModel comentario =
                new ComentarioModel(dto.comentario(), post);

        post.adicionarComentario(comentario);

        ComentarioModel saved =
                comentarioRepository.save(comentario);

        return comentarioMapper.toDto(saved);
    }

    @Transactional
    public void deleteComentario(UUID comentarioId) {
        ComentarioModel comentario =
                comentarioRepository.findById(comentarioId)
                        .orElseThrow(() -> new RecursoNaoEncontradoException(
                                messageService.getMessage(
                                        "comentario.nao.encontrado",
                                        comentarioId)
                        ));

        comentarioRepository.delete(comentario);
    }

    @Transactional
    public ComentarioResponseDto updateComentario(
            UUID id, ComentarioRequestDto dto) {

        ComentarioModel comentario =
                comentarioRepository.findById(id)
                        .orElseThrow(() -> new RecursoNaoEncontradoException(
                                messageService.getMessage(
                                        "comentario.nao.encontrado", id)
                        ));

        comentarioMapper.updateEntityFromDto(dto, comentario);

        ComentarioModel updatedComentario =
                comentarioRepository.save(comentario);

        return comentarioMapper.toDto(updatedComentario);
    }
}
