package com.fitzone.tracker.dto;

public record MemberSummary(
        String memberId,
        String memberName,
        int totalWorkouts,
        int totalMinutes,
        int totalCalories,
        double averageMinutesPerWorkout
) {
}
