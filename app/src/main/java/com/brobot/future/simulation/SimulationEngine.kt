package com.brobot.future.simulation

import com.brobot.future.model.*

/**
 * Deterministic local rules engine. No randomness, no clock, no network, no device access.
 * Same (scenario, profile) in → identical result out.
 */
object SimulationEngine {

    val interventions: List<Intervention> = listOf(
        Intervention(
            "unique_passwords",
            "Use unique passwords",
            "Assume each important account now has its own password.",
            ConditionId.PASSWORD_REUSE,
            false,
            setOf(
                ScenarioId.REUSED_PASSWORD,
                ScenarioId.SUSPICIOUS_LINK,
                ScenarioId.WEAK_RECOVERY,
                ScenarioId.PUBLIC_WIFI
            )
        ),
        Intervention(
            "enable_2fa",
            "Enable multifactor authentication",
            "Assume a second sign-in step is enabled where available.",
            ConditionId.TWO_FACTOR,
            true,
            setOf(
                ScenarioId.REUSED_PASSWORD,
                ScenarioId.SUSPICIOUS_LINK,
                ScenarioId.WEAK_RECOVERY,
                ScenarioId.PUBLIC_WIFI,
                ScenarioId.EXCESSIVE_PERMISSIONS
            )
        ),
        Intervention(
            "enable_lock",
            "Turn on a phone lock",
            "Assume the phone is protected by a PIN, pattern or biometric lock.",
            ConditionId.PHONE_LOCK,
            true,
            setOf(ScenarioId.LOST_PHONE)
        ),
        Intervention(
            "review_recovery",
            "Review account-recovery options",
            "Assume recovery options were reviewed and are no longer easy to guess.",
            ConditionId.RECOVERY_METHOD,
            true,
            setOf(
                ScenarioId.REUSED_PASSWORD,
                ScenarioId.LOST_PHONE,
                ScenarioId.WEAK_RECOVERY
            )
        ),
        Intervention(
            "learn_links",
            "Learn to recognise suspicious links",
            "Assume you now check senders and link addresses before acting.",
            ConditionId.LINK_AWARENESS,
            true,
            setOf(
                ScenarioId.SUSPICIOUS_LINK,
                ScenarioId.PUBLIC_WIFI
            )
        ),
        Intervention(
            "review_permissions",
            "Review app permissions",
            "Assume unneeded app permissions were removed manually.",
            ConditionId.PERMISSION_REVIEW,
            true,
            setOf(ScenarioId.EXCESSIVE_PERMISSIONS)
        )
    )

    fun intervention(id: String): Intervention =
        interventions.first { it.id == id }

    fun simulate(
        scenarioId: ScenarioId,
        profile: DemoProfile
    ): SimulationResult {
        val scenario = ScenarioCatalog.scenario(scenarioId)

        val steps = ScenarioCatalog.rulesFor(scenarioId)
            .filter { it.requires(profile) }
            .map { it.step }

        // Suggest interventions only if they would change something:
        // the condition is not already in the target state.
        val suggestions = interventions.filter {
            scenarioId in it.relevantScenarios &&
                profile.isSet(it.condition) != it.setsTo
        }

        val assumptions = buildList {
            scenario.readsConditions.forEach { c ->
                add(
                    Assumption(
                        EvidenceKind.USER_INPUT,
                        "${c.label}: ${conditionStateLabel(c, profile)}."
                    )
                )
            }

            add(
                Assumption(
                    EvidenceKind.SYNTHETIC,
                    "All inputs are a synthetic demo profile; nothing was read from the real device."
                )
            )

            add(
                Assumption(
                    EvidenceKind.RULE,
                    "Steps appear only when their documented rule matches the demo profile."
                )
            )

            add(
                Assumption(
                    EvidenceKind.UNKNOWN,
                    "Real-world likelihood, timing and impact are unknown to this simulation."
                )
            )
        }

        val result = SimulationResult(
            scenario = scenario,
            profile = profile,
            steps = steps,
            assumptions = assumptions,
            suggestedInterventions = suggestions,
            noPathwayNote = null
        )

        return if (result.riskStepCount == 0) {
            result.copy(
                noPathwayNote = ScenarioCatalog.noPathwayNote(scenarioId)
            )
        } else {
            result
        }
    }

    /** Applies the intervention to the profile and returns the new profile. */
    fun apply(
        intervention: Intervention,
        profile: DemoProfile
    ): DemoProfile =
        profile.with(intervention.condition, intervention.setsTo)

    /**
     * Runs the scenario before and after applying [intervention]. The comparison is computed from the two
     * actual engine results — nothing is hard-coded.
     */
    fun compare(
        scenarioId: ScenarioId,
        profile: DemoProfile,
        intervention: Intervention
    ): Comparison {
        val before = simulate(scenarioId, profile)
        val newProfile = apply(intervention, profile)
        val after = simulate(scenarioId, newProfile)

        val changed =
            profile.isSet(intervention.condition) != intervention.setsTo

        val changeText = if (changed) {
            "Demo condition changed: ${intervention.condition.label} — " +
                "${conditionStateLabel(intervention.condition, profile)} → " +
                "${conditionStateLabel(intervention.condition, newProfile)}."
        } else {
            "No demo condition changed: ${intervention.condition.label} was already " +
                "${conditionStateLabel(intervention.condition, profile)}."
        }

        val afterIds = after.steps.map { it.id }.toSet()
        val removed = before.steps.filter { it.id !in afterIds }
        val remaining = after.steps

        val level = when {
            after.riskStepCount == 0 ->
                PathwayLevel.ABSENT

            after.riskStepCount < before.riskStepCount ->
                PathwayLevel.REDUCED

            else ->
                PathwayLevel.PRESENT
        }

        val summary = when {
            !changed ->
                "Nothing changed because the demo already assumed this protection. Other risks may remain."

            level == PathwayLevel.ABSENT ->
                "The pathway is not present in this simulation after the change. Other risks may remain."

            level == PathwayLevel.REDUCED ->
                "The pathway is reduced in this simulation " +
                    "(${before.riskStepCount} → ${after.riskStepCount} possible steps). Other risks may remain."

            else ->
                "This change did not alter the pathway for this scenario in the simulation. " +
                    "A different protective action may be relevant. Other risks may remain."
        }

        return Comparison(
            before,
            after,
            intervention,
            changeText,
            removed,
            remaining,
            level,
            summary
        )
    }
}
