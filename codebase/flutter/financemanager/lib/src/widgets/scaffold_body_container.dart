import 'package:financemanager/src/constants/colors.dart';
import 'package:flutter/material.dart';

class ScaffoldBodyContainer extends StatelessWidget {
  const ScaffoldBodyContainer({
    Key? key,
    required this.child,
  }) : super(key: key);

  final Widget child;

  @override
  Widget build(
    BuildContext context,
  ) {
    return Container(
      color: myBlue_10,
      child: child,
      height: double.infinity,
    );
  }
}
