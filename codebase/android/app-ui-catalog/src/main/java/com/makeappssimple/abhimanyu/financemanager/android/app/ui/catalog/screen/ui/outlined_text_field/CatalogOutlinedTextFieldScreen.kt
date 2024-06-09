package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.outlined_text_field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextField
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.MyOutlinedTextFieldData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar

@Composable
public fun CatalogOutlinedTextFieldScreen(
    navigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    MyScaffold(
        sheetContent = {},
        coroutineScope = coroutineScope,
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_outlined_text_field,
                navigationAction = navigateUp,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    all = 16.dp,
                ),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement
                .spacedBy(
                    space = 16.dp,
                ),
        ) {
            MyOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                data = MyOutlinedTextFieldData(
                    labelTextStringResourceId = R.string.screen_outlined_text_field_label,
                    trailingIconContentDescriptionTextStringResourceId = R.string.common_empty_string,
                ),
            )
            MyOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                data = MyOutlinedTextFieldData(
                    isLoading = true,
                    labelTextStringResourceId = R.string.screen_outlined_text_field_label,
                    trailingIconContentDescriptionTextStringResourceId = R.string.common_empty_string,
                ),
            )
        }
    }
}
