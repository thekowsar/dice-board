package com.jkr.frontend.frontend1.util;

import com.jkr.frontend.frontend1.response.DiceClientResponse;
import com.jkr.frontend.frontend1.service.DiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;


@Component
@Slf4j
public class DiceClient {

    @Autowired
    DiceService diceService;

    public int rollDice(){
        WebClient client = WebClient.create();
        DiceClientResponse response = null;
        try {
            response = client.get()
                    .uri(new URI(Api.DICE_URL))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(DiceClientResponse.class)
                    .block();
        } catch (Exception e) {
            log.error("An exception occurred during getting dice score", e);
            return diceService.rollDice();
        }

        return  response.getScore();
    }
}
