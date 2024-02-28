package com.makeappssimple.abhimanyu.financemanager.android.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Detector.UastScanner
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UElement
import org.jetbrains.uast.ULiteralExpression
import org.jetbrains.uast.evaluateString

/**
 * Sample detector showing how to analyze Kotlin/Java code. This example
 * flags all string literals in the code that contain the word "lint".
 */
class LintStringDetector : Detector(), UastScanner {
    override fun getApplicableUastTypes(): List<Class<out UElement?>> {
        return listOf(ULiteralExpression::class.java)
    }

    override fun createUastHandler(
        context: JavaContext,
    ): UElementHandler {
        // Note: Visiting UAST nodes is a pretty general purpose mechanism.
        // Lint has specialized support to do common things like "visit every class that extends
        // a given super class or implements a given interface", and
        // "visit every call site that calls a method by a given name" etc.
        //
        // Take a careful look at UastScanner and the various existing lint check implementations
        // before doing things the "hard way".
        //
        // Also be aware of context.getJavaEvaluator() which provides a lot of utility functionality.
        return object : UElementHandler() {
//            override fun visitMethod(node: UMethod) {
//                super.visitMethod(node)
//
//                val name = node.name
//                context.report(
//                    ISSUE, node, context.getLocation(node),
//                    "This Method has a warning"
//                )
//            }

            override fun visitLiteralExpression(
                node: ULiteralExpression,
            ) {
                super.visitLiteralExpression(node)

                val string = node.evaluateString() ?: return
                if (string.contains("lint") && string.matches(Regex(".*\\blint\\b.*"))) {
                    context.report(
                        ISSUE, node, context.getLocation(node),
                        "This code mentions `lint`: **Congratulations**"
                    )
                }
            }
        }
    }

    companion object {
        /**
         * Issue describing the problem and pointing to the detector implementation.
         */
        @JvmField
        val ISSUE: Issue = Issue.create(
            // ID: used in @SuppressLint warnings etc
            id = "SampleId",
            // Title -- shown in the IDE's preference dialog, as category headers in the
            // Analysis results window, etc
            briefDescription = "Lint Mentions",
            // Full explanation of the issue; you can use some markdown markup such as
            // `monospace`, *italic*, and **bold**.
            explanation = """This check highlights string literals in code which mentions the word `lint`.""", // no need to .trimIndent(), lint does that automatically
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 7,
            severity = Severity.ERROR,
            implementation = Implementation(
                LintStringDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}
