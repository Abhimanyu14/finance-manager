package com.makeappssimple.abhimanyu.financemanager.android.feature.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun HomeBottomAppBar(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    updateHomeBottomSheetType: () -> Unit,
) {
    BottomAppBar(
        cutoutShape = CircleShape,
    ) {
        IconButton(
            onClick = {
                updateHomeBottomSheetType()
                toggleModalBottomSheetState(
                    coroutineScope = coroutineScope,
                    modalBottomSheetState = modalBottomSheetState,
                )
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = stringResource(
                    id = R.string.screen_home_bottom_app_bar_button_content_description,
                ),
                tint = MaterialTheme.colorScheme.surface,
            )
        }
        Spacer(
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
    }
}
