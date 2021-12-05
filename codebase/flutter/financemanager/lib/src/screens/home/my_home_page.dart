import 'package:financemanager/src/constants/colors.dart';
import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/models/transactions.dart';
import 'package:financemanager/src/utilities/my_navigator.dart';
import 'package:financemanager/src/widgets/bottom_app_bar_button.dart';
import 'package:financemanager/src/widgets/page_title.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

// ignore: todo
// TODO: Handle status bar icon color
// import 'package:flutter/services.dart';
// ignore: todo
// TODO: TO resolve Google font dependencies
// import 'package:google_fonts/google_fonts.dart';

class MyHomePage extends StatelessWidget {
  const MyHomePage({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Transactions transactions = Provider.of<Transactions>(context);
    print(transactions.toString());
    // ignore: todo
    // TODO: Handle status bar icon color
    // SystemChrome.setSystemUIOverlayStyle(
    //   SystemUiOverlayStyle(
    //     statusBarColor: Colors.transparent,
    //     statusBarIconBrightness: Brightness.light,
    //     systemNavigationBarIconBrightness: Brightness.light,
    //   ),
    // );

    return Scaffold(
      body: SafeArea(
        child: Column(
          children: const [
            PageTitle(
              title: myHomePagePageTitle,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        child: const Icon(
          Icons.add_rounded,
        ),
        tooltip: myHomePageFloatingActionButtonTooltipAddTransaction,
        backgroundColor: primaryColor,
        onPressed: () {
          handleAddTransactionPageNavigation(context);
        },
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      bottomNavigationBar: BottomAppBar(
        color: primaryColor,
        shape: const CircularNotchedRectangle(),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          mainAxisSize: MainAxisSize.max,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            BottomAppBarButton(
              icon: Icons.menu_rounded,
              onPressed: () {},
              tooltip: myHomePageBottomAppBarButtonTooltipMenu,
            ),
            BottomAppBarButton(
              icon: Icons.more_vert_rounded,
              onPressed: () {},
              tooltip: myHomePageBottomAppBarButtonTooltipMoreOptions,
            ),
          ],
        ),
      ),
    );
  }

  void handleAddTransactionPageNavigation(BuildContext context) {
    navigateFromHomePageToAddTransactionPage(context);
  }
}
