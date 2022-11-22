package com.doubledash.dto;

import lombok.Data;

@Data
public class TweetDTO {
    private int rt;

    private String contenu;

    private int replies;
}
