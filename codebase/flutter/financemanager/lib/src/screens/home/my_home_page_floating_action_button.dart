import 'package:financemanager/src/constants/colors.dart';
import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/utilities/my_navigator.dart';
import 'package:flutter/material.dart';

class MyHomePageFloatingActionButton extends StatelessWidget {
  const MyHomePageFloatingActionButton({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(
    BuildContext context,
  ) {
    return FloatingActionButton(
      child: const Icon(
        Icons.add_rounded,
      ),
      tooltip: myHomePageFloatingActionButtonTooltipAddTransaction,
      backgroundColor: primaryColor,
      onPressed: () {
        handleAddTransactionPageNavigation(context);
      },
    );
  }

  void handleAddTransactionPageNavigation(
    BuildContext context,
  ) {
    navigateFromHomePageToAddTransactionPage(
      context,
    );
  }
}
