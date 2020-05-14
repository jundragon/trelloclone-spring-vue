package com.trelloclone.web.result;

import org.springframework.http.ResponseEntity;

public final class Result {

    private Result() {

    }

    public static ResponseEntity<ApiResult> created() {
        return ResponseEntity.status(201).build();
    }

    public static ResponseEntity<ApiResult> failure(String errorMessage) {
        return ResponseEntity.badRequest().body(ApiResult.message(errorMessage));
    }
}
