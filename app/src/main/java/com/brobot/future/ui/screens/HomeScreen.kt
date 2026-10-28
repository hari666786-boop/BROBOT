package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.brobot.future.data.FeatureCatalog
import com.brobot.future.model.ConditionId
import com.brobot.future.model.DemoProfile
import com.brobot.future.model.Destination
import com.brobot.future.ui.components.DemoStatusBadge
import com.brobot.future.ui.components.FeatureTagRow
import com.brobot.future.ui.components.FuturisticCard
import com.brobot.future.ui.components.PrimaryActionButton
import com.brobot.future.ui.components.ScreenColumn
import com.brobot.future.ui.components.SectionTitle
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun HomeScreen(
    profile: DemoProfile,
    onTag: (String) -> Unit,
    onNavigate: (Destination) -> Unit,
    onHowItWorks: () -> Unit,
    onStartJudgeDemo: () -> Unit
) {
    ScreenColumn {
        DemoStatusBadge()

        Text(
            "Good to see you.",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = BrobotColors.TextPrimary
        )

        Text(
            "Your privacy universe, reimagined.",
            style = MaterialTheme.typography.bodyLarge,
            color = BrobotColors.TextSecondary
        )

        FuturisticCard(accent = BrobotColors.Lavender) {
            SectionTitle("Signature feature")

            Text(
                "BROBOT PRIVACY TWIN",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                color = BrobotColors.TextPrimary
            )

            Text(
                "Explore what could happen. Discover what you can change.",
                style = MaterialTheme.typography.bodyMedium,
                color = BrobotColors.TextSecondary
            )

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(androidx.compose.ui.unit.dp(8.dp))
            ) {
                PrimaryActionButton(
                    "Launch Simulation",
                    { onNavigate(Destination.TWIN) },
                    Modifier.weight(1f)
                )

                PrimaryActionButton(
                    "How It Works",
                    onHowItWorks,
                    Modifier.weight(1f),
                    secondary = true
                )
            }
        }

        PrimaryActionButton(
            "Start Judge Demo (≈90 s)",
            onStartJudgeDemo,
            Modifier.fillMaxWidth(),
            secondary = true
        )

        SectionTitle("Futuristic features — tap a tag")

        FeatureTagRow(
            FeatureCatalog.features.map { it.tag },
            onTag
        )

        val protective = ConditionId.values().count {
            if (it == ConditionId.PASSWORD_REUSE) {
                !profile.isSet(it)
            } else {
                profile.isSet(it)
            }
        }

        FuturisticCard {
            SectionTitle("Demo overview")

            Text(
                "Protective demo conditions set: $protective of ${ConditionId.values().size}",
                color = BrobotColors.TextPrimary,
                fontWeight = FontWeight.SemiBold
            )

            LinearProgressIndicator(
                progress = {
                    protective / ConditionId.values().size.toFloat()
                },
                modifier = Modifier.fillMaxWidth(),
                color = BrobotColors.Teal,
                trackColor = BrobotColors.VioletDeep
            )

            Text(
                "DEMO SCORE — a simple count of switches in the synthetic profile. It is not a security assessment, not a probability, and nothing was scanned.",
                style = MaterialTheme.typography.bodySmall,
                color = BrobotColors.TextSecondary
            )
        }
    }
}
