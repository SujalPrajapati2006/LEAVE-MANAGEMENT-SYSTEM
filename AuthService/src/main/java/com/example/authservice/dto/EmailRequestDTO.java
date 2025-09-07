package com.example.authservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDTO implements Serializable {

    private String to;
    private String subject;
    private String body;
}
