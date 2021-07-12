package com.jkr.frontend.frontend1.util;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExceptionHandlerUtil extends Exception{
    HttpStatus code;
    String message;
}
