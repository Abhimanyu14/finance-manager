package com.makeappssimple.abhimanyu.financemanager.android.cre.common.stringdecoder

import android.net.Uri
import javax.inject.Inject

public class StringDecoderImpl @Inject constructor() : StringDecoder {
    override fun decodeString(
        encodedString: String,
    ): String {
        return Uri.decode(encodedString)
    }
}
