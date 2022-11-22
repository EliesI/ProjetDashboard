package com.doubledash.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserByIdDTO {

    private Long id;

    private String email;

    private String displayName;

    @Size(min = 6, max = 120)
    private String password;

}
