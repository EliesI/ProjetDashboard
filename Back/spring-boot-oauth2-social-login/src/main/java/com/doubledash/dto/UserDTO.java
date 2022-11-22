package com.doubledash.dto;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UserDTO {

    private String email;

    private String displayName;

    @Size(min = 6, max = 120)
    private String password;

}
