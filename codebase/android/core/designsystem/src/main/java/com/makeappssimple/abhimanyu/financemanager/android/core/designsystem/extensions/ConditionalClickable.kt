package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull

fun Modifier.conditionalClickable(
    role: Role? = null,
    onClickLabel: String? = null,
    onLongClickLabel: String? = null,
    onClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
): Modifier {
    return composed {
        this.conditionalClickable(
            indication = LocalIndication.current,
            interactionSource = remember {
                MutableInteractionSource()
            },
            role = role,
            onClickLabel = onClickLabel,
            onLongClickLabel = onLongClickLabel,
            onClick = onClick,
            onDoubleClick = onDoubleClick,
            onLongClick = onLongClick,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.conditionalClickable(
    indication: Indication?,
    interactionSource: MutableInteractionSource,
    role: Role? = null,
    onClickLabel: String? = null,
    onLongClickLabel: String? = null,
    onClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
): Modifier {
    return if (onClick.isNotNull() && onLongClick.isNotNull()) {
        this.then(
            other = Modifier
                .combinedClickable(
                    interactionSource = interactionSource,
                    indication = indication,
                    onClickLabel = onClickLabel,
                    role = role,
                    onLongClickLabel = onLongClickLabel,
                    onLongClick = onLongClick,
                    onDoubleClick = onDoubleClick,
                    onClick = onClick,
                )
        )
    } else if (onClick.isNotNull()) {
        this.then(
            other = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = indication,
                    onClickLabel = onClickLabel,
                    role = role,
                    onClick = onClick,
                )
        )
    } else {
        this
    }
}
