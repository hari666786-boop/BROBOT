package com.brobot.future.data

data class UniverseNode(
    val id: String,
    val label: String,
    val represents: String,
    val whyItMatters: String,
    val productionNeeds: String,
    val prototypeStatus: String,

    // Normalised position in a 0..1 square.
    val x: Float,
    val y: Float,

    val connectsTo: List<String>
)

object UniverseCatalog {

    val nodes: List<UniverseNode> = listOf(

        UniverseNode(
            "identity",
            "Identity",
            "Who you are online: names, handles and profiles.",
            "Identity ties the other areas together.",
            "Consent-based identity inventory and clear controls.",
            "Illustration only; no identity is read or searched.",
            0.50f,
            0.46f,
            listOf("accounts", "personal", "behaviour", "recovery")
        ),

        UniverseNode(
            "accounts",
            "Accounts",
            "Services you sign in to.",
            "Accounts are the doors to your data.",
            "A user-approved account inventory and sign-in hygiene guidance.",
            "No accounts are connected; the demo uses a synthetic profile.",
            0.18f,
            0.20f,
            listOf("identity", "recovery", "permissions")
        ),

        UniverseNode(
            "devices",
            "Devices",
            "Phones, laptops and other hardware.",
            "Devices hold data and keep you signed in.",
            "On-device, user-approved posture checks.",
            "No device is inspected.",
            0.82f,
            0.20f,
            listOf("personal", "permissions", "recovery")
        ),

        UniverseNode(
            "personal",
            "Personal Data",
            "Photos, messages, files and records.",
            "This is what privacy protects.",
            "Local classification with explicit consent.",
            "No data is read or stored.",
            0.84f,
            0.62f,
            listOf("identity", "devices", "permissions")
        ),

        UniverseNode(
            "permissions",
            "App Permissions",
            "What apps are allowed to access.",
            "Broad permissions widen what could be seen.",
            "Guided permission reviews that never change settings silently.",
            "Permissions are not read; one scenario illustrates the idea.",
            0.50f,
            0.84f,
            listOf("accounts", "devices", "personal")
        ),

        UniverseNode(
            "recovery",
            "Recovery",
            "Ways to regain access to accounts.",
            "Recovery routes are also routes in.",
            "Recovery-method review with user confirmation.",
            "Represented by a demo condition only.",
            0.16f,
            0.64f,
            listOf("identity", "accounts", "devices")
        ),

        UniverseNode(
            "behaviour",
            "Online Behaviour",
            "Habits such as link handling and sharing.",
            "Behaviour shapes exposure as much as settings do.",
            "Private, on-device habit insights.",
            "Represented by a demo condition only.",
            0.50f,
            0.12f,
            listOf("identity", "accounts")
        )
    )

    fun node(id: String): UniverseNode =
        nodes.first { it.id == id }
}
