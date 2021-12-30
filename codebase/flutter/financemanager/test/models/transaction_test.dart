import 'package:financemanager/src/models/amount/amount.dart';
import 'package:financemanager/src/models/category/category.dart';
import 'package:financemanager/src/models/timestamp/timestamp.dart';
import 'package:financemanager/src/models/transaction/my_transaction.dart';
import 'package:test/test.dart';

void main() {
  group('Transaction Unit Tests : ', () {
    test(
        'A new my_transaction object is created with given values and default values',
        () {
      Amount amount = Amount(
        value: 45.0,
        id: 0,
      );
      Category defaultCategory = Category(
        id: 0,
        description: 'The default category',
        title: 'Default',
      );
      int id = 0;
      Timestamp creationTimestamp = Timestamp(
        date: '11-04-2021',
        time: '06:54:00',
      );
      Timestamp transactionTimestamp = Timestamp(
        date: '10-04-2021',
        time: '06:54:00',
      );
      MyTransaction transaction = MyTransaction(
        amount: amount,
        id: 0,
        creationTimestamp: creationTimestamp,
        transactionTimestamp: transactionTimestamp,
      );
      expect(transaction.amount, amount);
      expect(transaction.category, defaultCategory);
      expect(transaction.id, id);
      expect(transaction.description, null);
      expect(transaction.title, null);
      expect(transaction.creationTimestamp, creationTimestamp);
      expect(transaction.transactionTimestamp, transactionTimestamp);
    });

    test('A new my_transaction object is created with given values', () {
      Amount amount = Amount(
        value: 45.0,
        id: 0,
      );
      Category category = Category(
        id: 1,
        description: 'Food expenses',
        title: 'Food',
      );
      int id = 0;
      String description = 'ABC Restaurant';
      String title = 'Breakfast';
      Timestamp creationTimestamp = Timestamp(
        date: '11-04-2021',
        time: '06:54:00',
      );
      Timestamp transactionTimestamp = Timestamp(
        date: '10-04-2021',
        time: '06:54:00',
      );
      MyTransaction transaction = MyTransaction(
        amount: amount,
        category: category,
        id: id,
        description: description,
        title: title,
        creationTimestamp: creationTimestamp,
        transactionTimestamp: transactionTimestamp,
      );
      expect(transaction.amount, amount);
      // ignore: todo
      // TODO: fix this test
      // expect(my_transaction.category, category);
      expect(transaction.id, id);
      expect(transaction.description, description);
      expect(transaction.title, title);
      expect(transaction.creationTimestamp, creationTimestamp);
      expect(transaction.transactionTimestamp, transactionTimestamp);
    });
  });
}
