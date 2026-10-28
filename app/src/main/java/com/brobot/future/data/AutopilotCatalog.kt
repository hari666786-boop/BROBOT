package com.brobot.future.data

import com.brobot.future.model.ChecklistTask

object AutopilotCatalog {

    val tasks: List<ChecklistTask> = listOf(
        ChecklistTask(
            "recovery",
            "Review important account-recovery options",
            "Open each important account's settings yourself and check recovery email, number and questions."
        ),
        ChecklistTask(
            "mfa",
            "Learn how multifactor authentication works",
            "Read how a second step protects a sign-in and where it can be enabled."
        ),
        ChecklistTask(
            "permissions",
            "Review app permissions manually",
            "In your phone's settings, look for apps with access they do not need."
        ),
        ChecklistTask(
            "links",
            "Learn to recognise suspicious links",
            "Practise checking sender, spelling and destination before tapping."
        ),
        ChecklistTask(
            "unique",
            "Check that important accounts use unique passwords",
            "Make sure email and financial accounts do not share a password."
        ),
        ChecklistTask(
            "public",
            "Review publicly visible personal information",
            "Look at what your public profiles show to strangers."
        )
    )
}
