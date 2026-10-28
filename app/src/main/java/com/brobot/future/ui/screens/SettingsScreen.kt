package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

const val APP_VERSION = "1.0.0-prototype"

@Composable
fun SettingsScreen(
    largeText: Boolean,
    onLargeText: (Boolean) -> Unit,
    reduceMotion: Boolean,
    onReduceMotion: (Boolean) -> Unit,
    onResetAll: () -> Unit,
    onClearHistory: () -> Unit
) {
    var confirmReset by rememberSaveable { mutableStateOf(false) }
    var confirmClear by rememberSaveable { mutableStateOf(false) }
    var showLimits by rememberSaveable { mutableStateOf(false) }

    ScreenColumn {
        ScreenHeader(
            "Settings & About",
            "BROBOT 2035 · concept prototype"
        )

        DemoStatusBadge("DEMO MODE · ACTIVE")

        FuturisticCard {
            SectionTitle("About BROBOT 2035")

            Text(
                "BROBOT is a concept prototype that imagines how personal privacy technology could work in 2035.",
                color = BrobotColors.TextPrimary
            )

            SectionTitle("Product vision")

            Text(
                "Make invisible digital privacy risks understandable, visual and interactive.",
                color = BrobotColors.TextSecondary
            )

            SectionTitle("App version")

            Text(APP_VERSION, color = BrobotColors.TextPrimary)
        }

        FuturisticCard {
            SectionTitle("Accessibility")
            ToggleRow("Larger text", largeText, onLargeText)
            ToggleRow("Reduce motion", reduceMotion, onReduceMotion)
        }

        FuturisticCard {
            SectionTitle("Demo data")

            PrimaryActionButton(
                "Clear Local Demo History",
                { confirmClear = true },
                Modifier.fillMaxWidth(),
                secondary = true
            )

            PrimaryActionButton(
                "Reset All Demo Data",
                { confirmReset = true },
                Modifier.fillMaxWidth()
            )
        }

        FuturisticCard(
            accent = BrobotColors.Amber,
            onClick = { showLimits = !showLimits }
        ) {
            SectionTitle(
                "Privacy and limitations (tap to ${if (showLimits) "hide" else "show"})"
            )

            if (showLimits) {
                listOf(
                    "Concept prototype. Not a security product.",
                    "Uses synthetic data and a local, deterministic rules engine.",
                    "Does not scan your device, accounts, apps or networks.",
                    "Does not detect breaches or prevent attacks.",
                    "No live AI model is connected; explanations are template-based.",
                    "Requests no Android permissions and has no internet permission.",
                    "Collects no data, has no analytics and sends nothing to a server.",
                    "Demo history and settings may reset when the app restarts.",
                    "Scores shown are simple demo counts, not validated probabilities."
                ).forEach {
                    Text(
                        "• $it",
                        color = BrobotColors.TextPrimary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

    if (confirmReset) {
        ConfirmDialog(
            "Reset all demo data?",
            "This restores the default demo profile and clears scenarios, history, checklist and notes.",
            "Reset",
            {
                confirmReset = false
                onResetAll()
            },
            { confirmReset = false }
        )
    }

    if (confirmClear) {
        ConfirmDialog(
            "Clear demo history?",
            "This removes the Privacy Timeline entries only.",
            "Clear",
            {
                confirmClear = false
                onClearHistory()
            },
            { confirmClear = false }
        )
    }
}

@Composable
private fun ToggleRow(
    label: String,
    value: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        Modifier.fillMaxWidth().heightIn(min = 56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            Modifier.weight(1f).padding(end = 12.dp),
            fontWeight = FontWeight.SemiBold,
            color = BrobotColors.TextPrimary
        )

        Switch(
            checked = value,
            onCheckedChange = onChange
        )
    }
}
