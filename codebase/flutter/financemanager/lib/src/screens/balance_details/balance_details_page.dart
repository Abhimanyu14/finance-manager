import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/screens/balance_details/balance_details_page_body.dart';
import 'package:financemanager/src/widgets/my_app_bar.dart';
import 'package:financemanager/src/widgets/scaffold_body_container.dart';
import 'package:flutter/material.dart';

class BalanceDetailsPage extends StatelessWidget {
  const BalanceDetailsPage({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(
    BuildContext context,
  ) {
    return const Scaffold(
      appBar: MyAppBar(
        title: balanceDetailsPageAppBarTitle,
      ),
      body: ScaffoldBodyContainer(
        child: BalanceDetailsPageBody(),
      ),
    );
  }
}
