package com.makeappssimple.abhimanyu.financemanager.android.core.common.stringencoder

import android.net.Uri
import javax.inject.Inject

class StringEncoderImpl @Inject constructor() : StringEncoder {
    override fun encodeString(
        string: String,
    ): String {
        return Uri.encode(string)
    }
}
