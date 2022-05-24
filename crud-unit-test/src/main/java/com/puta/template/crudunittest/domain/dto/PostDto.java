package com.puta.template.crudunittest.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    @NotBlank(message = "Content is required!")
    @Size(min = 2, max = 100, message = "The length of content at least 3 char")
    private String content;

    private LocalDateTime createdAt;
}