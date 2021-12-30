import 'package:financemanager/src/constants/colors.dart';
import 'package:flutter/material.dart';

class MyAppBar extends StatelessWidget with PreferredSizeWidget {
  const MyAppBar({
    Key? key,
    required this.title,
  }) : super(key: key);

  final String title;

  @override
  Widget build(
    BuildContext context,
  ) {
    return AppBar(
      title: Text(
        title,
        style: const TextStyle(
          color: primaryColor,
        ),
      ),
      centerTitle: true,
      backgroundColor: myBlue_10,
      elevation: 0.0,
      actionsIconTheme: const IconThemeData(
        color: primaryColor,
      ),
      iconTheme: const IconThemeData(
        color: primaryColor,
      ),
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}
