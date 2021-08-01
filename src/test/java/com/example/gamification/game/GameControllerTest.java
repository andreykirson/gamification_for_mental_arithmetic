package com.example.gamification.game;

import com.example.gamification.challenge.ChallengeSolvedDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<ChallengeSolvedDTO> jsonRequestChallengeSolvedDTO;

    @Test
    void postValidResult() throws Exception {
        // given
        ChallengeSolvedDTO challengeSolvedDTO = new ChallengeSolvedDTO(1L, true, 10, 10, 1L, "user");
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestChallengeSolvedDTO.write(challengeSolvedDTO).getJson()))
                .andReturn()
                .getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}