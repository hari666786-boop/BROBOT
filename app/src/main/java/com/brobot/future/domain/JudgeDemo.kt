package com.brobot.future.domain

import com.brobot.future.model.Destination

data class JudgeDemoStep(
    val timing: String,
    val title: String,
    val body: String,
    val destination: Destination
)

object JudgeDemo {

    val steps: List<JudgeDemoStep> = listOf(
        JudgeDemoStep(
            "0–10 s",
            "Meet BROBOT",
            "BROBOT makes invisible privacy risks visible and interactive. " +
                "Everything runs offline on synthetic data.",
            Destination.HOME
        ),

        JudgeDemoStep(
            "10–25 s",
            "Your Privacy Twin",
            "A simulated digital identity. Tap a node or flip a demo condition — " +
                "only the local demo profile changes.",
            Destination.TWIN
        ),

        JudgeDemoStep(
            "25–40 s",
            "The Butterfly Effect",
            "We picked “Reused Password”. Watch a hypothetical, conditional chain " +
                "appear — nothing real is detected.",
            Destination.BUTTERFLY
        ),

        JudgeDemoStep(
            "40–55 s",
            "Inspect a step",
            "Every step explains what it represents, why it connects, " +
                "the assumptions used and what could reduce it.",
            Destination.BUTTERFLY
        ),

        JudgeDemoStep(
            "55–70 s",
            "Apply a protective action",
            "We applied “Use unique passwords” to the demo profile and " +
                "re-ran the same deterministic rules.",
            Destination.INTERVENTION
        ),

        JudgeDemoStep(
            "70–82 s",
            "Compare before and after",
            "The comparison comes straight from the rules engine. " +
                "It names the changed condition and says other risks may remain.",
            Destination.INTERVENTION
        ),

        JudgeDemoStep(
            "82–90 s",
            "More from 2035",
            "Tap any tag on Home to explore Autopilot, Digital Shadow, " +
                "the Explainer, Timeline and more.",
            Destination.HOME
        )
    )
}
