package com.makeappssimple.abhimanyu.financemanager.android.core.common.decoder

import android.net.Uri
import javax.inject.Inject

class StringDecoderImpl @Inject constructor() : StringDecoder {
    override fun decodeString(
        encodedString: String,
    ): String {
        return Uri.decode(encodedString)
    }
}
