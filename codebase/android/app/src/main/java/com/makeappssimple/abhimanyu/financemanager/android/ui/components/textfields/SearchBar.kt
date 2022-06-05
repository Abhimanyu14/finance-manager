package com.makeappssimple.abhimanyu.financemanager.android.ui.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Blue100
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Surface
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Transparent

data class SearchBarData(
    val searchText: String,
    val placeholderText: String,
    val onValueChange: (updatedSearchText: String) -> Unit,
    val onSearch: (() -> Unit)? = null,
)

@Composable
fun SearchBarContainer(
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = Surface,
            )
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            ),
    ) {
        content()
    }
}

@Composable
fun SearchBar(
    data: SearchBarData,
    modifier: Modifier = Modifier,
) {
    val focusRequester: FocusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        focusRequester.requestFocus()
    }

    TextField(
        value = data.searchText,
        onValueChange = {
            data.onValueChange(it)
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                data.onSearch?.invoke()
            },
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        shape = RoundedCornerShape(
            percent = 50,
        ),
        singleLine = true,
        colors = TextFieldDefaults
            .textFieldColors(
                containerColor = Blue100,
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
            ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
            )
        },
        trailingIcon = if (data.searchText.isNotBlank()) {
            {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(
                            shape = CircleShape,
                        )
                        .clickable {
                            data.onValueChange("")
                        }
                        .padding(
                            all = 8.dp,
                        ),
                )
            }
        } else {
            null
        },
        placeholder = {
            MyText(
                text = data.placeholderText,
                style = TextStyle(
                    color = DarkGray,
                    fontWeight = FontWeight.Bold,
                ),
            )
        },
        modifier = modifier
            .focusRequester(
                focusRequester = focusRequester,
            )
            .fillMaxWidth(),
    )
}
