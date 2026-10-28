package com.brobot.future.simulation

import com.brobot.future.model.*

/**
 * A documented rule: a pathway step is included in the simulation if and only if
 * [requires] is true for the demo profile. [ruleText] is shown to the judge verbatim
 * (Explainer / "What assumption is being made?"), so docs and behaviour cannot diverge.
 */
class RuleStep(
    val step: PathwayStep,
    val ruleText: String,
    val requires: (DemoProfile) -> Boolean
)

object ScenarioCatalog {

    val scenarios: List<Scenario> = listOf(
        Scenario(
            ScenarioId.REUSED_PASSWORD,
            "Reused Password",
            "Explore a hypothetical situation in which one password is used on several accounts.",
            listOf(
                ConditionId.PASSWORD_REUSE,
                ConditionId.TWO_FACTOR,
                ConditionId.RECOVERY_METHOD
            )
        ),
        Scenario(
            ScenarioId.SUSPICIOUS_LINK,
            "Suspicious Link",
            "Explore a hypothetical situation in which a convincing but untrustworthy link arrives.",
            listOf(
                ConditionId.LINK_AWARENESS,
                ConditionId.TWO_FACTOR,
                ConditionId.PASSWORD_REUSE
            )
        ),
        Scenario(
            ScenarioId.LOST_PHONE,
            "Lost Phone",
            "Explore a hypothetical situation in which your phone is lost.",
            listOf(
                ConditionId.PHONE_LOCK,
                ConditionId.RECOVERY_METHOD
            )
        ),
        Scenario(
            ScenarioId.WEAK_RECOVERY,
            "Weak Account Recovery",
            "Explore a hypothetical situation in which an account-recovery option is easy to guess.",
            listOf(
                ConditionId.RECOVERY_METHOD,
                ConditionId.TWO_FACTOR,
                ConditionId.PASSWORD_REUSE
            )
        ),
        Scenario(
            ScenarioId.PUBLIC_WIFI,
            "Public Wi-Fi Privacy",
            "Explore a hypothetical situation in which you use an unfamiliar public network.",
            listOf(
                ConditionId.LINK_AWARENESS,
                ConditionId.PASSWORD_REUSE,
                ConditionId.TWO_FACTOR
            )
        ),
        Scenario(
            ScenarioId.EXCESSIVE_PERMISSIONS,
            "Excessive App Permissions",
            "Explore a hypothetical situation in which an app holds broader permissions than it needs.",
            listOf(
                ConditionId.PERMISSION_REVIEW,
                ConditionId.TWO_FACTOR
            )
        )
    )

    fun scenario(id: ScenarioId): Scenario =
        scenarios.first { it.id == id }

    // ---- helpers to keep rule definitions compact and consistent ----

    private fun a(kind: EvidenceKind, text: String) =
        Assumption(kind, text)

    private val SYN_NO_REAL = a(
        EvidenceKind.SYNTHETIC,
        "No real device, account, network or app was inspected; all data is synthetic."
    )

    private val UNK_REAL_WORLD = a(
        EvidenceKind.UNKNOWN,
        "Real-world likelihood is unknown. Pathways are conditional possibilities, not predictions."
    )

    private fun step(
        id: String,
        title: String,
        represents: String,
        why: String,
        assumptions: List<Assumption>,
        reduce: String,
        nodes: Set<TwinNodeId>,
        trigger: Boolean = false
    ) = PathwayStep(
        id,
        title,
        represents,
        why,
        assumptions,
        reduce,
        nodes,
        trigger
    )

    private val rules: Map<ScenarioId, List<RuleStep>> = mapOf(

        // ---------------- REUSED PASSWORD ----------------

        ScenarioId.REUSED_PASSWORD to listOf(
            RuleStep(
                step(
                    "rp1",
                    "Password Reuse",
                    "One password is shared by several accounts in this demo profile.",
                    "This is the starting condition you selected.",
                    listOf(
                        a(EvidenceKind.USER_INPUT, "Password reuse is ON in the demo profile."),
                        SYN_NO_REAL
                    ),
                    "Use unique passwords for important accounts.",
                    setOf(TwinNodeId.ACCOUNT_ACCESS),
                    trigger = true
                ),
                "Included when: password reuse = ON.",
                { it.passwordReuse }
            ),
            RuleStep(
                step(
                    "rp2",
                    "One Account Could Be Compromised",
                    "A hypothetical exposure of a single account's password.",
                    "If a password is exposed somewhere, the same password is a candidate elsewhere.",
                    listOf(
                        a(EvidenceKind.RULE, "Assumes one account's password is hypothetically exposed."),
                        UNK_REAL_WORLD
                    ),
                    "Consider a reputable password manager to generate unique passwords.",
                    setOf(TwinNodeId.ACCOUNT_ACCESS)
                ),
                "Included when: password reuse = ON.",
                { it.passwordReuse }
            ),
            RuleStep(
                step(
                    "rp3",
                    "Other Accounts Might Become Exposed",
                    "Accounts sharing the same password become reachable in this model.",
                    "Reuse is what links the first account to the others.",
                    listOf(
                        a(EvidenceKind.RULE, "Rule: reused password links accounts together."),
                        UNK_REAL_WORLD
                    ),
                    "Change reused passwords, starting with email and financial accounts.",
                    setOf(
                        TwinNodeId.ACCOUNT_ACCESS,
                        TwinNodeId.ACCOUNT_RECOVERY
                    )
                ),
                "Included when: password reuse = ON.",
                { it.passwordReuse }
            ),
            RuleStep(
                step(
                    "rp4",
                    "Personal Information Could Be Misused",
                    "Data stored in the reachable accounts could be viewed.",
                    "Without a second sign-in step, a password alone could be enough in this model.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Two-factor authentication is OFF in the demo profile."
                        ),
                        a(
                            EvidenceKind.RULE,
                            "Rule: 2FA ON breaks this link in the simulation."
                        )
                    ),
                    "Enable multifactor authentication where available.",
                    setOf(TwinNodeId.PERSONAL_DATA)
                ),
                "Included when: password reuse = ON and 2FA = OFF.",
                { it.passwordReuse && !it.twoFactor }
            ),
            RuleStep(
                step(
                    "rp5",
                    "Further Privacy or Financial Consequences",
                    "Downstream effects if recovery routes are also weak.",
                    "Weak recovery options could widen the effect in this model.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Recovery method is not reviewed in the demo profile."
                        ),
                        UNK_REAL_WORLD
                    ),
                    "Review account-recovery options.",
                    setOf(
                        TwinNodeId.FINANCIAL_EXPOSURE,
                        TwinNodeId.ACCOUNT_RECOVERY
                    )
                ),
                "Included when: password reuse = ON, 2FA = OFF and recovery = weak.",
                { it.passwordReuse && !it.twoFactor && !it.strongRecovery }
            )
        ),

        // ---------------- SUSPICIOUS LINK ----------------

        ScenarioId.SUSPICIOUS_LINK to listOf(
            RuleStep(
                step(
                    "sl1",
                    "A Suspicious Link Arrives",
                    "A hypothetical message contains an untrustworthy link.",
                    "This is the scenario's starting event; it is always assumed.",
                    listOf(SYN_NO_REAL),
                    "Learn to recognise suspicious links before opening them.",
                    setOf(TwinNodeId.SOCIAL_ENGINEERING),
                    trigger = true
                ),
                "Always included (scenario trigger).",
                { true }
            ),
            RuleStep(
                step(
                    "sl2",
                    "The Link Might Be Opened",
                    "Without link awareness the link may look trustworthy.",
                    "Social-engineering messages rely on appearing legitimate.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Suspicious-link awareness is OFF in the demo profile."
                        ),
                        UNK_REAL_WORLD
                    ),
                    "Pause and check sender and link address before tapping.",
                    setOf(TwinNodeId.SOCIAL_ENGINEERING)
                ),
                "Included when: link awareness = OFF.",
                { !it.linkAware }
            ),
            RuleStep(
                step(
                    "sl3",
                    "Details Might Be Entered on a Look-alike Page",
                    "A copied sign-in page could ask for credentials.",
                    "An opened link can lead to a page imitating a real service.",
                    listOf(
                        a(
                            EvidenceKind.RULE,
                            "Rule: opened link may lead to a look-alike page."
                        ),
                        UNK_REAL_WORLD
                    ),
                    "Open services by typing the address or using the official app.",
                    setOf(
                        TwinNodeId.SOCIAL_ENGINEERING,
                        TwinNodeId.PERSONAL_DATA
                    )
                ),
                "Included when: link awareness = OFF.",
                { !it.linkAware }
            ),
            RuleStep(
                step(
                    "sl4",
                    "Account Access Might Be Attempted",
                    "Captured details could be tried on the real service.",
                    "Without 2FA, a password alone might be accepted in this model.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Two-factor authentication is OFF in the demo profile."
                        )
                    ),
                    "Enable multifactor authentication where available.",
                    setOf(TwinNodeId.ACCOUNT_ACCESS)
                ),
                "Included when: link awareness = OFF and 2FA = OFF.",
                { !it.linkAware && !it.twoFactor }
            ),
            RuleStep(
                step(
                    "sl5",
                    "Reuse Could Widen the Effect",
                    "The same password may exist on other accounts.",
                    "Reused passwords connect one account to others.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Password reuse is ON in the demo profile."
                        )
                    ),
                    "Use unique passwords for important accounts.",
                    setOf(
                        TwinNodeId.ACCOUNT_ACCESS,
                        TwinNodeId.FINANCIAL_EXPOSURE
                    )
                ),
                "Included when: link awareness = OFF, 2FA = OFF and password reuse = ON.",
                { !it.linkAware && !it.twoFactor && it.passwordReuse }
            )
        ),

        // ---------------- LOST PHONE ----------------

        ScenarioId.LOST_PHONE to listOf(
            RuleStep(
                step(
                    "lp1",
                    "The Phone Is Lost",
                    "A hypothetical loss of the device.",
                    "This is the scenario's starting event; it is always assumed.",
                    listOf(
                        SYN_NO_REAL,
                        a(
                            EvidenceKind.UNKNOWN,
                            "BROBOT does not locate or track any real phone."
                        )
                    ),
                    "Set a screen lock before anything goes missing.",
                    setOf(TwinNodeId.DEVICE_SECURITY),
                    trigger = true
                ),
                "Always included (scenario trigger).",
                { true }
            ),
            RuleStep(
                step(
                    "lp2",
                    "Unauthorised Access Might Be Attempted",
                    "Someone could try to use the unlocked device.",
                    "With no lock, the device itself is the only barrier.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Phone lock is OFF in the demo profile."
                        )
                    ),
                    "Enable a PIN, pattern or biometric lock.",
                    setOf(TwinNodeId.DEVICE_SECURITY)
                ),
                "Included when: phone lock = OFF.",
                { !it.phoneLock }
            ),
            RuleStep(
                step(
                    "lp3",
                    "Locally Stored Information Might Be Exposed",
                    "Photos, messages or files on the device.",
                    "Access to an unlocked device can include its stored data.",
                    listOf(
                        a(
                            EvidenceKind.RULE,
                            "Rule: no lock → local data considered reachable."
                        ),
                        UNK_REAL_WORLD
                    ),
                    "Enable a lock and consider encrypted storage options.",
                    setOf(
                        TwinNodeId.PERSONAL_DATA,
                        TwinNodeId.DEVICE_SECURITY
                    )
                ),
                "Included when: phone lock = OFF.",
                { !it.phoneLock }
            ),
            RuleStep(
                step(
                    "lp4",
                    "Signed-in Accounts Might Be Reached",
                    "Apps that stay signed in could be opened.",
                    "Signed-in apps depend on the device lock in this model.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Phone lock is OFF in the demo profile."
                        )
                    ),
                    "Enable a lock and review which apps stay signed in.",
                    setOf(
                        TwinNodeId.ACCOUNT_ACCESS,
                        TwinNodeId.FINANCIAL_EXPOSURE
                    )
                ),
                "Included when: phone lock = OFF.",
                { !it.phoneLock }
            ),
            RuleStep(
                step(
                    "lp5",
                    "Account-Recovery Complications",
                    "Recovery codes or numbers tied to the lost device.",
                    "Recovery methods that depend on the phone may be harder to use.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Recovery method is not reviewed in the demo profile."
                        ),
                        UNK_REAL_WORLD
                    ),
                    "Review account-recovery options and keep a backup method.",
                    setOf(TwinNodeId.ACCOUNT_RECOVERY)
                ),
                "Included when: recovery = weak (regardless of phone lock).",
                { !it.strongRecovery }
            )
        ),

        // ---------------- WEAK ACCOUNT RECOVERY ----------------

        ScenarioId.WEAK_RECOVERY to listOf(
            RuleStep(
                step(
                    "wr1",
                    "A Weak Recovery Option",
                    "A recovery question or address that is easy to guess or find.",
                    "This is the starting condition you selected.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Recovery method is not reviewed in the demo profile."
                        ),
                        SYN_NO_REAL
                    ),
                    "Review account-recovery options.",
                    setOf(TwinNodeId.ACCOUNT_RECOVERY),
                    trigger = true
                ),
                "Included when: recovery = weak.",
                { !it.strongRecovery }
            ),
            RuleStep(
                step(
                    "wr2",
                    "A Reset Might Be Attempted",
                    "Someone could try the recovery route instead of the password.",
                    "Recovery is an alternative way into an account.",
                    listOf(
                        a(
                            EvidenceKind.RULE,
                            "Rule: weak recovery is treated as an alternative entry route."
                        ),
                        UNK_REAL_WORLD
                    ),
                    "Use recovery options that are not publicly guessable.",
                    setOf(
                        TwinNodeId.ACCOUNT_RECOVERY,
                        TwinNodeId.ACCOUNT_ACCESS
                    )
                ),
                "Included when: recovery = weak.",
                { !it.strongRecovery }
            ),
            RuleStep(
                step(
                    "wr3",
                    "The Reset Might Succeed",
                    "Guessable answers could satisfy the check.",
                    "Without a second factor nothing else may stop the reset in this model.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Two-factor authentication is OFF in the demo profile."
                        )
                    ),
                    "Enable multifactor authentication where available.",
                    setOf(
                        TwinNodeId.ACCOUNT_ACCESS,
                        TwinNodeId.PERSONAL_DATA
                    )
                ),
                "Included when: recovery = weak and 2FA = OFF.",
                { !it.strongRecovery && !it.twoFactor }
            ),
            RuleStep(
                step(
                    "wr4",
                    "Connected Accounts Might Follow",
                    "An email account often unlocks other resets.",
                    "Reuse could let one recovered account open others.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Password reuse is ON in the demo profile."
                        )
                    ),
                    "Use unique passwords for important accounts.",
                    setOf(
                        TwinNodeId.FINANCIAL_EXPOSURE,
                        TwinNodeId.ACCOUNT_ACCESS
                    )
                ),
                "Included when: recovery = weak, 2FA = OFF and password reuse = ON.",
                { !it.strongRecovery && !it.twoFactor && it.passwordReuse }
            )
        ),

        // ---------------- PUBLIC WI-FI ----------------

        ScenarioId.PUBLIC_WIFI to listOf(
            RuleStep(
                step(
                    "pw1",
                    "An Unfamiliar Public Network",
                    "A hypothetical open network in a public place.",
                    "This is the scenario's starting event; it is always assumed.",
                    listOf(
                        SYN_NO_REAL,
                        a(
                            EvidenceKind.UNKNOWN,
                            "BROBOT does not test or connect to any real Wi-Fi network."
                        )
                    ),
                    "Prefer trusted networks for sensitive tasks.",
                    setOf(TwinNodeId.DEVICE_SECURITY),
                    trigger = true
                ),
                "Always included (scenario trigger).",
                { true }
            ),
            RuleStep(
                step(
                    "pw2",
                    "A Fake Sign-in Page Might Appear",
                    "Some networks show a login page that could imitate a real one.",
                    "People who are not link-aware may trust such pages.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Suspicious-link awareness is OFF in the demo profile."
                        )
                    ),
                    "Learn to recognise suspicious pages before entering details.",
                    setOf(
                        TwinNodeId.SOCIAL_ENGINEERING,
                        TwinNodeId.PERSONAL_DATA
                    )
                ),
                "Included when: link awareness = OFF.",
                { !it.linkAware }
            ),
            RuleStep(
                step(
                    "pw3",
                    "Captured Details Might Be Reused",
                    "A captured password could be tried elsewhere.",
                    "Reuse allows one captured password to matter on other services.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Password reuse is ON in the demo profile."
                        )
                    ),
                    "Use unique passwords for important accounts.",
                    setOf(TwinNodeId.ACCOUNT_ACCESS)
                ),
                "Included when: link awareness = OFF and password reuse = ON.",
                { !it.linkAware && it.passwordReuse }
            ),
            RuleStep(
                step(
                    "pw4",
                    "Account Access Might Be Attempted",
                    "Captured details might be tried on real services.",
                    "Without 2FA a password alone might be accepted in this model.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Two-factor authentication is OFF in the demo profile."
                        )
                    ),
                    "Enable multifactor authentication where available.",
                    setOf(
                        TwinNodeId.ACCOUNT_ACCESS,
                        TwinNodeId.FINANCIAL_EXPOSURE
                    )
                ),
                "Included when: link awareness = OFF, password reuse = ON and 2FA = OFF.",
                { !it.linkAware && it.passwordReuse && !it.twoFactor }
            )
        ),

        // ---------------- EXCESSIVE APP PERMISSIONS ----------------

        ScenarioId.EXCESSIVE_PERMISSIONS to listOf(
            RuleStep(
                step(
                    "ep1",
                    "An App Holds Broad Permissions",
                    "An app can access more than it needs in this demo.",
                    "This is the starting condition you selected.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "App permissions are NOT reviewed in the demo profile."
                        ),
                        SYN_NO_REAL
                    ),
                    "Review app permissions manually.",
                    setOf(TwinNodeId.PERSONAL_DATA),
                    trigger = true
                ),
                "Included when: permissions reviewed = OFF.",
                { !it.permissionsReviewed }
            ),
            RuleStep(
                step(
                    "ep2",
                    "More Data Might Be Collected Than Needed",
                    "Extra access can mean extra data.",
                    "Broader permissions widen what an app can read.",
                    listOf(
                        a(
                            EvidenceKind.RULE,
                            "Rule: broader permission → more data in reach."
                        ),
                        UNK_REAL_WORLD
                    ),
                    "Remove permissions an app does not need.",
                    setOf(TwinNodeId.PERSONAL_DATA)
                ),
                "Included when: permissions reviewed = OFF.",
                { !it.permissionsReviewed }
            ),
            RuleStep(
                step(
                    "ep3",
                    "Data Might Be Shared or Combined",
                    "Collected data could feed wider profiles.",
                    "Sharing choices are often unclear to users.",
                    listOf(
                        a(
                            EvidenceKind.UNKNOWN,
                            "What any real app shares is unknown to this simulation."
                        )
                    ),
                    "Review data-sharing choices in each app.",
                    setOf(
                        TwinNodeId.PERSONAL_DATA,
                        TwinNodeId.SOCIAL_ENGINEERING
                    )
                ),
                "Included when: permissions reviewed = OFF.",
                { !it.permissionsReviewed }
            ),
            RuleStep(
                step(
                    "ep4",
                    "An Exposed Account Could Reveal More",
                    "If that app's account were reached, more would be visible.",
                    "Without 2FA, a stolen password alone might suffice in this model.",
                    listOf(
                        a(
                            EvidenceKind.USER_INPUT,
                            "Two-factor authentication is OFF in the demo profile."
                        )
                    ),
                    "Enable multifactor authentication where available.",
                    setOf(
                        TwinNodeId.ACCOUNT_ACCESS,
                        TwinNodeId.PERSONAL_DATA
                    )
                ),
                "Included when: permissions reviewed = OFF and 2FA = OFF.",
                { !it.permissionsReviewed && !it.twoFactor }
            )
        )
    )

    fun rulesFor(id: ScenarioId): List<RuleStep> =
        rules.getValue(id)

    /** Absent-pathway wording per scenario. Never claims all risk is gone. */
    fun noPathwayNote(id: ScenarioId): String = when (id) {
        ScenarioId.REUSED_PASSWORD ->
            "The demo now assumes unique passwords. The password-reuse pathway has been removed from this scenario. Other risks may remain."

        ScenarioId.EXCESSIVE_PERMISSIONS ->
            "The demo now assumes app permissions were reviewed. The excessive-permission pathway is not present in this scenario. Other risks may remain."

        ScenarioId.WEAK_RECOVERY ->
            "The demo now assumes a reviewed recovery method. The weak-recovery pathway is not present in this scenario. Other risks may remain."

        else ->
            "Under the current demo conditions this scenario shows no further steps. Other risks may remain."
    }
}
