package com.puta.template.crudunittest.domain.common;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T> {
    private LocalDateTime timestamp;
    private String code;
    private String message;
    private T data;
    private Set<String> errors;
}