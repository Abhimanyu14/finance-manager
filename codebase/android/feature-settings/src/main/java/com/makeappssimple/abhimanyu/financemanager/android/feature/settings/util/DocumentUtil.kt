package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.util

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.getDateAndTimeString

internal const val JSON_MIMETYPE = "application/json"

internal class CreateJsonDocument : ActivityResultContracts.CreateDocument(
    mimeType = JSON_MIMETYPE,
) {
    override fun createIntent(
        context: Context,
        input: String,
    ): Intent {
        return super.createIntent(
            context = context,
            input = input,
        ).apply {
            putExtra(
                Intent.EXTRA_TITLE,
                getDateAndTimeString(System.currentTimeMillis()),
            )
        }
    }
}
