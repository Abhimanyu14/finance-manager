package com.makeappssimple.abhimanyu.financemanager.android.lint

/**
 * Sample detector showing how to analyze Kotlin/Java code. This example
 * flags all string literals in the code that contain the word "lint".
 */
/*
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
                        issue = ISSUE_LINT_STRING,
                        scope = node,
                        location = context.getLocation(node),
                        message = "This code mentions `lint` string",
                    )
                }
            }
        }
    }

    companion object {
        */
/**
 * Issue describing the problem and pointing to the detector implementation.
 *//*

        @JvmField
        val ISSUE_LINT_STRING: Issue = Issue.create(
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
*/
