package com.example.gamification.badgeprocessors;

import com.example.gamification.challenge.ChallengeSolvedDTO;
import com.example.gamification.game.BadgeType;
import com.example.gamification.game.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LuckyNumberBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(
            int currentScore,
            List<ScoreCard> scoreCardList,
            ChallengeSolvedDTO solved
    ) {
        return (solved.getFactorA()==42 || solved.getFactorB()==42) ?
                Optional.of(BadgeType.LUCKY_NUMBER) :
                Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return null;
    }
}
