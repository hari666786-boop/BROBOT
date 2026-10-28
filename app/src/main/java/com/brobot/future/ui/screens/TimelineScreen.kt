package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.brobot.future.model.TimelineEvent
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun TimelineScreen(
    events: List<TimelineEvent>,
    onClear: () -> Unit,
    onInfo: () -> Unit
) {
    var openId by rememberSaveable { mutableStateOf<Int?>(null) }
    var confirmClear by rememberSaveable { mutableStateOf(false) }

    ScreenColumn {
        ScreenHeader(
            "Privacy Timeline",
            "What you explored in this demo session.",
            onInfo
        )

        DemoStatusBadge("DEMONSTRATION EVENTS · NOT REAL INCIDENTS")

        Text(
            "History is kept for this session only and may reset when the app restarts.",
            style = MaterialTheme.typography.bodySmall,
            color = BrobotColors.TextSecondary
        )

        if (events.isEmpty()) {
            FuturisticCard {
                Text(
                    "No demo history",
                    fontWeight = FontWeight.Bold,
                    color = BrobotColors.TextPrimary
                )

                Text(
                    "Run a scenario or apply an intervention and events will appear here.",
                    color = BrobotColors.TextSecondary
                )
            }
        }

        events.forEach { e ->
            FuturisticCard(onClick = { openId = e.id }) {
                Text(
                    "STEP ${e.sequence}",
                    style = MaterialTheme.typography.labelMedium,
                    color = BrobotColors.Lavender
                )

                Text(
                    e.title,
                    fontWeight = FontWeight.Bold,
                    color = BrobotColors.TextPrimary
                )

                Text(
                    "Tap to inspect",
                    style = MaterialTheme.typography.bodySmall,
                    color = BrobotColors.TextSecondary
                )
            }
        }

        PrimaryActionButton(
            "Clear Demo History",
            { confirmClear = true },
            Modifier.fillMaxWidth(),
            secondary = true,
            enabled = events.isNotEmpty()
        )
    }

    val open = events.firstOrNull { it.id == openId }

    if (open != null) {
        InfoDialog(
            title = open.title,
            sections = listOf(
                "Saved demonstration details" to open.details,
                "Note" to "Synthetic demo event — not a real security incident."
            ),
            onDismiss = { openId = null }
        )
    }

    if (confirmClear) {
        ConfirmDialog(
            title = "Clear demo history?",
            text = "This removes the demonstration events listed here. Your demo profile is not changed.",
            confirmLabel = "Clear History",
            onConfirm = {
                confirmClear = false
                onClear()
            },
            onDismiss = { confirmClear = false }
        )
    }
}
