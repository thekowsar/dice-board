package com.jkr.frontend.frontend1.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class DiceService {
    private Random random;

    public DiceService(){
        random = new Random();
    }

    public int rollDice(){
        int dice = random.nextInt(6) + 1;
        return dice;
    }
}
