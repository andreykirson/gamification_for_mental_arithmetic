package com.example.gamification.game;

import com.example.gamification.badgeprocessors.BadgeProcessor;
import com.example.gamification.challenge.ChallengeSolvedDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    GameServiceImpl gameService;
    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private BadgeRepository badgeRepository;
    @Mock
    private BadgeProcessor badgeProcessors;

    @BeforeEach
    public void setUp() {
        gameService = new GameServiceImpl(scoreRepository, badgeRepository, List.of(badgeProcessors));
    }

    @Test
    public void whenAttemptIsCorrect() {
        //given
        ChallengeSolvedDTO challengeSolvedDTO = new ChallengeSolvedDTO(1, true, 10, 20, 1, "user");
        ScoreCard scoreCard = new ScoreCard(challengeSolvedDTO.getUserId(), challengeSolvedDTO.getAttemptId());
        given(scoreRepository.save(scoreCard)).willReturn(scoreCard);
        given(scoreRepository.getTotalScoreForUser(1L)).willReturn(Optional.of(10));
        given(scoreRepository.findByUserIdOrderByScoreTimestampDesc(1L)).willReturn(List.of(scoreCard));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(1L)).
                willReturn(List.of());
        given(badgeProcessors.badgeType()).willReturn(BadgeType.FIRST_WON);
        given(badgeProcessors.processForOptionalBadge(10, List.of(scoreCard), challengeSolvedDTO)).
                willReturn(Optional.of(BadgeType.FIRST_WON));
        //when
        final GameService.GameResult gameResult = gameService.newAttemptForUser(challengeSolvedDTO);
        //then
        then(gameResult).isEqualTo(new GameService.GameResult(10, List.of(BadgeType.FIRST_WON)));
    }

    @Test
    public void whenAttemptIsNotCorrect() {
        //given
        ChallengeSolvedDTO challengeSolvedDTO = new ChallengeSolvedDTO(1, false, 10, 20, 1, "user");
        // when
        final GameService.GameResult gameResult = gameService.newAttemptForUser(challengeSolvedDTO);
        //then
        then(gameResult).isEqualTo(new GameService.GameResult(0, List.of()));

    }
}