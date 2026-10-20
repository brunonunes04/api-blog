package com.api.api_blog.controller;

import com.api.api_blog.dto.request.ComentarioRequestDto;
import com.api.api_blog.dto.request.PostRequestDto;
import com.api.api_blog.dto.response.ComentarioResponseDto;
import com.api.api_blog.dto.response.PostResponseDto;
import com.api.api_blog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Posts e comentários",
        description = "Operações do Blog API"
)
@RestController
@RequestMapping({"/api"})
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Lista posts com paginação"
    )
    @GetMapping({"/posts"})
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "data") String sortBy, @RequestParam(defaultValue = "desc") String direction, @RequestParam(required = false) String titulo) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(new String[]{sortBy}).ascending() : Sort.by(new String[]{sortBy}).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(this.postService.findAll(pageable, titulo));
        //return ResponseEntity.ok(this.postService.findAll(pageable));
    }

    @GetMapping({"/posts/{id}"})
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.postService.findById(id));
    }

    @PostMapping({"/newpost"})
    public ResponseEntity<PostResponseDto> createPost(@RequestBody @Valid PostRequestDto dto) {
        PostResponseDto created = this.postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping({"/editapost/{id}"})
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable UUID id, @RequestBody @Valid PostRequestDto dto) {
        return ResponseEntity.ok(this.postService.updatePost(id, dto));
    }

    @DeleteMapping({"/deletapost/{id}"})
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        this.postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping({"/comentarios/{postId}"})
    public ResponseEntity<ComentarioResponseDto> addComentario(@PathVariable UUID postId, @RequestBody @Valid ComentarioRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.postService.addComentario(postId, dto));
    }

    @DeleteMapping({"/deletacomentarios/{comentarioId}"})
    public ResponseEntity<Void> deleteComentario(@PathVariable UUID comentarioId) {
        this.postService.deleteComentario(comentarioId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping({"/updatecomentario/{id}"})
    public ResponseEntity<ComentarioResponseDto> updateComentario (@PathVariable UUID id, @RequestBody @Valid ComentarioRequestDto dto){
        return ResponseEntity.ok(this.postService.updateComentario(id, dto));
    }
}
