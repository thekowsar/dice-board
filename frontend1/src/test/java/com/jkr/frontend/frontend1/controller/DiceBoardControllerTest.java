package com.jkr.frontend.frontend1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkr.frontend.frontend1.domain.Board;
import com.jkr.frontend.frontend1.domain.Player;
import com.jkr.frontend.frontend1.request.BoardStartRequest;
import com.jkr.frontend.frontend1.response.BoardInitResponse;
import com.jkr.frontend.frontend1.response.BoardStartResponse;
import com.jkr.frontend.frontend1.service.BoardService;
import com.jkr.frontend.frontend1.util.Constant;
import com.jkr.frontend.frontend1.util.ExceptionHandlerUtil;
import com.jkr.frontend.frontend1.util.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DiceBoardController.class)
public class DiceBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;

    Player player1 = Player.builder()
            .order(1)
            .doseDiceHolder(Constant.NO)
            .doseHitSix(Constant.NO)
            .lastScore(0)
            .playerName(Constant.PLAYER_DEAFaULT_NAME.concat(" ").concat(1+""))
            .totalScore(0)
            .sixAndNotFour(Constant.NO)
            .build();

    Player player2 = Player.builder()
            .order(2)
            .doseDiceHolder(Constant.NO)
            .doseHitSix(Constant.NO)
            .lastScore(0)
            .playerName(Constant.PLAYER_DEAFaULT_NAME.concat(" ").concat(2+""))
            .totalScore(0)
            .sixAndNotFour(Constant.NO)
            .build();
    List<Player> playerList = new ArrayList<Player>(Arrays.asList(player1, player2));

    Board board = Board.builder()
            .playerList(playerList)
            .boardStatus(Constant.BOARD_STATUS_INIT)
            .boardWinnerName("")
            .playerNumber(2)
            .build();

    // build response
    BoardInitResponse response = BoardInitResponse.builder()
            .data(board)
            .userMessage(Messages.BOARD_INITIATE_SUCCESS)
            .build();

    ResponseEntity<BoardInitResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

    List<Player> playerListForStart = new ArrayList<Player>(Arrays.asList(player1));
    BoardStartRequest boardStartRequest = BoardStartRequest.builder()
            .playerList(playerListForStart)
            .build();

    Board boardStarted = Board.builder()
            .playerList(playerList)
            .boardStatus(Constant.BOARD_STATUS_RUNNING)
            .boardWinnerName("")
            .playerNumber(2)
            .build();

    BoardStartResponse responseStarted = BoardStartResponse.builder()
            .data(boardStarted)
            .userMessage(Messages.BOARD_STARTED_SUCCESS)
            .build();

    ResponseEntity<BoardStartResponse> startedResponseEntity = new ResponseEntity<>(responseStarted, HttpStatus.OK);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
    }

    @Test
    public void whenPlayerLessThenTwoThrownBadRequest() throws Exception {
        when(boardService.initBoard(1)).thenThrow(new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, Messages.PLAYER_CAN_NOT_BE_LESS_THEN_TWO));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/dice-board/01/player/number")
                .content(objectMapper.writeValueAsString(1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void successfulPlayerInitRequestTest() throws Exception {
        when(boardService.initBoard(2)).thenReturn(responseEntity);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/dice-board/01/player/number")
                .content(objectMapper.writeValueAsString(2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPlayerCountMistatchThrownBadRequest() throws Exception {
        when(boardService.startBoard(ArgumentMatchers.any())).thenThrow(new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, Messages.PLAYER_NUMBER_NOT_MATCHED));
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/dice-board/02/start/board")
                .content(objectMapper.writeValueAsString(boardStartRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void successfullBoardStartRequestTest() throws Exception {
        when(boardService.startBoard(ArgumentMatchers.any())).thenReturn(startedResponseEntity);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/dice-board/02/start/board")
                .content(objectMapper.writeValueAsString(boardStartRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());
    }
}
