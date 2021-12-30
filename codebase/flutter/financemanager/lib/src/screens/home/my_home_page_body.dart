import 'package:financemanager/src/models/transactions.dart';
import 'package:financemanager/src/screens/home/balance_card.dart';
import 'package:financemanager/src/screens/home/transactions_list.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class MyHomePageBody extends StatelessWidget {
  const MyHomePageBody({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(
    BuildContext context,
  ) {
    Transactions transactions = Provider.of<Transactions>(context);
    return Column(
      children: [
        const BalanceCard(),
        TransactionsList(
          transactions: transactions,
        ),
      ],
    );
  }
}
