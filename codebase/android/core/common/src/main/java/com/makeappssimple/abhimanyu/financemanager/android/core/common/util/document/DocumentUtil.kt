package com.makeappssimple.abhimanyu.financemanager.android.core.common.util.document

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDateAndTime
import java.time.Instant
import java.time.ZoneId

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

// TODO(Abhi): To inject this method
private fun getFormattedDateAndTime(
    timestamp: Long = Instant.now().toEpochMilli(),
    zoneId: ZoneId = getSystemDefaultZoneId(),
): String {
    return Instant
        .ofEpochMilli(timestamp)
        .formattedDateAndTime(
            zoneId = zoneId,
        )
}

private fun getSystemDefaultZoneId(): ZoneId {
    return ZoneId.systemDefault()
}
