package com.flamingo.predictandwin.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for the mock data models used by the Predict & Win feature.
 * Validates default values, computed properties, and data consistency.
 */
class MockDataTest {

    // ── PredictionDirection ────────────────────────────────────────────────────

    @Test
    fun `PredictionDirection UP has correct label`() {
        assertEquals("UP", PredictionDirection.UP.label)
    }

    @Test
    fun `PredictionDirection DOWN has correct label`() {
        assertEquals("DOWN", PredictionDirection.DOWN.label)
    }

    @Test
    fun `PredictionDirection has exactly two values`() {
        assertEquals(2, PredictionDirection.entries.size)
    }

    // ── ChallengeProgress ──────────────────────────────────────────────────────

    @Test
    fun `ChallengeProgress defaults are valid`() {
        val progress = ChallengeProgress()
        assertTrue(progress.currentDay in 1..progress.totalDays)
        assertTrue(progress.correctCount >= 0)
        assertTrue(progress.wrongCount >= 0)
        assertTrue(progress.currentStreak >= 0)
        assertEquals(7, progress.totalDays)
    }

    @Test
    fun `ChallengeProgress correct plus wrong does not exceed completed days`() {
        val progress = ChallengeProgress()
        val completedDays = progress.currentDay - 1
        assertTrue(progress.correctCount + progress.wrongCount <= completedDays)
    }

    // ── CommunitySentiment ─────────────────────────────────────────────────────

    @Test
    fun `CommunitySentiment percentages sum to 1`() {
        val sentiment = CommunitySentiment()
        val sum = sentiment.bullishPercent + sentiment.bearishPercent
        assertEquals(1.0f, sum, 0.001f)
    }

    @Test
    fun `CommunitySentiment percentages are in valid range`() {
        val sentiment = CommunitySentiment()
        assertTrue(sentiment.bullishPercent in 0f..1f)
        assertTrue(sentiment.bearishPercent in 0f..1f)
    }

    // ── RewardMilestone ────────────────────────────────────────────────────────

    @Test
    fun `mockMilestones has three entries`() {
        assertEquals(3, mockMilestones.size)
    }

    @Test
    fun `mockMilestones are sorted by correctRequired ascending`() {
        val sorted = mockMilestones.sortedBy { it.correctRequired }
        assertEquals(sorted, mockMilestones)
    }

    @Test
    fun `each milestone has a non-empty reward label`() {
        mockMilestones.forEach { milestone ->
            assertTrue(milestone.rewardLabel.isNotBlank())
        }
    }

    @Test
    fun `each milestone has a non-empty emoji`() {
        mockMilestones.forEach { milestone ->
            assertTrue(milestone.emoji.isNotBlank())
        }
    }

    // ── GoldPrice ──────────────────────────────────────────────────────────────

    @Test
    fun `GoldPrice defaults are populated`() {
        val price = GoldPrice()
        assertTrue(price.pricePerGram.isNotBlank())
        assertTrue(price.changePercent.isNotBlank())
        assertNotNull(price.isPositive)
    }

    @Test
    fun `GoldPrice changePercent starts with sign`() {
        val price = GoldPrice()
        assertTrue(
            price.changePercent.startsWith("+") || price.changePercent.startsWith("-")
        )
    }
}
