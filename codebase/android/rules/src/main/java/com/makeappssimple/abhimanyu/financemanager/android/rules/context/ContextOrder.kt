package com.makeappssimple.abhimanyu.financemanager.android.rules.context

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtNamedFunction

public class ContextOrder(config: Config) : Rule(config) {
    override val issue: Issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.Minor,
        description = ISSUE_DESCRIPTION,
        debt = Debt.FIVE_MINS,
    )

    //Triggers for every function
    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)
        val parameters = function.valueParameters
        //If parameter size smaller than 2, no need to check
        if (parameters.size < 2) {
            return
        }
        //Get parameter types like [String,Context,Fragment] etc
        val parameterTypeList = parameters.map { x -> x.children[0].text }
        //If Context is the first parameter, no need to check
        if (isContext(parameterTypeList[0])) {
            return
        }
        //If Context is not the first parameter, report issue
        else if (isContext(parameterTypeList)) {
            report(CodeSmell(issue, Entity.from(function), REPORT_MESSAGE))
        }
    }

    private fun isContext(s: String?): Boolean {
        return (s == CONTEXT || s == CONTEXT_WITH_QUESTION_MARK)
    }

    private fun isContext(s: List<String>): Boolean {
        return (s.contains(CONTEXT) || s.contains(CONTEXT_WITH_QUESTION_MARK))
    }

    public companion object {
        public const val CONTEXT: String = "Context"
        public const val CONTEXT_WITH_QUESTION_MARK: String = "$CONTEXT?"
        public const val REPORT_MESSAGE: String = "Context must be the first parameter"
        public const val ISSUE_DESCRIPTION: String =
            "This rule reports the method which doesn't use Context as the first parameter"
    }
}
