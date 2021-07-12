package com.jkr.frontend.frontend1.request;

import com.jkr.frontend.frontend1.domain.Player;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardStartRequest {

    @NotNull(message = "Player list can not be null")
    private List<Player> playerList;

}
