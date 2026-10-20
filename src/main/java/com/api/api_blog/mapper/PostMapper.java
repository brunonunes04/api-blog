package com.api.api_blog.mapper;

import com.api.api_blog.dto.request.ComentarioRequestDto;
import com.api.api_blog.dto.request.PostRequestDto;
import com.api.api_blog.dto.response.ComentarioResponseDto;
import com.api.api_blog.dto.response.PostResponseDto;
import com.api.api_blog.model.ComentarioModel;
import com.api.api_blog.model.PostModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper{
public PostModel toEntity(PostRequestDto dto) {
    return dto == null ? null : new PostModel(dto.autor(), dto.titulo(), dto.texto());
}

public PostResponseDto toDto(PostModel entity) {
    if (entity == null) {
        return null;
    } else {
        List<ComentarioResponseDto> comentariosDto = new ArrayList();
        if (entity.getComentarios() != null) {
            for(ComentarioModel comentario : entity.getComentarios()) {
                ComentarioResponseDto dto = ComentarioMapper.toResponseDto(comentario);
                comentariosDto.add(dto);
            }
        }

        return new PostResponseDto(entity.getId(), entity.getAutor(), entity.getData(), entity.getTitulo(), entity.getTexto(), comentariosDto);
    }
}

public void updateEntityFromDto(PostRequestDto dto, PostModel entity) {
    if (dto != null && entity != null) {
        entity.setAutor(dto.autor());
        entity.setTitulo(dto.titulo());
        entity.setTexto(dto.texto());
    }
}
}

