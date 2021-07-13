package com.jkr.frontend.frontend1.service;

import com.jkr.frontend.frontend1.domain.Board;
import com.jkr.frontend.frontend1.response.BoardInitResponse;
import com.jkr.frontend.frontend1.util.Constant;
import com.jkr.frontend.frontend1.util.DiceClient;
import com.jkr.frontend.frontend1.util.ExceptionHandlerUtil;
import com.jkr.frontend.frontend1.util.Messages;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BoardServiceTest {

    @Mock
    DiceClient diceClient;

    @InjectMocks
    BoardService boardService;

    Board boardInit = Board.builder()
            .boardStatus(Constant.BOARD_STATUS_INIT)
            .boardWinnerName("")
            .playerNumber(2)
            .build();

    @Test
    public void checkWhenWhenPlayerNot() throws ExceptionHandlerUtil {
       boardService = new BoardService(new Board());
       ExceptionHandlerUtil ex = assertThrows(ExceptionHandlerUtil.class, () -> {
           ResponseEntity<BoardInitResponse> response = boardService.initBoard(1);
        });
        assertEquals(Messages.PLAYER_CAN_NOT_BE_LESS_THEN_TWO, ex.getMessage());
    }

    @Test
    public void checkWhenBoardStatusIsNotInit() throws ExceptionHandlerUtil {
        boardService = new BoardService(boardInit);
        ExceptionHandlerUtil ex = assertThrows(ExceptionHandlerUtil.class, () -> {
            ResponseEntity<BoardInitResponse> response = boardService.initBoard(2);
        });
        assertEquals(Messages.BOARD_ALREADY_INITIATE, ex.getMessage());
    }

}
