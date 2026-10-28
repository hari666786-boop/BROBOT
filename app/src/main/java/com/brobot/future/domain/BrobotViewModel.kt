package com.brobot.future.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.brobot.future.data.FeatureCatalog
import com.brobot.future.model.*
import com.brobot.future.simulation.SimulationEngine

data class UiMessage(val id: Long, val text: String)

/** Single source of UI state. All state is in memory (session only) and all data is synthetic. */
class BrobotViewModel : ViewModel() {

    private val tabs = setOf(
        Destination.HOME,
        Destination.TWIN,
        Destination.SIMULATE,
        Destination.UNIVERSE,
        Destination.SETTINGS
    )

    // ---- navigation (state-based, no extra dependency) ----
    val backStack = mutableStateListOf(Destination.INTRO)
    val current: Destination get() = backStack.last()
    val selectedTab: Destination
        get() = backStack.lastOrNull { it in tabs } ?: Destination.HOME

    // ---- demo state ----
    var profile by mutableStateOf(DemoProfile.DEFAULT)
        private set

    var selectedScenario by mutableStateOf<ScenarioId?>(null)
        private set

    var result by mutableStateOf<SimulationResult?>(null)
        private set

    var comparison by mutableStateOf<Comparison?>(null)
        private set

    var selectedInterventionId by mutableStateOf<String?>(null)

    var replayKey by mutableIntStateOf(0)
        private set

    var inspectStepId by mutableStateOf<String?>(null)
    var explainerQuestion by mutableStateOf<String?>(null)

    val timeline = mutableStateListOf<TimelineEvent>()

    var completedTasks by mutableStateOf<Set<String>>(emptySet())
        private set

    val notes = mutableStateListOf<String>()

    // ---- overlays / settings ----
    var featureDialog by mutableStateOf<FeatureInfo?>(null)

    var demoStep by mutableStateOf<Int?>(null)
        private set

    var message by mutableStateOf<UiMessage?>(null)
        private set

    var largeText by mutableStateOf(false)
    var reduceMotion by mutableStateOf(false)

    private var eventId = 0
    private var msgId = 0L
    private val demoLogged = mutableSetOf<Int>()

    init {
        logProfileCreated()
    }

    // ---------------- navigation ----------------

    fun enterApp() {
        setStack(Destination.HOME)
    }

    fun navigate(d: Destination) {
        when (d) {
            Destination.HOME, Destination.INTRO ->
                setStack(Destination.HOME)

            Destination.TWIN,
            Destination.SIMULATE,
            Destination.UNIVERSE,
            Destination.SETTINGS ->
                setStack(d)

            Destination.JUDGE_DEMO ->
                startJudgeDemo()

            else ->
                if (current != d) backStack.add(d)
        }
    }

    private fun setStack(d: Destination) {
        backStack.clear()
        backStack.add(Destination.HOME)

        if (d != Destination.HOME) {
            backStack.add(d)
        }
    }

    /** @return true if a screen was popped. */
    fun back(): Boolean {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
            return true
        }

        return false
    }

    fun openFeature(tag: String) {
        featureDialog = FeatureCatalog.byTag(tag)
    }

    fun openFeatureById(id: String) {
        featureDialog = FeatureCatalog.byId(id)
    }

    fun dismissFeature() {
        featureDialog = null
    }

    fun followFeature(info: FeatureInfo) {
        featureDialog = null
        navigate(info.destination)
    }

    // ---------------- simulation ----------------

    fun toggleCondition(id: ConditionId, value: Boolean) {
        profile = profile.with(id, value)
        comparison = null
        selectedInterventionId = null

        selectedScenario?.let {
            if (result != null) {
                result = SimulationEngine.simulate(it, profile)
            }
        }

        replayKey++
    }

    fun selectScenario(id: ScenarioId) {
        if (selectedScenario != id) {
            selectedScenario = id
            result = null
            comparison = null
            selectedInterventionId = null
        }
    }

    fun runScenario(id: ScenarioId) =
        runScenarioInternal(id, log = true)

    private fun runScenarioInternal(id: ScenarioId, log: Boolean) {
        selectedScenario = id
        comparison = null
        selectedInterventionId = null

        val r = SimulationEngine.simulate(id, profile)

        result = r
        replayKey++

        if (log) {
            log(
                "Scenario explored: ${r.scenario.title}",
                "Simulated with: ${profileSummary(profile)}. " +
                    "Result: ${r.level.label} " +
                    "(${r.riskStepCount} possible step(s)). Synthetic data only."
            )
        }
    }

    fun replay() {
        replayKey++
    }

    fun applyIntervention() =
        applyInterventionInternal(log = true)

    private fun applyInterventionInternal(log: Boolean) {
        val r = result ?: return
        val ivId = selectedInterventionId ?: return
        val iv = SimulationEngine.intervention(ivId)

        val cmp = SimulationEngine.compare(r.scenario.id, profile, iv)

        profile = SimulationEngine.apply(iv, profile)
        comparison = cmp
        result = cmp.after
        selectedInterventionId = null
        replayKey++

        if (log) {
            log(
                "Protective intervention selected",
                "Demo intervention: ${iv.title}. ${cmp.changeDescription}"
            )

            log(
                "Scenario simulated again",
                "${r.scenario.title} re-run with: ${profileSummary(profile)}."
            )

            log(
                "Comparison reviewed",
                "${cmp.summary} Steps: " +
                    "${cmp.before.riskStepCount} → ${cmp.after.riskStepCount}."
            )
        }

        toast(cmp.changeDescription)
    }

    fun resetDemoProfile() {
        profile = DemoProfile.DEFAULT
        comparison = null
        selectedInterventionId = null

        val s = selectedScenario

        result = if (s != null && result != null) {
            SimulationEngine.simulate(s, profile)
        } else {
            null
        }

        replayKey++

        log(
            "Demo profile reset",
            "Demo conditions restored to the default synthetic profile."
        )

        toast("Demo profile reset")
    }

    fun resetAll() {
        profile = DemoProfile.DEFAULT
        selectedScenario = null
        result = null
        comparison = null
        selectedInterventionId = null
        inspectStepId = null
        explainerQuestion = null
        completedTasks = emptySet()
        notes.clear()
        timeline.clear()
        demoStep = null
        featureDialog = null
        demoLogged.clear()
        eventId = 0

        logProfileCreated()
        toast("All demo data reset")
    }

    // ---------------- checklist, notes, history ----------------

    fun toggleTask(id: String, checked: Boolean) {
        completedTasks = if (checked) {
            completedTasks + id
        } else {
            completedTasks - id
        }

        toast(
            if (checked) {
                "Marked complete — demo state only, no device settings changed"
            } else {
                "Marked incomplete"
            }
        )
    }

    fun addNote(text: String) {
        val t = text.trim().take(80)

        if (t.isNotEmpty()) {
            notes.add(t)
            toast("Local demo note added")
        }
    }

    fun removeNote(index: Int) {
        if (index in notes.indices) {
            notes.removeAt(index)
        }
    }

    fun clearHistory() {
        timeline.clear()
        toast("Demo history cleared")
    }

    // ---------------- judge demo ----------------

    fun startJudgeDemo() {
        demoLogged.clear()
        goToDemoStep(0)
    }

    fun exitJudgeDemo() {
        demoStep = null
        inspectStepId = null
    }

    fun nextDemoStep() {
        val s = demoStep ?: return

        if (s < JudgeDemo.steps.lastIndex) {
            goToDemoStep(s + 1)
        } else {
            exitJudgeDemo()
        }
    }

    fun previousDemoStep() {
        val s = demoStep ?: return

        if (s > 0) {
            goToDemoStep(s - 1)
        }
    }

    private fun prepareReusedPassword(log: Boolean) {
        profile = DemoProfile.DEFAULT
        runScenarioInternal(ScenarioId.REUSED_PASSWORD, log)
    }

    private fun goToDemoStep(i: Int) {
        demoStep = i
        featureDialog = null
        inspectStepId = null

        val first = demoLogged.add(if (i == 5) 4 else i)

        when (i) {
            0 -> {
                resetAllQuiet()
                setStack(Destination.HOME)
            }

            1 -> {
                profile = DemoProfile.DEFAULT
                selectedScenario = null
                result = null
                comparison = null
                setStack(Destination.TWIN)
            }

            2 -> {
                prepareReusedPassword(first)
                setStack(Destination.BUTTERFLY)
            }

            3 -> {
                prepareReusedPassword(false)
                setStack(Destination.BUTTERFLY)
