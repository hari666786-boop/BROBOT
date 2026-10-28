@file:OptIn(ExperimentalLayoutApi::class)

package com.brobot.future.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.model.ConditionId
import com.brobot.future.model.DemoProfile
import com.brobot.future.model.PathwayStep
import com.brobot.future.model.Scenario
import com.brobot.future.model.conditionStateLabel
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun FeatureTagRow(tags: List<String>, onTag: (String) -> Unit) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { FeatureChip(it, onClick = { onTag(it) }) }
    }
}

@Composable
fun ConditionsPanel(
    profile: DemoProfile,
    onToggle: (ConditionId, Boolean) -> Unit
) {
    FuturisticCard {
        SectionTitle("Demo conditions")
        Text(
            "These switches change only the simulated profile. Nothing real is read or changed.",
            style = MaterialTheme.typography.bodySmall,
            color = BrobotColors.TextSecondary
        )

        ConditionId.values().forEach { id ->
            Row(
                Modifier.fillMaxWidth().heightIn(min = 56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    Modifier.weight(1f).padding(end = 12.dp)
                ) {
                    Text(
                        id.label,
                        fontWeight = FontWeight.SemiBold,
                        color = BrobotColors.TextPrimary
                    )
                    Text(
                        conditionStateLabel(id, profile),
                        style = MaterialTheme.typography.bodySmall,
                        color = BrobotColors.TextSecondary
                    )
                }

                Switch(
                    checked = profile.isSet(id),
                    onCheckedChange = { onToggle(id, it) }
                )
            }
        }
    }
}

@Composable
fun ScenarioCard(
    scenario: Scenario,
    selected: Boolean,
    onSelect: () -> Unit,
    onSimulate: () -> Unit
) {
    FuturisticCard(
        accent = if (selected) BrobotColors.Lavender else BrobotColors.Violet,
        onClick = onSelect
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                scenario.title.uppercase(),
                Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = BrobotColors.TextPrimary
            )

            if (selected) {
                Text(
                    "SELECTED",
                    color = BrobotColors.Teal,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        Text(
            scenario.summary,
            style = MaterialTheme.typography.bodyMedium,
            color = BrobotColors.TextSecondary
        )

        PrimaryActionButton(
            "Simulate",
            onSimulate,
            Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun StepSummaryList(
    steps: List<PathwayStep>,
    struck: Boolean = false
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        steps.forEachIndexed { i, s ->
            Text(
                "${i + 1}. ${s.title}",
                style = MaterialTheme.typography.bodyMedium,
                color = if (struck) {
                    BrobotColors.TextSecondary
                } else {
                    BrobotColors.TextPrimary
                }
            )
        }
    }
}
