package com.makeappssimple.abhimanyu.financemanager.android.core.common.util.document

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.MimeTypeConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.getSystemDefaultZoneId
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.formattedDateAndTime
import java.time.Instant
import java.time.ZoneId

public class CreateJsonDocument : ActivityResultContracts.CreateDocument(
    mimeType = MimeTypeConstants.JSON,
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
