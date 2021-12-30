import 'package:financemanager/src/constants/colors.dart';
import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/models/my_transaction/my_transaction.dart';
import 'package:financemanager/src/models/transactions.dart';
import 'package:financemanager/src/utilities/my_navigator.dart';
import 'package:flutter/material.dart';

class AddTransactionPageFloatingActionButton extends StatelessWidget {
  const AddTransactionPageFloatingActionButton({
    Key? key,
    required GlobalKey<FormState> formKey,
    required this.transactions,
    required MyTransaction transaction,
  })  : _formKey = formKey,
        _transaction = transaction,
        super(key: key);

  final GlobalKey<FormState> _formKey;
  final Transactions transactions;
  final MyTransaction _transaction;

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
      child: const Icon(
        Icons.done_rounded,
      ),
      tooltip: addTransactionPageFloatingActionButtonTooltipSaveTransaction,
      backgroundColor: primaryColor,
      onPressed: () {
        handleFormSubmission(
          context: context,
          formKey: _formKey,
          transactions: transactions,
          transaction: _transaction,
        );
      },
    );
  }
}

void handleFormSubmission({
  required BuildContext context,
  required GlobalKey<FormState> formKey,
  required Transactions transactions,
  required MyTransaction transaction,
}) {
  transactions.add(transaction);
  bool isValid = formKey.currentState?.validate() ?? false;
  if (isValid) {
    navigateBack(context);
  }
}
