package com.api.api_blog.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ComentarioRequestDto (
        @NotBlank(message = "{comentario.obrigatorio}")
        String comentario
){}
