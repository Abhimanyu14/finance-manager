import 'package:financemanager/src/constants/sources.dart';
import 'package:financemanager/src/screens/balance_details/balance_details_sources_list.dart';
import 'package:flutter/material.dart';

class BalanceDetailsPageBody extends StatelessWidget {
  const BalanceDetailsPageBody({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(
    BuildContext context,
  ) {
    return BalanceDetailsSourcesList(
      sources: sources,
    );
  }
}
