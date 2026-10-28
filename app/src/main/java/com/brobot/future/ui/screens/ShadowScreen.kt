package com.brobot.future.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brobot.future.data.ShadowCatalog
import com.brobot.future.ui.components.*
import com.brobot.future.ui.theme.BrobotColors

@Composable
fun ShadowScreen(
    notes: List<String>,
    onAdd: (String) -> Unit,
    onRemove: (Int) -> Unit,
    onInfo: () -> Unit
) {
    var draft by rememberSaveable { mutableStateOf("") }

    ScreenColumn {
        ScreenHeader(
            "Digital Shadow",
            "The information and patterns a person may leave across digital services.",
            onInfo
        )

        DemoStatusBadge("SYNTHETIC EXAMPLE", BrobotColors.Lavender)

        FuturisticCard(accent = BrobotColors.Amber) {
            Text(
                "Real footprint discovery is NOT active. BROBOT does not scrape the internet or search for anyone's identity. A future version could offer consent-based discovery with clear controls.",
                color = BrobotColors.TextPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        ShadowCatalog.categories.forEach { c ->
            FuturisticCard {
                Text(
                    c.title,
                    fontWeight = FontWeight.Bold,
                    color = BrobotColors.TextPrimary
                )

                DemoStatusBadge(
                    "SYNTHETIC EXAMPLE",
                    BrobotColors.Lavender
                )

                Text(
                    c.syntheticExample,
                    color = BrobotColors.TextPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    c.whyItMatters,
                    color = BrobotColors.TextSecondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        FuturisticCard(accent = BrobotColors.Cyan) {
            SectionTitle("Local demo notes")

            Text(
                "Notes stay in this session only. Please do not type real personal details.",
                style = MaterialTheme.typography.bodySmall,
                color = BrobotColors.TextSecondary
            )

            OutlinedTextField(
                value = draft,
                onValueChange = { draft = it.take(80) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Fictional note") }
            )

            PrimaryActionButton(
                "Add Note",
                {
                    onAdd(draft)
                    draft = ""
                },
                Modifier.fillMaxWidth(),
                enabled = draft.isNotBlank()
            )

            if (notes.isEmpty()) {
                Text("No notes yet.", color = BrobotColors.TextSecondary)
            }

            notes.forEachIndexed { i, n ->
                Row(
                    Modifier.fillMaxWidth().heightIn(min = 48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        n,
                        Modifier.weight(1f).padding(end = 8.dp),
                        color = BrobotColors.TextPrimary
                    )

                    TextButton(
                        onClick = { onRemove(i) },
                        modifier = Modifier.heightIn(min = 48.dp)
                    ) {
                        Text("Remove", color = BrobotColors.Amber)
                    }
                }
            }
        }
    }
}
