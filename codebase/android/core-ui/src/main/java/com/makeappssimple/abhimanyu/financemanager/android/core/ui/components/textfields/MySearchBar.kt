package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
        colors = TextFieldDefaults
            .textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
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

@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    autoFocus: Boolean = true,
    searchText: String,
    placeholderText: String,
    onValueChange: (updatedSearchText: String) -> Unit,
    onSearch: (() -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current
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

    BasicTextField(
        value = searchText,
        onValueChange = {
            onValueChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape,
            )
            .padding(
                horizontal = 0.dp,
                vertical = 0.dp,
            )
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
                onSearch?.invoke()
            },
        ),
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = {
            TextFieldDefaults.TextFieldDecorationBox(
                value = searchText,
                innerTextField = it,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = remember {
                    MutableInteractionSource()
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
                                    focusManager.clearFocus()
                                    onValueChange("")
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
                contentPadding = TextFieldDefaults
                    .textFieldWithoutLabelPadding(
                        top = 0.dp,
                        bottom = 0.dp,
                        start = 0.dp,
                        end = 0.dp,
                    ),
            )
        },
    )
}
