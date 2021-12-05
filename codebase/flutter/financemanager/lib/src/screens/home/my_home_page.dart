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
// TODO: TO resolve Google font depedencies
// import 'package:google_fonts/google_fonts.dart';

class MyHomePage extends StatelessWidget {
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
          children: [
            PageTitle(
              title: MY_HOME_PAGE_PAGE_TITLE,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        child: const Icon(
          Icons.add_rounded,
        ),
        tooltip: MY_HOME_PAGE_FLOATING_ACTION_BUTTON_TOOLTIP_ADD_TRANSACTION,
        backgroundColor: PRIMARY_COLOR,
        onPressed: () {
          handleAddTransactionPageNavigation(context);
        },
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      bottomNavigationBar: BottomAppBar(
        color: PRIMARY_COLOR,
        shape: CircularNotchedRectangle(),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          mainAxisSize: MainAxisSize.max,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            BottomAppBarButton(
              icon: Icons.menu_rounded,
              onPressed: () {},
              tooltip: MY_HOME_PAGE_BOTTOM_APP_BAR_BUTTON_TOOLTIP_MENU,
            ),
            BottomAppBarButton(
              icon: Icons.more_vert_rounded,
              onPressed: () {},
              tooltip: MY_HOME_PAGE_BOTTOM_APP_BAR_BUTTON_TOOLTIP_MORE_OPTIONS,
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
