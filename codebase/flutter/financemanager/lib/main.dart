import 'package:financemanager/src/constants/colors.dart';
import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/models/transactions.dart';
import 'package:financemanager/src/screens/home/my_home_page.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<Transactions>(
      create: (context) => Transactions(),
      child: MaterialApp(
        home: MyHomePage(),
        title: APP_TITLE,
        theme: ThemeData(
          visualDensity: VisualDensity.adaptivePlatformDensity,
          primaryColor: PRIMARY_COLOR,
        ),
      ),
    );
  }
}
