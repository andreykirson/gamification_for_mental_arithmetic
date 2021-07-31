package com.example.gamification.game;

import com.example.gamification.badgeprocessors.BadgeProcessor;
import com.example.gamification.challenge.ChallengeSolvedDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private BadgeRepository badgeRepository;
    @Mock
    private List<BadgeProcessor> badgeProcessors;

    @Test
    public void whenAttemptIsCorrect() {
        //given
        ChallengeSolvedDTO challengeSolvedDTO = new ChallengeSolvedDTO(1, true, 10, 20, 1, "user");
        List<BadgeCard> badgeCards = List.of();
        ScoreCard scoreCard = new ScoreCard(challengeSolvedDTO.getUserId(), challengeSolvedDTO.getAttemptId());
        GameServiceImpl spyGameService = Mockito.spy(new GameServiceImpl(scoreRepository, badgeRepository, badgeProcessors));
        given(scoreRepository.save(scoreCard)).willReturn(scoreCard);
        //when
        Mockito.doReturn(badgeCards).when(spyGameService).processForBadges(challengeSolvedDTO);
        //then
        then(spyGameService.newAttemptForUser(challengeSolvedDTO)).isEqualTo(new GameService.GameResult(10, List.of()));
    }
}