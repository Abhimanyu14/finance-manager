package com.makeappssimple.abhimanyu.financemanager.android.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.ULiteralExpression
import org.jetbrains.uast.UQualifiedReferenceExpression
import org.jetbrains.uast.evaluateString

class DesignSystemDetector : Detector(), Detector.UastScanner {
    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(
            ULiteralExpression::class.java,
            UCallExpression::class.java,
            UQualifiedReferenceExpression::class.java
        )
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                val name = node.methodName ?: return
                val preferredName = METHOD_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }

            override fun visitQualifiedReferenceExpression(node: UQualifiedReferenceExpression) {
                val name = node.receiver.asRenderString()
                val preferredName = RECEIVER_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }
        }
    }

    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            id = "DesignSystem",
            briefDescription = "Design system",
            explanation = "This check highlights calls in code that use Compose Material " +
                    "Composables instead of equivalents from the project design system  module.",
            category = Category.CORRECTNESS,
            priority = 7,
            severity = Severity.ERROR,
            implementation = Implementation(
                DesignSystemDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            ),
        )

        // Unfortunately :lint is a Java module and thus can't depend on the :core-designsystem
        // Android module, so we can't use composable function references (eg. ::Button.name)
        // instead of hardcoded names.
        val METHOD_NAMES = mapOf(
            "MaterialTheme" to "MyTheme",
            "Text" to "MyText",
            "Button" to "MyFilledButton",
            "OutlinedButton" to "MyOutlinedButton",
            "TextButton" to "MyTextButton",
            "FilterChip" to "MyFilterChip",
            "ElevatedFilterChip" to "MyFilterChip",
            "DropdownMenu" to "MyDropdownMenu",
            "NavigationBar" to "MyNavigationBar",
            "NavigationBarItem" to "MyNavigationBarItem",
            "NavigationRail" to "MyNavigationRail",
            "NavigationRailItem" to "MyNavigationRailItem",
            "TabRow" to "MyTabRow",
            "Tab" to "MyTab",
            "IconToggleButton" to "MyToggleButton",
            "FilledIconToggleButton" to "MyToggleButton",
            "FilledTonalIconToggleButton" to "MyToggleButton",
            "OutlinedIconToggleButton" to "MyToggleButton",
            "CenterAlignedTopAppBar" to "MyTopAppBar",
            "SmallTopAppBar" to "MyTopAppBar",
            "MediumTopAppBar" to "MyTopAppBar",
            "LargeTopAppBar" to "MyTopAppBar",
        )
        val RECEIVER_NAMES = mapOf(
            "Icons" to "MyIcons"
        )

        fun reportIssue(
            context: JavaContext,
            node: UElement,
            name: String,
            preferredName: String,
        ) {
            context.report(
                ISSUE,
                node,
                context.getLocation(node),
                "Using $name instead of $preferredName"
            )
        }
    }
}
