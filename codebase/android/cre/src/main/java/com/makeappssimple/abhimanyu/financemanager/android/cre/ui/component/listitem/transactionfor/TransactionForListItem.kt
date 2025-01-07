package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.transactionfor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.cre.R
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.util.minimumListItemHeight

@Composable
public fun TransactionForListItem(
    modifier: Modifier = Modifier,
    data: TransactionForListItemData,
    handleEvent: (event: TransactionForListItemEvent) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = minimumListItemHeight,
            )
            .conditionalClickable(
                onClick = {
                    handleEvent(TransactionForListItemEvent.OnClick)
                },
            )
            .padding(
                start = 16.dp,
                end = 8.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MyText(
            text = data.title,
            style = MaterialTheme.typography.bodyLarge
                .copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
        )
        if (data.isMoreOptionsIconButtonVisible) {
            MyIconButton(
                tint = MaterialTheme.colorScheme.onBackground,
                imageVector = MyIcons.MoreVert,
                contentDescriptionStringResourceId = R.string.transaction_for_list_item_more_options_content_description,
                onClick = {
                    handleEvent(TransactionForListItemEvent.OnMoreOptionsIconButtonClick)
                },
            )
        }
    }
}
