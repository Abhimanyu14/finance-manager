package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.foundation.color

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.R
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.top_app_bar.MyTopAppBar
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.scaffold.MyScaffold

@Composable
fun ColorScreen(
    navigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    MyScaffold(
        sheetContent = {},
        onClick = { },
        coroutineScope = coroutineScope,
        onBackPress = { },
        topBar = {
            MyTopAppBar(
                titleTextStringResourceId = R.string.screen_color,
                navigationAction = navigateUp,
            )
        },
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
        ) {
            /**
             * Primary
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                text = "Primary",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onPrimary,
                textColor = MaterialTheme.colorScheme.primary,
                text = "On Primary",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                text = "Primary Container",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onPrimaryContainer,
                textColor = MaterialTheme.colorScheme.primaryContainer,
                text = "On Primary Container",
            )

            /**
             * Secondary
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.secondary,
                textColor = MaterialTheme.colorScheme.onSecondary,
                text = "Secondary",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onSecondary,
                textColor = MaterialTheme.colorScheme.secondary,
                text = "On Secondary",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                text = "Secondary Container",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onSecondaryContainer,
                textColor = MaterialTheme.colorScheme.secondaryContainer,
                text = "On Secondary Container",
            )

            /**
             * Tertiary
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.tertiary,
                textColor = MaterialTheme.colorScheme.onTertiary,
                text = "Tertiary",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onTertiary,
                textColor = MaterialTheme.colorScheme.tertiary,
                text = "On Tertiary",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.tertiaryContainer,
                textColor = MaterialTheme.colorScheme.onTertiaryContainer,
                text = "Tertiary Container",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onTertiaryContainer,
                textColor = MaterialTheme.colorScheme.tertiaryContainer,
                text = "On Tertiary Container",
            )

            /**
             * Error
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.error,
                textColor = MaterialTheme.colorScheme.onError,
                text = "Surface",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onError,
                textColor = MaterialTheme.colorScheme.error,
                text = "On Surface",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.errorContainer,
                textColor = MaterialTheme.colorScheme.onErrorContainer,
                text = "Surface Variant",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onErrorContainer,
                textColor = MaterialTheme.colorScheme.errorContainer,
                text = "On Surface Variant",
            )

            /**
             * Background
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.background,
                textColor = MaterialTheme.colorScheme.onBackground,
                text = "Background",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onBackground,
                textColor = MaterialTheme.colorScheme.background,
                text = "On Background",
            )

            /**
             * Surface
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.surface,
                textColor = MaterialTheme.colorScheme.onSurface,
                text = "Surface",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onSurface,
                textColor = MaterialTheme.colorScheme.surface,
                text = "On Surface",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.surfaceVariant,
                textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                text = "Surface Variant",
            )
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.onSurfaceVariant,
                textColor = MaterialTheme.colorScheme.surfaceVariant,
                text = "On Surface Variant",
            )

            /**
             * Outline
             */
            ColorSwatch(
                surfaceColor = MaterialTheme.colorScheme.outline,
                textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                text = "Outline",
            )
        }
    }
}

@Composable
private fun ColorSwatch(
    surfaceColor: Color,
    textColor: Color,
    text: String,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                height = 160.dp,
            )
            .padding(
                all = 16.dp,
            ),
        color = surfaceColor,
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        MyText(
            modifier = Modifier
                .padding(
                    all = 24.dp,
                ),
            text = text,
            style = MaterialTheme.typography.displaySmall
                .copy(
                    color = textColor,
                ),
        )
    }
}
