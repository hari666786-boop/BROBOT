package com.brobot.future.data

data class ShadowCategory(
    val id: String,
    val title: String,
    val syntheticExample: String,
    val whyItMatters: String
)

object ShadowCatalog {

    val categories: List<ShadowCategory> = listOf(

        ShadowCategory(
            "profile",
            "Public Profile Information",
            "Fictional: “Avery K., Lisbon, loves cycling”.",
            "Small details can combine into a recognisable picture."
        ),

        ShadowCategory(
            "posts",
            "Public Posts",
            "Fictional: a holiday photo with a tagged place.",
            "Posts can reveal habits and locations."
        ),

        ShadowCategory(
            "permissions",
            "App Permissions",
            "Fictional: a flashlight app that can read contacts.",
            "Broad access can widen what is collected."
        ),

        ShadowCategory(
            "connections",
            "Account Connections",
            "Fictional: “Sign in with…” links between services.",
            "Connected accounts can share more than expected."
        ),

        ShadowCategory(
            "sharing",
            "Data-Sharing Choices",
            "Fictional: personalised-ads toggle left on.",
            "Defaults often decide what is shared."
        )
    )
}
