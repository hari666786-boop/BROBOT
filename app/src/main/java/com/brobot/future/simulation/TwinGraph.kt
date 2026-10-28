package com.brobot.future.simulation

import com.brobot.future.model.*

/** Privacy Twin node logic: which demo conditions protect which node, and the explanation text. */
object TwinGraph {

    /** For each node: protective checks (label → is protective in this profile). */
    private fun checks(
        node: TwinNodeId,
        p: DemoProfile
    ): List<Pair<String, Boolean>> = when (node) {
        TwinNodeId.ACCOUNT_ACCESS ->
            listOf(
                "Unique passwords" to !p.passwordReuse,
                "Two-factor authentication" to p.twoFactor
            )

        TwinNodeId.PERSONAL_DATA ->
            listOf(
                "App permissions reviewed" to p.permissionsReviewed,
                "Phone lock" to p.phoneLock
            )

        TwinNodeId.DEVICE_SECURITY ->
            listOf(
                "Phone lock" to p.phoneLock
            )

        TwinNodeId.SOCIAL_ENGINEERING ->
            listOf(
                "Suspicious-link awareness" to p.linkAware
            )

        TwinNodeId.ACCOUNT_RECOVERY ->
            listOf(
                "Reviewed recovery method" to p.strongRecovery,
                "Two-factor authentication" to p.twoFactor
            )

        TwinNodeId.FINANCIAL_EXPOSURE ->
            listOf(
                "Unique passwords" to !p.passwordReuse,
                "Two-factor authentication" to p.twoFactor,
                "Suspicious-link awareness" to p.linkAware
            )
    }

    fun status(
        node: TwinNodeId,
        p: DemoProfile
    ): NodeStatus {
        val c = checks(node, p)
        val good = c.count { it.second }

        return when (good) {
            c.size -> NodeStatus.PROTECTED
            0 -> NodeStatus.ATTENTION
            else -> NodeStatus.MIXED
        }
    }

    private val meaning = mapOf(
        TwinNodeId.ACCOUNT_ACCESS to
            "How sign-in to your accounts is protected: passwords and second factors.",

        TwinNodeId.PERSONAL_DATA to
            "Information stored on the device and in apps, and how widely apps can reach it.",

        TwinNodeId.DEVICE_SECURITY to
            "Whether the device itself is protected if someone else holds it.",

        TwinNodeId.SOCIAL_ENGINEERING to
            "How easily convincing messages or pages could lead to a mistake.",

        TwinNodeId.ACCOUNT_RECOVERY to
            "The routes used to regain an account — which are also routes into it.",

        TwinNodeId.FINANCIAL_EXPOSURE to
            "Where weaknesses elsewhere could reach money-related accounts. Always conditional."
    )

    private val links = mapOf(
        TwinNodeId.ACCOUNT_ACCESS to
            "Connects to Account Recovery and Financial Exposure.",

        TwinNodeId.PERSONAL_DATA to
            "Connects to Device Security and Account Access.",

        TwinNodeId.DEVICE_SECURITY to
            "Connects to Personal Data and Account Access.",

        TwinNodeId.SOCIAL_ENGINEERING to
            "Connects to Account Access and Financial Exposure.",

        TwinNodeId.ACCOUNT_RECOVERY to
            "Connects to Account Access and Device Security.",

        TwinNodeId.FINANCIAL_EXPOSURE to
            "Connects to Account Access and Social Engineering."
    )

    fun explanation(
        node: TwinNodeId,
        p: DemoProfile
    ): String {
        val checkText = checks(node, p).joinToString("; ") { (n, ok) ->
            "$n: ${if (ok) "set" else "not set"}"
        }

        return "${meaning.getValue(node)}\n\n" +
            "${links.getValue(node)}\n\n" +
            "Current demo state — ${status(node, p).label}. $checkText.\n\n" +
            "Synthetic demo data only: BROBOT has not inspected your real device or accounts."
    }

    val nodeOrder: List<TwinNodeId> =
        TwinNodeId.values().toList()
}
