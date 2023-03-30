package com.makeappssimple.abhimanyu.financemanager.android.core.common.util

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts

const val JSON_MIMETYPE = "application/json"

class CreateJsonDocument : ActivityResultContracts.CreateDocument(
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
                getFormattedDateAndTime(),
            )
        }
    }
}
