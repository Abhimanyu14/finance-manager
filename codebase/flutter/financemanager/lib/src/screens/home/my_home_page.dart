import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/screens/home/my_home_page_body.dart';
import 'package:financemanager/src/screens/home/my_home_page_bottom_app_bar.dart';
import 'package:financemanager/src/screens/home/my_home_page_floating_action_button.dart';
import 'package:financemanager/src/widgets/my_app_bar.dart';
import 'package:financemanager/src/widgets/scaffold_body_container.dart';
import 'package:flutter/material.dart';

// ignore: todo
// TODO: TO resolve Google font dependencies
// import 'package:google_fonts/google_fonts.dart';

class MyHomePage extends StatelessWidget {
  const MyHomePage({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(
    BuildContext context,
  ) {
    return const Scaffold(
      appBar: MyAppBar(
        title: myHomePageAppBarTitle,
      ),
      body: ScaffoldBodyContainer(
        child: MyHomePageBody(),
      ),
      floatingActionButton: MyHomePageFloatingActionButton(),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      bottomNavigationBar: MyHomePageBottomAppBar(),
    );
  }
}
