import 'package:financemanager/src/constants/colors.dart';
import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/utilities/my_navigator.dart';
import 'package:flutter/material.dart';

class BalanceCard extends StatelessWidget {
  const BalanceCard({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(
    BuildContext context,
  ) {
    return BalanceCardBackground(
      onTap: () {
        navigateFromHomePageToBalanceDetailsPage(
          context,
        );
      },
      child: const BalanceCardContent(
        totalBalance: '$symbolInr 14,27,458',
      ),
    );
  }
}

class BalanceCardBackground extends StatelessWidget {
  const BalanceCardBackground({
    Key? key,
    required this.child,
    required this.onTap,
  }) : super(key: key);

  final Widget child;
  final VoidCallback onTap;

  final BorderRadius _borderRadius = const BorderRadius.all(
    Radius.circular(16.0),
  );

  final LinearGradient _linearGradient = const LinearGradient(
    colors: [
      myBlue_700,
      myBlue_700,
    ],
    begin: Alignment.topLeft,
    end: Alignment.bottomRight,
  );

  @override
  Widget build(
    BuildContext context,
  ) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Card(
        elevation: 0.0,
        shape: RoundedRectangleBorder(
          borderRadius: _borderRadius,
        ),
        child: Container(
          decoration: BoxDecoration(
            gradient: _linearGradient,
            borderRadius: _borderRadius,
          ),
          child: Material(
            color: Colors.transparent,
            child: InkWell(
              onTap: onTap,
              borderRadius: _borderRadius,
              child: child,
            ),
          ),
        ),
      ),
    );
  }
}

class BalanceCardContent extends StatelessWidget {
  const BalanceCardContent({
    Key? key,
    required String totalBalance,
  })  : _totalBalance = totalBalance,
        super(key: key);

  final String _totalBalance;

  @override
  Widget build(
    BuildContext context,
  ) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        const Padding(
          padding: EdgeInsets.only(
            top: 16.0,
            left: 16.0,
            right: 16.0,
            bottom: 0.0,
          ),
          child: Text(
            'TOTAL BALANCE',
            textAlign: TextAlign.center,
            style: TextStyle(
              color: myBlue_10,
              fontSize: 14.0,
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.only(
            top: 8.0,
            left: 16.0,
            right: 16.0,
            bottom: 0.0,
          ),
          child: Text(
            _totalBalance,
            textAlign: TextAlign.center,
            style: const TextStyle(
              color: myBlue_10,
              fontSize: 24.0,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
        const Padding(
          padding: EdgeInsets.only(
            top: 0.0,
            left: 16.0,
            right: 16.0,
            bottom: 0.0,
          ),
          child: Icon(
            Icons.keyboard_arrow_down_rounded,
            color: myBlue_10,
          ),
        ),
      ],
    );
  }
}
