package com.example.gamification.game;

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

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureJsonTesters
@ExtendWith(SpringExtension.class)
@WebMvcTest(LeaderBoardController.class)
class LeaderBoardControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    JacksonTester<List<LeaderBoardRow>> json;

    @MockBean
    LeaderBoardService leaderBoardService;

    @Test
    public void getLeaderBoard() throws Exception {
        // given
        List<String> badgesOne = List.of("First time");
        List<String> badgesTwo = List.of("Silver");
        List<LeaderBoardRow> leaderBoardRowExpected = List.of(
                new LeaderBoardRow(1L, 10L, badgesOne),
                new LeaderBoardRow(1L, 160L, badgesTwo)
        );
        // when
        given(leaderBoardService.getCurrentLeaderBoard()).willReturn(leaderBoardRowExpected);
        MockHttpServletResponse response = mvc.perform(get("/leaders").
                accept(MediaType.APPLICATION_JSON)).
                andReturn().
                getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(json.write(leaderBoardRowExpected).getJson());
    }
}