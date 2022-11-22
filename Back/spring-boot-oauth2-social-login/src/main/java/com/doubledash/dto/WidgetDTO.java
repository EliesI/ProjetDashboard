package com.doubledash.dto;

import lombok.Data;

@Data
public class WidgetDTO {

    private Long id;

    private Long user_id;

    private long widget_id;

    private int refresh_rate;

    private String param_1;

    private String param_2;

}
