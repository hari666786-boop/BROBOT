package com.brobot.future.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.data.AutopilotCatalog
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun AutopilotScreen(
    completed: Set<String>,
    onToggle: (String, Boolean) -> Unit,
    onInfo: () -> Unit
) {
    val total = AutopilotCatalog.tasks.size
    val done = AutopilotCatalog.tasks.count { it.id in completed }

    ScreenColumn {
        ScreenHeader(
            "Privacy Autopilot",
            "A guided checklist you complete yourself.",
            onInfo
        )

        DemoStatusBadge(
            "GUIDED DEMO — NO AUTOMATIC DEVICE CHANGES",
            BrobotColors.Amber
        )

        FuturisticCard(accent = BrobotColors.Teal) {
            Text(
                "$done of $total complete",
                fontWeight = FontWeight.Bold,
                color = BrobotColors.TextPrimary
            )

            LinearProgressIndicator(
                progress = { done / total.toFloat() },
                modifier = Modifier.fillMaxWidth(),
                color = BrobotColors.Teal,
                trackColor = BrobotColors.VioletDeep
            )

            Text(
                "Checking a box only records it in this demo session. BROBOT never changes your real security settings.",
                style = MaterialTheme.typography.bodySmall,
                color = BrobotColors.TextSecondary
            )
        }

        AutopilotCatalog.tasks.forEach { t ->
            val checked = t.id in completed

            FuturisticCard(
                accent = if (checked) BrobotColors.Teal else BrobotColors.Violet,
                onClick = { onToggle(t.id, !checked) }
            ) {
                Row(
                    Modifier.fillMaxWidth().heightIn(min = 48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { onToggle(t.id, it) }
                    )

                    Column(Modifier.padding(start = 8.dp)) {
                        Text(
                            t.title,
                            fontWeight = FontWeight.SemiBold,
                            color = BrobotColors.TextPrimary
                        )

                        Text(
                            t.detail,
                            style = MaterialTheme.typography.bodySmall,
                            color = BrobotColors.TextSecondary
                        )
                    }
                }
            }
        }
    }
}
