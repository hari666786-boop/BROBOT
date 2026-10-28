package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.brobot.future.model.ScenarioId
import com.brobot.future.model.SimulationResult
import com.brobot.future.simulation.ExplainerEngine
import com.brobot.future.simulation.ScenarioCatalog
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun ExplainerScreen(
    result: SimulationResult?,
    question: String?,
    onQuestion: (String) -> Unit,
    onRun: (ScenarioId) -> Unit,
    onInfo: () -> Unit
) {
    ScreenColumn {
        ScreenHeader(
            "BROBOT INTELLIGENCE",
            "Understand the reason behind every simulated risk.",
            onInfo
        )

        DemoStatusBadge(
            "LOCAL RULES · NO LIVE AI MODEL CONNECTED",
            BrobotColors.Amber
        )

        Text(
            "These answers are assembled from this simulation's own rules and scenario data. They are not written by an AI model, and every statement is labelled by where it comes from.",
            style = MaterialTheme.typography.bodySmall,
            color = BrobotColors.TextSecondary
        )

        SectionTitle("Scenario")

        FeatureTagRow(ScenarioCatalog.scenarios.map { it.title }) { title ->
            ScenarioCatalog.scenarios
                .firstOrNull { it.title == title }
                ?.let { onRun(it.id) }
        }

        if (result == null) {
            FuturisticCard {
                Text(
                    "No scenario selected",
                    fontWeight = FontWeight.Bold,
                    color = BrobotColors.TextPrimary
                )

                Text(
                    "Pick a scenario above, then ask a question.",
                    color = BrobotColors.TextSecondary
                )

                PrimaryActionButton(
                    "Use “Reused Password”",
                    { onRun(ScenarioId.REUSED_PASSWORD) },
                    Modifier.fillMaxWidth()
                )
            }

            return@ScreenColumn
        }

        Text(
            "Scenario: ${result.scenario.title} · ${result.level.label}",
            fontWeight = FontWeight.SemiBold,
            color = BrobotColors.TextPrimary
        )

        SectionTitle("Ask a question")

        ExplainerEngine.questions.forEach { q ->
            PrimaryActionButton(
                q,
                { onQuestion(q) },
                Modifier.fillMaxWidth(),
                secondary = q != question
            )
        }

        if (question != null) {
            val answer = ExplainerEngine.answer(question, result)

            FuturisticCard(accent = BrobotColors.Cyan) {
                Text(
                    answer.question,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = BrobotColors.TextPrimary
                )

                AssumptionList(answer.parts)
            }
        } else {
            Text(
                "Choose one of the questions above.",
                color = BrobotColors.TextSecondary
            )
        }
    }
}
