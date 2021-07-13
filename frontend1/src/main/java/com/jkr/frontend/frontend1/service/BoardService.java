package com.jkr.frontend.frontend1.service;

import com.jkr.frontend.frontend1.domain.Board;
import com.jkr.frontend.frontend1.domain.Player;
import com.jkr.frontend.frontend1.request.BoardStartRequest;
import com.jkr.frontend.frontend1.response.BoardInitResponse;
import com.jkr.frontend.frontend1.response.BoardRestartResponse;
import com.jkr.frontend.frontend1.response.BoardRollDiceResponse;
import com.jkr.frontend.frontend1.response.BoardStartResponse;
import com.jkr.frontend.frontend1.util.Constant;
import com.jkr.frontend.frontend1.util.DiceClient;
import com.jkr.frontend.frontend1.util.ExceptionHandlerUtil;
import com.jkr.frontend.frontend1.util.Messages;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
public class BoardService {

    //@Autowired
    //DiceService diceService;

    @Autowired
    DiceClient diceClient;

    private Board board = new Board();

    public BoardService (Board board){
        this.board = board;
    }

    public ResponseEntity<BoardInitResponse> initBoard(int playerNumber) throws ExceptionHandlerUtil {
        if(!Strings.isBlank(board.getBoardStatus())){
            log.debug(Messages.BOARD_ALREADY_INITIATE);
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, Messages.BOARD_ALREADY_INITIATE);
        }
        if(playerNumber < 2){
            log.debug(Messages.PLAYER_CAN_NOT_BE_LESS_THEN_TWO);
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, Messages.PLAYER_CAN_NOT_BE_LESS_THEN_TWO);
        }
        if(playerNumber > 4){
            log.debug(Messages.PLAYER_CAN_NOT_BE_GREATER_THEN_FOUR);
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, Messages.PLAYER_CAN_NOT_BE_GREATER_THEN_FOUR);
        }
        String doseDiceHolder = Constant.NO;
        List<Player> playerList = new ArrayList<Player>();

        // initiate players
        for(int i = 1; i <= playerNumber; i++){
            Player player = Player.builder()
                    .order(i)
                    .doseDiceHolder(doseDiceHolder)
                    .doseHitSix(Constant.NO)
                    .lastScore(0)
                    .playerName(Constant.PLAYER_DEAFaULT_NAME.concat(" ").concat(i+""))
                    .totalScore(0)
                    .sixAndNotFour(Constant.NO)
                    .build();
            playerList.add(player);
        }

        // initiate board
        board.setPlayerList(playerList);
        board.setBoardStatus(Constant.BOARD_STATUS_INIT);
        board.setBoardWinnerName("");
        board.setPlayerNumber(playerNumber);

        // build response
        BoardInitResponse response = BoardInitResponse.builder()
                .data(board)
                .userMessage(Messages.BOARD_INITIATE_SUCCESS)
                .build();

        // Return response
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<BoardStartResponse>  startBoard(BoardStartRequest request) throws ExceptionHandlerUtil {
        if(!board.getBoardStatus().equalsIgnoreCase(Constant.BOARD_STATUS_INIT)){
            log.debug(Messages.PLAYER_NUMBER_NOT_MATCHED);
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, Messages.PLAYER_NUMBER_NOT_MATCHED);

        }
        if(board.getPlayerList().size() != request.getPlayerList().size()){
            log.debug(Messages.PLAYER_NUMBER_NOT_MATCHED);
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, Messages.PLAYER_NUMBER_NOT_MATCHED);
        }

        for (Player userPlayer: request.getPlayerList()){
            for (Player boardPlayer: board.getPlayerList()){
                if(userPlayer.getOrder() == boardPlayer.getOrder()){
                    boardPlayer.setPlayerName(userPlayer.getPlayerName());
                }
                if(boardPlayer.getOrder() == 1){
                    boardPlayer.setDoseDiceHolder(Constant.YES);                }
            }
        }

        // board started
        board.setBoardStatus(Constant.BOARD_STATUS_RUNNING);

        // build response
        BoardStartResponse response = BoardStartResponse.builder()
                .data(board)
                .userMessage(Messages.BOARD_STARTED_SUCCESS)
                .build();

        // Return response
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<BoardRollDiceResponse>   rollDice() throws ExceptionHandlerUtil {
        if(board.getBoardStatus().equalsIgnoreCase(Constant.BOARD_STATUS_OVER)){
            log.debug(Messages.GAME_OVER);
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, Messages.GAME_OVER);

        }
        if(Strings.isBlank(board.getBoardStatus()) || !board.getBoardStatus().equalsIgnoreCase(Constant.BOARD_STATUS_RUNNING)){
            log.debug(Messages.BOARD_IS_NOT_RUNNING);
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, Messages.BOARD_IS_NOT_RUNNING);

        }
        BoardRollDiceResponse response = null;
        Player rollRequestPlayer = new Player();
        for(Player player : board.getPlayerList()){
            if(player.getDoseDiceHolder().equalsIgnoreCase(Constant.YES)){
                rollRequestPlayer = player;
            }
        }
        // int score = diceService.rollDice();
        int score = diceClient.rollDice();
        rollRequestPlayer.setLastScore(score);
        String doseDiceHolder = rollRequestPlayer.updateScore(score);

        board.getPlayerList().set(rollRequestPlayer.getOrder() -1, rollRequestPlayer);
        if(rollRequestPlayer.dosePassWinningScore(Constant.WINING_SCORE)){
            board.setBoardStatus(Constant.BOARD_STATUS_OVER);
            board.setBoardWinnerId(rollRequestPlayer.getOrder());
            board.setBoardWinnerName(rollRequestPlayer.getPlayerName());

            // build response
            response = BoardRollDiceResponse.builder()
                    .data(board)
                    .userMessage(Messages.GAVE_IS_OVER)
                    .build();
        }
        else if(doseDiceHolder.equalsIgnoreCase(Constant.NO)){
            int nextPlayerOrder = rollRequestPlayer.getOrder();
            if(nextPlayerOrder == board.getPlayerNumber()){
                nextPlayerOrder = 0;
            }
            Player nextPlayer = board.getPlayerList().get(nextPlayerOrder);
            nextPlayer.setDoseDiceHolder(Constant.YES);
            board.getPlayerList().set(nextPlayer.getOrder() -1, nextPlayer);
            // build response
            response = BoardRollDiceResponse.builder()
                    .data(board)
                    .userMessage(Messages.BOARD_IS_RUNNING)
                    .build();
        }
        else {
            // build response
            response = BoardRollDiceResponse.builder()
                    .data(board)
                    .userMessage(Messages.BOARD_IS_RUNNING)
                    .build();
        }

        log.info("Player name: {}", rollRequestPlayer.getPlayerName());
        log.info("Total Score: {}", rollRequestPlayer.getTotalScore());
        log.info("Current Value of Dice: {}", rollRequestPlayer.getLastScore());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<BoardRestartResponse> restartBoard() {
        board = new Board();

        // build response
        BoardRestartResponse response = BoardRestartResponse.builder()
                .data(board)
                .userMessage(Messages.BOARD_RESTART_SUCCESSFULLY)
                .build();

        // Return response
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
