package com.jkr.frontend.frontend1.response;

import com.jkr.frontend.frontend1.domain.Board;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardInitResponse {

    private Board data;
    private String userMessage;

}
