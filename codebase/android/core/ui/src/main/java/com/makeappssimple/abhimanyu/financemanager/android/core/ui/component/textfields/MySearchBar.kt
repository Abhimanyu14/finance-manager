package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfields

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Immutable
data class MySearchBarData(
    val autoFocus: Boolean = true,
    val placeholderText: String,
    val searchText: String,
)

@Immutable
data class MySearchBarEvents(
    val onSearch: (() -> Unit)? = null,
    val onValueChange: (updatedSearchText: String) -> Unit = {},
)

@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    data: MySearchBarData,
    events: MySearchBarEvents = MySearchBarEvents(),
) {
    val focusManager = LocalFocusManager.current
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
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
        onValueChange = events.onValueChange,
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
                events.onSearch?.invoke()
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
                interactionSource = remember<MutableInteractionSource> {
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
                        imageVector = Icons.Rounded.Search,
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
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    focusManager.clearFocus()
                                    events.onValueChange("")
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

@Deprecated("Use MySearchBar instead of this")
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    autoFocus: Boolean = true,
    searchText: String,
    placeholderText: String,
    onValueChange: (updatedSearchText: String) -> Unit,
    onSearch: (() -> Unit)? = null,
) {
    val focusRequester: FocusRequester = remember {
        FocusRequester()
    }

    if (autoFocus) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            focusRequester.requestFocus()
        }
    }

    TextField(
        value = searchText,
        onValueChange = {
            onValueChange(it)
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch?.invoke()
            },
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        shape = CircleShape,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
            )
        },
        trailingIcon = if (searchText.isNotBlank()) {
            {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(
                            shape = CircleShape,
                        )
                        .clickable {
                            onValueChange("")
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
                text = placeholderText,
                style = MaterialTheme.typography.headlineLarge
                    .copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
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
