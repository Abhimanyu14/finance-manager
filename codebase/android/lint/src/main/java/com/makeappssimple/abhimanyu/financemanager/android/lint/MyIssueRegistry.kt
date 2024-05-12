package com.makeappssimple.abhimanyu.financemanager.android.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

/**
 * An issue registry that checks for custom issues.
 */
class MyIssueRegistry : IssueRegistry() {
    override val issues = listOf(
        DesignSystemDetector.ISSUE_DESIGN_SYSTEM,
        // LintStringDetector.ISSUE_LINT_STRING,
        TrailingLambdaDetector.ISSUE_TRAILING_LAMBDA,
    )

    override val api: Int = CURRENT_API

    override val minApi: Int = 12

    override val vendor: Vendor = Vendor(
        vendorName = "Make Apps Simple",
        feedbackUrl = "https://github.com/Abhimanyu14/finance-manager/issues",
        contact = "https://github.com/Abhimanyu14/finance-manager"
    )
}
