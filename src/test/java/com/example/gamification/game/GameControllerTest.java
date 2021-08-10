package com.example.gamification.game;

import com.example.gamification.challenge.ChallengeSolvedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
class GameControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<ChallengeSolvedEvent> jsonRequestChallengeSolvedDTO;

    @Test
    void postValidResult() throws Exception {
        // given
        ChallengeSolvedEvent challengeSolvedEvent = new ChallengeSolvedEvent(1L, true, 10, 10, 1L, "user");
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestChallengeSolvedDTO.write(challengeSolvedEvent).getJson()))
                .andReturn()
                .getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}