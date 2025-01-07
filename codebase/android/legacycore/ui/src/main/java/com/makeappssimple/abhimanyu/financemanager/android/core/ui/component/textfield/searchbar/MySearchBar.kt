package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

private object MySearchBarConstants {
    val borderWidth = 1.dp
    val leadingIconPaddingBottom = 2.dp
    val leadingIconPaddingEnd = 2.dp
    val leadingIconPaddingStart = 12.dp
    val leadingIconPaddingTop = 2.dp
    const val leadingIconScale = 0.75F
    val loadingUIHeight = 40.dp
    val loadingUIPadding = 4.dp
    val loadingUIWidth = 80.dp
    val padding = 4.dp
    val shape = CircleShape
    val textPaddingBottom = 6.dp
    val textPaddingEnd = 16.dp
    val textPaddingStart = 16.dp
    val textPaddingStartWithIcon = 0.dp
    val textPaddingTop = 6.dp
}

@Composable
public fun MySearchBar(
    modifier: Modifier = Modifier,
    data: MySearchBarData,
    handleEvent: (event: MySearchBarEvent) -> Unit = {},
) {
    if (data.isLoading) {
        MySearchBarLoadingUI()
    } else {
        MySearchBarUI(
            modifier = modifier,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun MySearchBarUI(
    modifier: Modifier = Modifier,
    data: MySearchBarData,
    handleEvent: (event: MySearchBarEvent) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val focusRequester: FocusRequester = remember {
        FocusRequester()
    }

    if (data.autoFocus) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            focusRequester.requestFocus()
        }
    }

    BasicTextField(
        value = data.searchText,
        onValueChange = {
            handleEvent(
                MySearchBarEvent.OnSearchTextChange(
                    updatedSearchText = it,
                )
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = 40.dp,
            )
            .focusRequester(
                focusRequester = focusRequester,
            ),
        textStyle = MaterialTheme.typography.bodyLarge
            .copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                handleEvent(MySearchBarEvent.OnSearch)
            },
        ),
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = {
            TextFieldDefaults.DecorationBox(
                value = data.searchText,
                innerTextField = it,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                placeholder = {
                    MyText(
                        text = data.placeholderText,
                        style = MaterialTheme.typography.headlineLarge
                            .copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            ),
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = MyIcons.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(
                                all = 0.dp,
                            )
                            .size(
                                size = 20.dp,
                            ),
                    )
                },
                trailingIcon = if (data.searchText.isNotBlank()) {
                    {
                        Icon(
                            imageVector = MyIcons.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    focusManager.clearFocus()
                                    handleEvent(
                                        MySearchBarEvent.OnSearchTextChange(
                                            updatedSearchText = "",
                                        )
                                    )
                                }
                                .padding(
                                    all = 6.dp,
                                )
                                .size(
                                    size = 20.dp,
                                ),
                        )
                    }
                } else {
                    null
                },
                shape = CircleShape,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                ),
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 0.dp,
                ),
                container = {
                    Box(
                        modifier = Modifier
                            .requiredHeight(
                                height = 40.dp,
                            )
                            .clip(
                                shape = CircleShape,
                            )
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                            )
                    )
                },
            )
        },
    )
}

@Composable
private fun MySearchBarLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(
                MySearchBarConstants.loadingUIHeight,
            )
            .fillMaxWidth()
            .clip(
                shape = MySearchBarConstants.shape,
            )
            .shimmer(),
    )
}
