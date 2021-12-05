import 'package:financemanager/src/screens/add_transaction/add_transaction_page.dart';
import 'package:flutter/material.dart';

void navigateFromHomePageToAddTransactionPage(BuildContext context) {
  Navigator.push(
    context,
    MaterialPageRoute(
      builder: (context) => AddTransactionPage(),
    ),
  );
}
