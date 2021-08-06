package com.example.gamification.game;

import com.example.gamification.challenge.ChallengeSolvedEvent;
import lombok.Value;

import java.util.List;

public interface GameService {
    /**
     * @param challenge the challenge data with user details, factors, etc.
     * @return a {@link GameResult} object containing the new score and badge
     * cards obtained
     */
    GameResult newAttemptForUser(ChallengeSolvedEvent challenge);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }

}
