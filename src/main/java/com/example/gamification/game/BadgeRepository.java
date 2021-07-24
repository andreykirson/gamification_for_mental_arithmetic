package com.example.gamification.game;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BadgeRepository extends CrudRepository<BadgeCard, Long> {
    /**
     * Retrieves all BadgeCards for a given user.
     *
     * @param userId the id of the user to look for BadgeCards
     * @return the list of BadgeCards, ordered by most recent first.
     */
    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(Long userId);
}
