package com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response;

import org.springframework.data.domain.Page;

import java.util.Map;

public record ApiResponse<T>(
        Map<String, Object> summary,
        Page<T> orders) {
}
