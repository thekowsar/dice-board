package com.jkr.frontend.frontend1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkr.frontend.frontend1.service.BoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DiceBoardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BoardService boardService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;

    @Test
    public void successfulPlayerInitRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/dice-board/01/player/number")
                .content(objectMapper.writeValueAsString(2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk());
    }

    @Test
    public void checkIfPlayerLessThenTwo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/dice-board/01/player/number")
                .content(objectMapper.writeValueAsString(1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(CONTENT_TYPE))
                .andExpect(status().isBadRequest());
    }

}