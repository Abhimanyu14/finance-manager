import 'package:financemanager/src/models/transactions.dart';
import 'package:flutter/material.dart';

class TransactionsList extends StatelessWidget {
  const TransactionsList({
    Key? key,
    required this.transactions,
  }) : super(key: key);

  final Transactions transactions;

  @override
  Widget build(
    BuildContext context,
  ) {
    return Flexible(
      child: ListView.builder(
        physics: const BouncingScrollPhysics(),
        itemCount: transactions.value.length,
        itemBuilder: (
          BuildContext context,
          int index,
        ) {
          return ListTile(
            title: Text(
              transactions.value[index].title,
            ),
            subtitle: Text(
              transactions.value[index].amount.value.toString(),
            ),
          );
        },
      ),
    );
  }
}
