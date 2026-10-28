package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import com.brobot.future.model.ConditionId
import com.brobot.future.model.DemoProfile
import com.brobot.future.model.Destination
import com.brobot.future.model.ScenarioId
import com.brobot.future.model.SimulationResult
import com.brobot.future.simulation.ScenarioCatalog
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun SimulateScreen(
    profile: DemoProfile,
    selected: ScenarioId?,
    result: SimulationResult?,
    onSelect: (ScenarioId) -> Unit,
    onSimulate: (ScenarioId) -> Unit,
    onToggle: (ConditionId, Boolean) -> Unit,
    onInfo: () -> Unit,
    onNavigate: (Destination) -> Unit
) {
    var showConditions by rememberSaveable { mutableStateOf(false) }

    ScreenColumn {
        ScreenHeader(
            "What-If Simulator",
            "Pick a hypothetical scenario. The outcome depends on your demo conditions.",
            onInfo
        )

        DemoStatusBadge("SIMULATED · LOCAL RULES · OFFLINE")

        PrimaryActionButton(
            if (showConditions) "Hide demo conditions" else "Edit demo conditions",
            { showConditions = !showConditions },
            Modifier.fillMaxWidth(),
            secondary = true
        )

        if (showConditions) {
            ConditionsPanel(profile, onToggle)
        }

        ScenarioCatalog.scenarios.forEach { sc ->
            ScenarioCard(
                sc,
                selected = sc.id == selected,
                onSelect = { onSelect(sc.id) },
                onSimulate = { onSimulate(sc.id) }
            )
        }

        if (result != null && result.scenario.id == selected) {
            FuturisticCard(accent = BrobotColors.Teal) {
                DemoStatusBadge(result.label, BrobotColors.Teal)

                Text(
                    "Scenario: ${result.scenario.title}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = BrobotColors.TextPrimary
                )

                Text(
                    result.level.label,
                    color = BrobotColors.Teal,
                    fontWeight = FontWeight.SemiBold
                )

                SectionTitle("Possible risk pathway (hypothetical)")

                if (result.noPathwayNote != null) {
                    Text(result.noPathwayNote!!, color = BrobotColors.TextPrimary)
                } else {
                    StepSummaryList(result.steps)
                }

                SectionTitle("Assumptions used")
                AssumptionList(result.assumptions)

                SectionTitle("Suggested protective actions")

                if (result.suggestedInterventions.isEmpty()) {
                    Text(
                        "The relevant demo protections are already set. Other risks may remain.",
                        color = BrobotColors.TextSecondary
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        result.suggestedInterventions.forEach {
                            Text(
                                "• ${it.title} — ${it.description}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = BrobotColors.TextPrimary
                            )
                        }
                    }
                }

                PrimaryActionButton(
                    "View Butterfly Pathway",
                    { onNavigate(Destination.BUTTERFLY) },
                    Modifier.fillMaxWidth()
                )

                PrimaryActionButton(
                    "Choose Protective Action",
                    { onNavigate(Destination.INTERVENTION) },
                    Modifier.fillMaxWidth(),
                    secondary = true
                )

                PrimaryActionButton(
                    "Explain This Result",
                    { onNavigate(Destination.EXPLAINER) },
                    Modifier.fillMaxWidth(),
                    secondary = true
                )
            }
        } else if (selected != null) {
            Text(
                "Press “Simulate” on the selected scenario to run the local rules.",
                style = MaterialTheme.typography.bodyMedium,
                color = BrobotColors.TextSecondary
            )
        } else {
            Text(
                "Select a scenario above to begin.",
                style = MaterialTheme.typography.bodyMedium,
                color = BrobotColors.TextSecondary
            )
        }
    }
}
