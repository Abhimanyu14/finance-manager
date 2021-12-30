import 'package:financemanager/src/models/transactions.dart';
import 'package:financemanager/src/screens/home/my_home_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:provider/provider.dart';

late Transactions transactions;

Widget createMyHomePage() {
  return ChangeNotifierProvider<Transactions>(
    create: (context) {
      transactions = Transactions();
      return transactions;
    },
    child: const MaterialApp(
      home: MyHomePage(),
    ),
  );
}

void main() {
  group('My Home Page Widget Tests : ', () {
    testWidgets('Testing if floating action button shows up', (tester) async {
      await tester.pumpWidget(createMyHomePage());
      expect(find.byType(FloatingActionButton), findsOneWidget);
    });

    testWidgets('Testing if floating action button icon is correct',
        (tester) async {
      await tester.pumpWidget(createMyHomePage());
      expect(find.byIcon(Icons.add_rounded), findsOneWidget);
    });

    testWidgets('Testing if bottom app bar shows up', (tester) async {
      await tester.pumpWidget(createMyHomePage());
      expect(find.byType(BottomAppBar), findsOneWidget);
    });

    testWidgets('Testing if bottom app bar icons are correct', (tester) async {
      await tester.pumpWidget(createMyHomePage());
      expect(find.byIcon(Icons.menu_rounded), findsOneWidget);
      expect(find.byIcon(Icons.more_vert_rounded), findsOneWidget);
    });

    testWidgets('Testing if title shows up', (tester) async {
      await tester.pumpWidget(createMyHomePage());
      expect(find.text('My Transactions'), findsOneWidget);
    });
  });
}
