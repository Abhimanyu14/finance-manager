import 'package:financemanager/src/constants/colors.dart';
import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/models/transactions.dart';
import 'package:financemanager/src/screens/home/my_home_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(
    BuildContext context,
  ) {
    SystemChrome.setSystemUIOverlayStyle(
      const SystemUiOverlayStyle(
        systemNavigationBarColor: primaryColor,
        systemNavigationBarIconBrightness: Brightness.light,
        // systemNavigationBarDividerColor: Colors.white,
        statusBarColor: myBlue_10,
        statusBarIconBrightness: Brightness.dark,
        // statusBarBrightness: Brightness.dark,
      ),
    );

    return ChangeNotifierProvider<Transactions>(
      create: (context) => Transactions(),
      child: MaterialApp(
        debugShowCheckedModeBanner: false,
        title: appTitle,
        home: const MyHomePage(),
        theme: ThemeData(
          // brightness: Brightness.dark,
          visualDensity: VisualDensity.adaptivePlatformDensity,
          primaryColor: primaryColor,
          appBarTheme: const AppBarTheme(
            systemOverlayStyle: SystemUiOverlayStyle.dark,
          ),
        ),
      ),
    );
  }
}
