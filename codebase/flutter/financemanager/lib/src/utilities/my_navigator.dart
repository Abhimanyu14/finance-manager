import 'package:financemanager/src/screens/add_transaction/add_transaction_page.dart';
import 'package:financemanager/src/screens/balance_details/balance_details_page.dart';
import 'package:flutter/material.dart';

void navigateBack(
  BuildContext context,
) {
  Navigator.pop(context);
}

void navigateFromHomePageToAddTransactionPage(
  BuildContext context,
) {
  Navigator.push(
    context,
    MaterialPageRoute(
      builder: (context) => const AddTransactionPage(),
    ),
  );
}

void navigateFromHomePageToBalanceDetailsPage(
  BuildContext context,
) {
  Navigator.push(
    context,
    MaterialPageRoute(
      builder: (context) => const BalanceDetailsPage(),
    ),
  );
}
