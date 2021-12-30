import 'package:financemanager/src/models/transactions.dart';
import 'package:financemanager/src/screens/add_transaction/add_transaction_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:provider/provider.dart';

late Transactions transactions;

Widget createAddTransactionPage() {
  return ChangeNotifierProvider<Transactions>(
    create: (context) {
      transactions = Transactions();
      return transactions;
    },
    child: MaterialApp(
      home: AddTransactionPage(),
    ),
  );
}

void main() {
  group('Add Transaction Page Widget Tests : ', () {
    testWidgets('Testing if floating action button shows up', (tester) async {
      await tester.pumpWidget(createAddTransactionPage());
      expect(find.byType(FloatingActionButton), findsOneWidget);
    });

    testWidgets('Testing if floating action button icon is correct',
        (tester) async {
      await tester.pumpWidget(createAddTransactionPage());
      expect(find.byIcon(Icons.done_rounded), findsOneWidget);
    });

    testWidgets('Testing if app bar shows up', (tester) async {
      await tester.pumpWidget(createAddTransactionPage());
      expect(find.byType(AppBar), findsOneWidget);
    });

    testWidgets('Testing if app bar title is correct', (tester) async {
      await tester.pumpWidget(createAddTransactionPage());
      expect(find.text('Enter Transaction Details'), findsOneWidget);
    });

    testWidgets('Testing amount form field', (tester) async {
      await tester.pumpWidget(createAddTransactionPage());
      expect(find.text('Amount*'), findsOneWidget);
      // TODO: Fix when you get a solution
      // https://stackoverflow.com/questions/67191640/flutter-widget-test-finds-hint-text-in-textformfield-without-the-field-getting-f
      // expect(find.text('Enter the amount...'), findsNothing);
    });

    testWidgets('Testing form field label and hints', (tester) async {
      await tester.pumpWidget(createAddTransactionPage());

      expect(find.byType(TextFormField), findsNWidgets(5));

      expect(find.text('Amount*'), findsOneWidget);
      expect(find.text('Enter the amount...'), findsOneWidget);

      expect(find.text('Title'), findsOneWidget);
      expect(find.text('Enter a title...'), findsOneWidget);

      expect(find.text('Description'), findsOneWidget);
      expect(find.text('Enter the description...'), findsOneWidget);

      expect(find.text('Category'), findsOneWidget);
      expect(find.text('Select the category...'), findsOneWidget);
      expect(find.text('Default'), findsOneWidget);

      expect(find.text('Transaction date'), findsOneWidget);
      expect(find.text('Select the my_transaction date...'), findsOneWidget);
    });

    testWidgets('Testing amount form field error ', (tester) async {
      await tester.pumpWidget(createAddTransactionPage());
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();

      expect(find.text('Amount required'), findsOneWidget);
    });

    testWidgets('Testing adding transactions', (tester) async {
      await tester.pumpWidget(createAddTransactionPage());
      expect(transactions.value.length, 0);
      expect(find.byType(TextFormField), findsNWidgets(5));

      await tester.enterText(find.byType(TextFormField).at(0), '45.50');
      await tester.enterText(find.byType(TextFormField).at(1), 'Breakfast');
      await tester.enterText(find.byType(TextFormField).at(2), 'Sandwich');
      await tester.tap(find.byType(FloatingActionButton));

      expect(transactions.value.length, 1);
      expect(transactions.value.first.amount.value, 45.50);
      expect(transactions.value.first.title, 'Breakfast');
      expect(transactions.value.first.description, 'Sandwich');

      expect(find.byType(TextFormField), findsNWidgets(5));
      // await tester.pumpWidget(createAddTransactionPage());
      // await tester.tap(find.byType(TextFormField).first);
      // await tester.showKeyboard(find.byType(TextFormField).at(0));
      // await tester.enterText(find.byType(TextFormField).at(0), '101.75');
      // await tester.enterText(find.byType(TextFormField).at(1), 'Lunch');
      // await tester.enterText(find.byType(TextFormField).at(2), 'Salad');
      // await tester.tap(find.byType(FloatingActionButton));

      // expect(transactions.value.length, 2);
      // expect(transactions.value.first.amount.value, 45.50);
      // expect(transactions.value.first.title, 'Breakfast');
      // expect(transactions.value.first.description, 'Sandwich');
      // expect(transactions.value.elementAt(1).amount.value, 101.75);
      // expect(transactions.value.elementAt(1).title, 'Lunch');
      // expect(transactions.value.elementAt(1).description, 'Salad');
    });

    // TODO: Add test to verify amount is required field
    // TODO: Add test to check amount format is correct
    // TODO: Add test to verify Title is within character limit
    // TODO: Add test to verify Description is within character limit
    // TODO: Add test to add my_transaction with category selection
    // TODO: Add test to add my_transaction with my_transaction date selection
  });
}
