package com.makeappssimple.abhimanyu.financemanager.android.rules

import com.makeappssimple.abhimanyu.financemanager.android.rules.context.ContextOrder
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

public class RuleProvider : RuleSetProvider {
    override val ruleSetId: String = "extra-rules"
    override fun instance(config: Config): RuleSet {
        return RuleSet(ruleSetId, listOf(ContextOrder(config)))
    }
}
