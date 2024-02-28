package com.makeappssimple.abhimanyu.financemanager.android.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.ULambdaExpression

class TrailingLambdaDetector : Detector(), Detector.UastScanner {
    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(
            ULambdaExpression::class.java,
        )
    }

    override fun createUastHandler(
        context: JavaContext,
    ): UElementHandler {
        return object : UElementHandler() {
            override fun visitLambdaExpression(
                node: ULambdaExpression,
            ) {
                if (node.isTrailingLambda() && node.isNotWhitelisted()) {
                    context.report(
                        issue = ISSUE_TRAILING_LAMBDA,
                        scope = node,
                        location = context.getLocation(node),
                        message = "Trailing lambdas are not allowed",
                    )
                }
            }
        }
    }

    private fun ULambdaExpression.isTrailingLambda(): Boolean {
        val parent = uastParent
        return parent is UCallExpression && parent.valueArguments.size == 1 && parent.valueArguments[0] == this
    }

    private fun ULambdaExpression.isNotWhitelisted(): Boolean {
        return isParameterNamedContent()
    }

    private fun ULambdaExpression.isParameterNamedContent(): Boolean {
        // Get the parent expression (method call)
        val parent = getParent() ?: return false

        // Get the parameter names of the method
        val parameterNames = getParameterNames() ?: return false

        // Check if the lambda is the last argument
        val lambdaIndex = parent.valueArguments.indexOf(this)
        return parameterNames[lambdaIndex] == "content"
    }

    private fun ULambdaExpression.getParent(): UCallExpression? {
        val parent = uastParent as? UCallExpression ?: return null
        return parent
    }

    private fun ULambdaExpression.getParameterNames(): List<String>? {
        // Get the method being called
        val method = getMethod() ?: return null

        // Get the parameter names of the method
        val parameterNames = method.parameters.map {
            it.name.orEmpty()
        }

        return parameterNames
    }

    private fun ULambdaExpression.getMethod(): PsiMethod? {
        // Get the parent expression (method call)
        val parent = getParent()

        // Get the method being called
        val method = parent?.resolve() ?: return null

        return method
    }

    private fun PsiMethod.isComposable(): Boolean {
        return this.hasAnnotation("androidx.compose.runtime.Composable")
    }

    companion object {
        @JvmField
        val ISSUE_TRAILING_LAMBDA = Issue.create(
            id = "TrailingLambda",
            briefDescription = "Trailing lambdas should not be used",
            explanation = "Trailing lambdas are not allowed in this codebase.",
            category = Category.CORRECTNESS,
            priority = 5,
            severity = Severity.ERROR,
            implementation = Implementation(
                TrailingLambdaDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            ),
        )
    }
}
