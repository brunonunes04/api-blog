package com.api.api_blog.mapper;

import com.api.api_blog.dto.request.ComentarioRequestDto;
import com.api.api_blog.dto.request.PostRequestDto;
import com.api.api_blog.dto.response.ComentarioResponseDto;
import com.api.api_blog.model.ComentarioModel;
import com.api.api_blog.model.PostModel;
import org.springframework.stereotype.Component;

@Component
public class ComentarioMapper {
    public static ComentarioResponseDto toResponseDto(ComentarioModel comentario) {
        return comentario == null ? null : new ComentarioResponseDto(comentario.getId(), comentario.getData(), comentario.getComentario());
    }

    public ComentarioModel toEntity(ComentarioRequestDto dto, PostModel entity) {
        return dto == null ? null : new ComentarioModel(dto.comentario().toString(), entity);
    }

    public ComentarioResponseDto toDto(ComentarioModel entity) {
        return entity == null ? null : new ComentarioResponseDto(entity.getId(), entity.getData(), entity.getComentario());
    }

    public void updateEntityFromDto(ComentarioRequestDto dto, ComentarioModel entity) {
        if (dto != null && entity != null) {
            entity.setComentario(dto.comentario());
        }
    }
}
