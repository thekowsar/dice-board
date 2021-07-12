package com.jkr.frontend.frontend1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;

@RestController
@Validated
@Slf4j
@RequestMapping("/v1/dice-board")
public class DiceBoardController {

    @GetMapping(value = "/player/{number}")
    public ResponseEntity<String> getByOid(@PathVariable("number") @NotBlank String number) throws Exception {
        try{

            return new ResponseEntity<String>("response: " + number, HttpStatus.OK);
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

}
