import 'package:financemanager/src/constants/colors.dart';
import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/widgets/bottom_app_bar_button.dart';
import 'package:flutter/material.dart';

class MyHomePageBottomAppBar extends StatelessWidget {
  const MyHomePageBottomAppBar({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(
    BuildContext context,
  ) {
    return BottomAppBar(
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
    );
  }
}
