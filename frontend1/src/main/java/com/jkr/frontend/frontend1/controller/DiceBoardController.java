package com.jkr.frontend.frontend1.controller;

import com.jkr.frontend.frontend1.request.BoardStartRequest;
import com.jkr.frontend.frontend1.response.BoardInitResponse;
import com.jkr.frontend.frontend1.response.BoardRestartResponse;
import com.jkr.frontend.frontend1.response.BoardRollDiceResponse;
import com.jkr.frontend.frontend1.response.BoardStartResponse;
import com.jkr.frontend.frontend1.service.BoardService;
import com.jkr.frontend.frontend1.util.Api;
import com.jkr.frontend.frontend1.util.ExceptionHandlerUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static com.jkr.frontend.frontend1.util.Api.*;

@RestController
@Validated
@Slf4j
@RequestMapping(value = DICE_BOARD)
@io.swagger.annotations.Api(value="Dice Roller", description="Online board game apis entry point")
public class DiceBoardController {

    @Autowired
    BoardService boardService;

    @ApiOperation(value = "Initiate game with number of player want to play")
    @PostMapping(value = PLAYER_NUMBER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoardInitResponse> getByOid(@Valid @RequestBody int playerNumber) throws Exception {
        try{
            log.info("Request received for board initiation for number of player: {}", playerNumber);
            ResponseEntity<BoardInitResponse> response = boardService.initBoard(playerNumber);
            log.info("Successfully board initiate with response: {}", response);
            return response;
        } catch (ExceptionHandlerUtil ex){
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }

    @ApiOperation(value = "Start game and update player name")
    @PutMapping(value = START_BOARD, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoardStartResponse> startBoard(@Valid @RequestBody BoardStartRequest request) throws Exception {
        try{
            log.info("Request received for board start: {}", request);
            ResponseEntity<BoardStartResponse> response = boardService.startBoard(request);
            log.info("Successfully board started with response: {}", response);
            return response;
        } catch (ExceptionHandlerUtil ex){
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }

    @ApiOperation(value = "Roll dice to get score")
    @GetMapping(value = ROLL_DICE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoardRollDiceResponse> rollDice() throws Exception {
        try{
            log.info("Request received for board start: ");
            ResponseEntity<BoardRollDiceResponse> response = boardService.rollDice();
            log.info("Roll dice response: {}", response);
            return response;
        } catch (ExceptionHandlerUtil ex){
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }

    @ApiOperation(value = "Restart game if user want")
    @PutMapping(value = RESTART_BOARD, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoardRestartResponse> startBoard() throws Exception {
        try{
            log.info("Request received for board restart: ");
            ResponseEntity<BoardRestartResponse> response = boardService.restartBoard();
            log.info("Successfully board restarted with response: {}", response);
            return response;
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

}
