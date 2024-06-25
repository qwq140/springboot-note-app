package org.example.noteappserver.global.common.dto;

import lombok.Getter;

@Getter
public class CommonResponseDTO<T> {
    private final Integer code;
    private final String message;
    private final T data;

    public CommonResponseDTO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResponseDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }
}
