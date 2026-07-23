package com.flamingo.predictandwin.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flamingo.predictandwin.data.ChallengeProgress
import com.flamingo.predictandwin.data.CommunitySentiment
import com.flamingo.predictandwin.data.Constants
import com.flamingo.predictandwin.data.GoldPrice
import com.flamingo.predictandwin.data.PredictionDirection
import com.flamingo.predictandwin.data.RewardMilestone
import com.flamingo.predictandwin.data.mockMilestones
import com.flamingo.predictandwin.ui.components.LivePriceIndicator
import com.flamingo.predictandwin.ui.theme.BearishRed
import com.flamingo.predictandwin.ui.theme.BearishRedDark
import com.flamingo.predictandwin.ui.theme.BullishGreen
import com.flamingo.predictandwin.ui.theme.BullishGreenDark
import com.flamingo.predictandwin.ui.theme.GoldDark
import com.flamingo.predictandwin.ui.theme.GoldLight
import com.flamingo.predictandwin.ui.theme.GoldPrimary
import com.flamingo.predictandwin.ui.theme.PredictAndWinTheme
import com.flamingo.predictandwin.ui.theme.SurfaceCard
import com.flamingo.predictandwin.ui.theme.SurfaceCardElevated
import com.flamingo.predictandwin.ui.theme.SurfaceDark
import com.flamingo.predictandwin.ui.theme.SurfaceOverlay
import com.flamingo.predictandwin.ui.theme.TextMuted
import com.flamingo.predictandwin.ui.theme.TextPrimary
import com.flamingo.predictandwin.ui.theme.TextSecondary

// ════════════════════════════════════════════════════════════════════════════════
// Root Composable — PredictAndWinScreen
// ════════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictAndWinScreen() {
    // ── State ──────────────────────────────────────────────────────────────────
    var selectedDirection by remember { mutableStateOf<PredictionDirection?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // ── Mock data ──────────────────────────────────────────────────────────────
    val goldPrice = remember { GoldPrice() }
    val challengeProgress = remember { ChallengeProgress() }
    val sentiment = remember { CommunitySentiment() }
    val milestones = remember { mockMilestones }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceDark),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // 1. Header & Gold Price
            HeaderSection(goldPrice = goldPrice)

            // 2. 7-Day Challenge Card
            ChallengeCard(progress = challengeProgress)

            // 3. Reward Milestones
            RewardMilestonesRow(milestones = milestones, currentCorrect = challengeProgress.correctCount)

            // 4. Community Sentiment
            CommunitySentimentCard(sentiment = sentiment)

            // 5. Prediction Selection
            PredictionSelector(
                selected = selectedDirection,
                onSelect = { selectedDirection = it },
            )

            // 6. CTA Button
            PredictButton(
                enabled = selectedDirection != null,
                onClick = { showBottomSheet = true },
            )
        }

        // 7. Success Bottom Sheet
        if (showBottomSheet) {
            SuccessBottomSheet(
                direction = selectedDirection!!,
                sheetState = sheetState,
                onDismiss = { showBottomSheet = false },
            )
        }
    }
}

// ════════════════════════════════════════════════════════════════════════════════
// 1. Header & Gold Price
// ════════════════════════════════════════════════════════════════════════════════

@Composable
private fun HeaderSection(goldPrice: GoldPrice) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = Constants.SCREEN_TITLE,
            style = MaterialTheme.typography.headlineLarge,
            color = GoldPrimary,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = Constants.SCREEN_SUBTITLE,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Gold price banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                GoldDark.copy(alpha = 0.3f),
                                SurfaceCard,
                            ),
                        ),
                    )
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = Constants.GOLD_PRICE_LABEL,
                        style = MaterialTheme.typography.labelMedium,
                        color = TextMuted,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${goldPrice.pricePerGram} / gm",
                        style = MaterialTheme.typography.headlineMedium,
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                    )
                }

                // Price change badge
                val badgeColor = if (goldPrice.isPositive) BullishGreen else BearishRed
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(badgeColor.copy(alpha = 0.15f))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                ) {
                    Text(
                        text = goldPrice.changePercent,
                        style = MaterialTheme.typography.labelLarge,
                        color = badgeColor,
                    )
                }
            }
        }

        // Live price indicator
        LivePriceIndicator(lastUpdated = "2 min ago")
    }
}

// ════════════════════════════════════════════════════════════════════════════════
// 2. 7-Day Challenge Card
// ════════════════════════════════════════════════════════════════════════════════

@Composable
private fun ChallengeCard(progress: ChallengeProgress) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Title row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = Constants.CHALLENGE_TITLE,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(GoldPrimary.copy(alpha = 0.15f))
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                ) {
                    Text(
                        text = "Day ${progress.currentDay} of ${progress.totalDays}",
                        style = MaterialTheme.typography.labelMedium,
                        color = GoldPrimary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress bar
            val animatedProgress by animateFloatAsState(
                targetValue = progress.currentDay.toFloat() / progress.totalDays.toFloat(),
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                label = "challenge_progress",
            )
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = GoldPrimary,
                trackColor = SurfaceOverlay,
                strokeCap = StrokeCap.Round,
            )

            // Day dots
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                for (day in 1..progress.totalDays) {
                    val isCompleted = day < progress.currentDay
                    val isCurrent = day == progress.currentDay
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isCompleted -> GoldPrimary
                                    isCurrent -> GoldPrimary.copy(alpha = 0.4f)
                                    else -> SurfaceOverlay
                                },
                            )
                            .then(
                                if (isCurrent) Modifier.border(2.dp, GoldPrimary, CircleShape)
                                else Modifier
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "$day",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isCompleted || isCurrent) Color.White else TextMuted,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Stats row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                StatChip(
                    icon = Icons.Default.CheckCircle,
                    label = Constants.STAT_CORRECT,
                    value = "${progress.correctCount}",
                    tint = BullishGreen,
                )
                StatChip(
                    icon = Icons.Default.Close,
                    label = Constants.STAT_WRONG,
                    value = "${progress.wrongCount}",
                    tint = BearishRed,
                )
                StatChip(
                    icon = Icons.Default.LocalFireDepartment,
                    label = Constants.STAT_STREAK,
                    value = "${progress.currentStreak}",
                    tint = GoldPrimary,
                )
            }
        }
    }
}

@Composable
private fun StatChip(
    icon: ImageVector,
    label: String,
    value: String,
    tint: Color,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(tint.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = tint,
                modifier = Modifier.size(24.dp),
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TextMuted,
        )
    }
}

// ════════════════════════════════════════════════════════════════════════════════
// 3. Reward Milestones
// ════════════════════════════════════════════════════════════════════════════════

@Composable
private fun RewardMilestonesRow(milestones: List<RewardMilestone>, currentCorrect: Int) {
    Column {
        Text(
            text = Constants.MILESTONES_TITLE,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            milestones.forEach { milestone ->
                MilestoneCard(
                    milestone = milestone,
                    isUnlocked = currentCorrect >= milestone.correctRequired,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun MilestoneCard(
    milestone: RewardMilestone,
    isUnlocked: Boolean,
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "glow_alpha",
    )

    Card(
        modifier = modifier.then(
            if (isUnlocked) Modifier.border(
                width = 1.5.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        milestone.tintColor.copy(alpha = glowAlpha),
                        milestone.tintColor.copy(alpha = glowAlpha * 0.5f),
                    ),
                ),
                shape = RoundedCornerShape(14.dp),
            ) else Modifier
        ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) SurfaceCardElevated else SurfaceCard,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = milestone.emoji,
                fontSize = 28.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${milestone.correctRequired} Correct",
                style = MaterialTheme.typography.labelMedium,
                color = if (isUnlocked) milestone.tintColor else TextMuted,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = milestone.rewardLabel,
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                maxLines = 2,
            )
        }
    }
}

// ════════════════════════════════════════════════════════════════════════════════
// 4. Community Sentiment
// ════════════════════════════════════════════════════════════════════════════════

@Composable
private fun CommunitySentimentCard(sentiment: CommunitySentiment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = Constants.SENTIMENT_TITLE,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Percentage labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                        contentDescription = Constants.BULLISH_LABEL,
                        tint = BullishGreen,
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = Constants.BULLISH_LABEL,
                        style = MaterialTheme.typography.labelMedium,
                        color = BullishGreen,
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${(sentiment.bullishPercent * 100).toInt()}%",
                        style = MaterialTheme.typography.titleSmall,
                        color = BullishGreen,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${(sentiment.bearishPercent * 100).toInt()}%",
                        style = MaterialTheme.typography.titleSmall,
                        color = BearishRed,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = Constants.BEARISH_LABEL,
                        style = MaterialTheme.typography.labelMedium,
                        color = BearishRed,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.TrendingDown,
                        contentDescription = Constants.BEARISH_LABEL,
                        tint = BearishRed,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Split progress bar
            val animatedBullish by animateFloatAsState(
                targetValue = sentiment.bullishPercent,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                label = "bullish",
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp)),
            ) {
                Box(
                    modifier = Modifier
                        .weight(animatedBullish)
                        .height(12.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(BullishGreenDark, BullishGreen),
                            ),
                        ),
                )
                Box(
                    modifier = Modifier
                        .weight(1f - animatedBullish)
                        .height(12.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(BearishRed, BearishRedDark),
                            ),
                        ),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = Constants.SENTIMENT_FOOTER,
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

// ════════════════════════════════════════════════════════════════════════════════
// 5. Prediction Selector (UP / DOWN)
// ════════════════════════════════════════════════════════════════════════════════

@Composable
private fun PredictionSelector(
    selected: PredictionDirection?,
    onSelect: (PredictionDirection) -> Unit,
) {
    Column {
        Text(
            text = Constants.PREDICTION_TITLE,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DirectionButton(
                direction = PredictionDirection.UP,
                isSelected = selected == PredictionDirection.UP,
                onClick = { onSelect(PredictionDirection.UP) },
                modifier = Modifier.weight(1f),
            )
            DirectionButton(
                direction = PredictionDirection.DOWN,
                isSelected = selected == PredictionDirection.DOWN,
                onClick = { onSelect(PredictionDirection.DOWN) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun DirectionButton(
    direction: PredictionDirection,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isUp = direction == PredictionDirection.UP
    val activeColor = if (isUp) BullishGreen else BearishRed
    val darkColor = if (isUp) BullishGreenDark else BearishRedDark

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) activeColor else SurfaceOverlay,
        animationSpec = tween(300),
        label = "border",
    )
    val bgAlpha by animateFloatAsState(
        targetValue = if (isSelected) 0.15f else 0.0f,
        animationSpec = tween(300),
        label = "bg",
    )

    Card(
        modifier = modifier
            .height(100.dp)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp),
            )
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) activeColor.copy(alpha = bgAlpha) else SurfaceCard,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = if (isUp) Icons.AutoMirrored.Filled.TrendingUp else Icons.AutoMirrored.Filled.TrendingDown,
                contentDescription = direction.label,
                tint = if (isSelected) activeColor else TextMuted,
                modifier = Modifier.size(32.dp),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = direction.label,
                style = MaterialTheme.typography.titleMedium,
                color = if (isSelected) activeColor else TextSecondary,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

// ════════════════════════════════════════════════════════════════════════════════
// 6. CTA Button
// ════════════════════════════════════════════════════════════════════════════════

@Composable
private fun PredictButton(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldPrimary,
            contentColor = Color.Black,
            disabledContainerColor = SurfaceOverlay,
            disabledContentColor = TextMuted,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp,
        ),
    ) {
        Text(
            text = Constants.CTA_LABEL,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

// ════════════════════════════════════════════════════════════════════════════════
// 7. Success Bottom Sheet
// ════════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessBottomSheet(
    direction: PredictionDirection,
    sheetState: androidx.compose.material3.SheetState,
    onDismiss: () -> Unit,
) {
    val isUp = direction == PredictionDirection.UP
    val accentColor = if (isUp) BullishGreen else BearishRed

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SurfaceCard,
        dragHandle = { BottomSheetDefaults.DragHandle(color = TextMuted) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Success icon with animated glow
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Success",
                    tint = accentColor,
                    modifier = Modifier.size(48.dp),
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = Constants.SUCCESS_TITLE,
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Direction badge
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.1f))
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = if (isUp) Icons.AutoMirrored.Filled.TrendingUp else Icons.AutoMirrored.Filled.TrendingDown,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "You predicted ${direction.label}",
                    style = MaterialTheme.typography.titleMedium,
                    color = accentColor,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = Constants.SUCCESS_BODY,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldPrimary,
                    contentColor = Color.Black,
                ),
            ) {
                Text(
                    text = Constants.SUCCESS_DISMISS,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

// ════════════════════════════════════════════════════════════════════════════════
// Preview
// ════════════════════════════════════════════════════════════════════════════════

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PredictAndWinScreenPreview() {
    PredictAndWinTheme {
        PredictAndWinScreen()
    }
}
