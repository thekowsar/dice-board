package com.jkr.frontend.frontend1.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Board {

    private List<Player> playerList = new ArrayList<Player>();
    private String boardStatus, boardWinnerName;
    private int boardWinnerId, playerNumber;

}
