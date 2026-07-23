package com.flamingo.predictandwin.data

import androidx.compose.ui.graphics.Color
import com.flamingo.predictandwin.ui.theme.MilestoneBronze
import com.flamingo.predictandwin.ui.theme.MilestoneGold
import com.flamingo.predictandwin.ui.theme.MilestoneSilver

// ── Prediction direction ───────────────────────────────────────────────────────

enum class PredictionDirection(val label: String) {
    UP("UP"),
    DOWN("DOWN"),
}

// ── 7-Day Challenge ────────────────────────────────────────────────────────────

data class ChallengeProgress(
    val currentDay: Int = 3,
    val totalDays: Int = 7,
    val correctCount: Int = 2,
    val wrongCount: Int = 0,
    val currentStreak: Int = 2,
)

// ── Reward Milestones ──────────────────────────────────────────────────────────

data class RewardMilestone(
    val correctRequired: Int,
    val rewardLabel: String,
    val emoji: String,
    val tintColor: Color,
    val isAchieved: Boolean,
)

val mockMilestones = listOf(
    RewardMilestone(
        correctRequired = 3,
        rewardLabel = "1 Feather",
        emoji = "🪶",
        tintColor = MilestoneBronze,
        isAchieved = false,
    ),
    RewardMilestone(
        correctRequired = 5,
        rewardLabel = "₹50 Digital Gold",
        emoji = "🥇",
        tintColor = MilestoneSilver,
        isAchieved = false,
    ),
    RewardMilestone(
        correctRequired = 7,
        rewardLabel = "₹500 Digital Gold",
        emoji = "🏆",
        tintColor = MilestoneGold,
        isAchieved = false,
    ),
)

// ── Community Sentiment ────────────────────────────────────────────────────────

data class CommunitySentiment(
    val bullishPercent: Float = 0.65f,
) {
    val bearishPercent: Float get() = 1f - bullishPercent
}

// ── Gold Price ─────────────────────────────────────────────────────────────────

data class GoldPrice(
    val pricePerGram: String = "₹6,245.50",
    val changePercent: String = "+0.32%",
    val isPositive: Boolean = true,
)
