package com.flamingo.predictandwin.data

/**
 * Centralized string and numeric constants used across the Predict & Win feature.
 * Keeping literals here avoids scattering magic values throughout composables.
 */
object Constants {

    // ── Screen Copy ────────────────────────────────────────────────────────────
    const val SCREEN_TITLE = "Predict & Win"
    const val SCREEN_SUBTITLE = "Forecast tomorrow's gold price movement"
    const val GOLD_PRICE_LABEL = "Current Gold Price"
    const val CHALLENGE_TITLE = "🎯  7-Day Challenge"
    const val MILESTONES_TITLE = "🏅  Reward Milestones"
    const val SENTIMENT_TITLE = "📊  Community Sentiment"
    const val PREDICTION_TITLE = "🔮  Your Prediction"

    // ── CTA & Bottom Sheet ─────────────────────────────────────────────────────
    const val CTA_LABEL = "Predict & Invest ₹50"
    const val SUCCESS_TITLE = "Prediction Submitted!"
    const val SUCCESS_BODY = "₹50 Digital Gold has been invested.\nResults will be announced tomorrow at 10 AM."
    const val SUCCESS_DISMISS = "Got it!"

    // ── Sentiment ──────────────────────────────────────────────────────────────
    const val BULLISH_LABEL = "Bullish"
    const val BEARISH_LABEL = "Bearish"
    const val SENTIMENT_FOOTER = "Based on 12,847 predictions today"

    // ── Stat Labels ────────────────────────────────────────────────────────────
    const val STAT_CORRECT = "Correct"
    const val STAT_WRONG = "Wrong"
    const val STAT_STREAK = "Streak"

    // ── Disclaimer ─────────────────────────────────────────────────────────────
    const val DISCLAIMER_TEXT =
        "Digital Gold is offered by a registered partner. " +
        "Investments are subject to market risks. " +
        "Past performance is not indicative of future results."
}
