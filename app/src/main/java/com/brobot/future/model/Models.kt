package com.brobot.future.model

/** Every place a feature tag can send the judge. */
enum class Destination(val label: String) {
    INTRO("Intro"),
    HOME("Home"),
    TWIN("Twin"),
    SIMULATE("Simulate"),
    UNIVERSE("Universe"),
    SETTINGS("Settings"),
    BUTTERFLY("Butterfly Effect"),
    INTERVENTION("Risk Intervention"),
    EXPLAINER("AI Risk Explainer"),
    AUTOPILOT("Privacy Autopilot"),
    SHADOW("Digital Shadow"),
    TIMELINE("Privacy Timeline"),
    JUDGE_DEMO("Judge Demo")
}

/** Selectable demo conditions. These only ever change the LOCAL simulated profile. */
enum class ConditionId(val label: String) {
    PASSWORD_REUSE("Password reuse"),
    TWO_FACTOR("Two-factor authentication"),
    PHONE_LOCK("Phone lock"),
    RECOVERY_METHOD("Recovery method"),
    LINK_AWARENESS("Suspicious-link awareness"),
    PERMISSION_REVIEW("App-permission review")
}

/**
 * The simulated profile. Pure synthetic data: never read from the real device.
 * Default = a deliberately "weak" demo persona so there is something to simulate.
 */
data class DemoProfile(
    val passwordReuse: Boolean = true,
    val twoFactor: Boolean = false,
    val phoneLock: Boolean = false,
    val strongRecovery: Boolean = false,
    val linkAware: Boolean = false,
    val permissionsReviewed: Boolean = false
) {
    fun isSet(id: ConditionId): Boolean = when (id) {
        ConditionId.PASSWORD_REUSE -> passwordReuse
        ConditionId.TWO_FACTOR -> twoFactor
        ConditionId.PHONE_LOCK -> phoneLock
        ConditionId.RECOVERY_METHOD -> strongRecovery
        ConditionId.LINK_AWARENESS -> linkAware
        ConditionId.PERMISSION_REVIEW -> permissionsReviewed
    }

    fun with(id: ConditionId, value: Boolean): DemoProfile = when (id) {
        ConditionId.PASSWORD_REUSE -> copy(passwordReuse = value)
        ConditionId.TWO_FACTOR -> copy(twoFactor = value)
        ConditionId.PHONE_LOCK -> copy(phoneLock = value)
        ConditionId.RECOVERY_METHOD -> copy(strongRecovery = value)
        ConditionId.LINK_AWARENESS -> copy(linkAware = value)
        ConditionId.PERMISSION_REVIEW -> copy(permissionsReviewed = value)
    }

    companion object {
        val DEFAULT = DemoProfile()
    }
}

/** Human-readable state label for a condition, e.g. "Unique passwords" / "Passwords reused". */
fun conditionStateLabel(id: ConditionId, profile: DemoProfile): String = when (id) {
    ConditionId.PASSWORD_REUSE ->
        if (profile.passwordReuse) "Passwords reused (demo)" else "Unique passwords (demo)"

    ConditionId.TWO_FACTOR ->
        if (profile.twoFactor) "2FA on (demo)" else "2FA off (demo)"

    ConditionId.PHONE_LOCK ->
        if (profile.phoneLock) "Phone locked (demo)" else "No phone lock (demo)"

    ConditionId.RECOVERY_METHOD ->
        if (profile.strongRecovery) "Reviewed recovery (demo)" else "Weak recovery (demo)"

    ConditionId.LINK_AWARENESS ->
        if (profile.linkAware) "Link-aware (demo)" else "Not link-aware (demo)"

    ConditionId.PERMISSION_REVIEW ->
        if (profile.permissionsReviewed) "Permissions reviewed (demo)"
        else "Permissions not reviewed (demo)"
}

enum class ScenarioId {
    REUSED_PASSWORD,
    SUSPICIOUS_LINK,
    LOST_PHONE,
    WEAK_RECOVERY,
    PUBLIC_WIFI,
    EXCESSIVE_PERMISSIONS
}

data class Scenario(
    val id: ScenarioId,
    val title: String,
    val summary: String,
    /** Conditions the rules for this scenario read. Documented for the Explainer. */
    val readsConditions: List<ConditionId>
)

/** Where a statement in an explanation comes from. Required by the "no fake AI" rule. */
enum class EvidenceKind(val label: String) {
    USER_INPUT("Your demo input"),
    SYNTHETIC("Synthetic demo data"),
    RULE("Documented rule"),
    UNKNOWN("Unknown to this simulation"),
    SUGGESTION("Suggested protective action")
}

data class Assumption(
    val kind: EvidenceKind,
    val text: String
)

enum class TwinNodeId(val label: String) {
    ACCOUNT_ACCESS("Account Access"),
    PERSONAL_DATA("Personal Data"),
    DEVICE_SECURITY("Device Security"),
    SOCIAL_ENGINEERING("Social Engineering"),
    ACCOUNT_RECOVERY("Account Recovery"),
    FINANCIAL_EXPOSURE("Financial Exposure")
}

enum class NodeStatus(val label: String) {
    PROTECTED("Protective conditions set (demo)"),
    MIXED("Partly protected (demo)"),
    ATTENTION("Needs attention (demo)")
}

/** One step of a hypothetical, conditional pathway. */
data class PathwayStep(
    val id: String,
    val title: String,
    val represents: String,
    val whyConnected: String,
    val assumptions: List<Assumption>,
    val reduceAction: String,
    val nodes: Set<TwinNodeId>,
    val isTrigger: Boolean
)

enum class PathwayLevel(val label: String) {
    PRESENT("Pathway present in this simulation"),
    REDUCED("Pathway reduced in this simulation"),
    ABSENT("Pathway not present in this simulation")
}

data class Intervention(
    val id: String,
    val title: String,
    val description: String,
    val condition: ConditionId,
    /** Value the condition is set to when applied. */
    val setsTo: Boolean,
    val relevantScenarios: Set<ScenarioId>
)

data class SimulationResult(
    val scenario: Scenario,
    val profile: DemoProfile,
    val steps: List<PathwayStep>,
    val assumptions: List<Assumption>,
    val suggestedInterventions: List<Intervention>,
    val noPathwayNote: String?,
    val label: String = "SIMULATED RESULT · SYNTHETIC DATA · NOT A REAL SCAN"
) {
    /** Risk steps = everything after the trigger. */
    val riskStepCount: Int
        get() = steps.count { !it.isTrigger }

    val level: PathwayLevel
        get() = if (riskStepCount > 0) PathwayLevel.PRESENT else PathwayLevel.ABSENT

    val affectedNodes: Set<TwinNodeId>
        get() = steps.flatMap { it.nodes }.toSet()
}

data class Comparison(
    val before: SimulationResult,
    val after: SimulationResult,
    val intervention: Intervention,
    val changeDescription: String,
    val removedSteps: List<PathwayStep>,
    val remainingSteps: List<PathwayStep>,
    val level: PathwayLevel,
    val summary: String
)

data class TimelineEvent(
    val id: Int,
    val title: String,
    val details: String,
    /** Simple monotonically increasing sequence label, e.g. "Step 3" (no real clock needed). */
    val sequence: Int
)

data class ChecklistTask(
    val id: String,
    val title: String,
    val detail: String
)

data class FeatureInfo(
    val id: String,
    val tag: String,
    val dialogTitle: String,
    val whatItIs: String,
    val whatItDoes: String,
    val problemSolved: String,
    val vision2035: String,
    val prototypeDemonstrates: String,
    val simulatedOrMissing: String,
    val destination: Destination,
    val actionLabel: String
)

data class ExplainerAnswer(
    val question: String,
    val parts: List<Assumption>
)
