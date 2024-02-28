package com.makeappssimple.abhimanyu.financemanager.android.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

/**
 * An issue registry that checks for incorrect usages of Compose Material APIs over equivalents in
 * the project design system module.
 */
class MyIssueRegistry : IssueRegistry() {
    override val issues = listOf(
        DesignSystemDetector.ISSUE,
        LintStringDetector.ISSUE,
    )

    override val api: Int = CURRENT_API

    override val minApi: Int = 12

    override val vendor: Vendor = Vendor(
        vendorName = "Make Apps Simple",
        feedbackUrl = "https://github.com/Abhimanyu14/finance-manager/issues",
        contact = "https://github.com/Abhimanyu14/finance-manager"
    )
}
