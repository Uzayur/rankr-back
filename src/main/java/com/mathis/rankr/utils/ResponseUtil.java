package com.mathis.rankr.utils;

import com.mathis.rankr.models.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static ResponseEntity<CustomResponse> build(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new CustomResponse(status, message));
    }

    public static <T> ResponseEntity<T> body(HttpStatus status, T data) {
        return ResponseEntity.status(status).body(data);
    }
}
