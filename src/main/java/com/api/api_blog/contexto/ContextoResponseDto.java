package com.api.api_blog.contexto;

public record ContextoResponseDto(
    String locale,
    String timezone,
    String data,
    String moeda,
    String numero
    ){}
