package com.charter.user.rewards.model;

import lombok.Data;

@Data
public class RewardsCalculatorResponse {

    private Long userId;
    private Long rewardPointsForPastMonth;
    private Long rewardPointsForSecondPastMonth;
    private Long rewardPointsForThirdPastMonth;
    private Long totalRewardPoints;

}
