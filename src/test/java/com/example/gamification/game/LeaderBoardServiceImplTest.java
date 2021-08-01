package com.example.gamification.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LeaderBoardServiceImplTest {

    LeaderBoardServiceImpl leaderBoardService;

    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private BadgeRepository badgeRepository;

    @BeforeEach
    public void setUp() {
        leaderBoardService = new LeaderBoardServiceImpl(scoreRepository, badgeRepository);
    }

    @Test
    public void getLeaderBoard() {
        //given
        List<String> badges = List.of("Silver", "Bronze", "Gold");
        List<LeaderBoardRow> leaderBoardRow = List.of(new LeaderBoardRow(1L, 20L));
        List<LeaderBoardRow> leaderBoardRowExpected = List.of(new LeaderBoardRow(1L, 20L, badges));
        List<BadgeCard> badgeCards = List.of(
                new BadgeCard(1L, BadgeType.SILVER),
                new BadgeCard(1L, BadgeType.BRONZE),
                new BadgeCard(1L, BadgeType.GOLD));
        given(scoreRepository.findFirst10()).willReturn(leaderBoardRow);
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(1L)).willReturn(badgeCards);
        //when
        List<LeaderBoardRow> leaderBoardRowResult = leaderBoardService.getCurrentLeaderBoard();
        //then
        then(leaderBoardRowResult).isEqualTo(leaderBoardRowExpected);
    }
}