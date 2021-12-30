import 'package:financemanager/src/models/amount/amount.dart';
import 'package:financemanager/src/models/timestamp/timestamp.dart';
import 'package:financemanager/src/models/transaction/my_transaction.dart';
import 'package:financemanager/src/models/transactions.dart';
import 'package:test/test.dart';

void main() {
  group('Transactions Unit Tests : ', () {
    MyTransaction _transaction1 = MyTransaction(
      amount: Amount(
        value: 50.00,
        id: 0,
      ),
      id: 0,
      creationTimestamp: Timestamp(
        date: '04-10-2021',
        time: '22:42:00',
      ),
      transactionTimestamp: Timestamp(
        date: '06-10-2021',
        time: '22:42:00',
      ),
    );
    MyTransaction _transaction2 = MyTransaction(
      amount: Amount(
        value: 200.00,
        id: 0,
      ),
      id: 0,
      creationTimestamp: Timestamp(
        date: '04-10-2021',
        time: '22:42:00',
      ),
      transactionTimestamp: Timestamp(
        date: '06-10-2021',
        time: '22:42:00',
      ),
    );

    test('A new transactions should be created', () {
      Transactions transactions = Transactions();
      expect(transactions.value, []);
    });

    test('Adding transactions', () {
      Transactions transactions = Transactions();
      transactions.add(_transaction1);
      expect(transactions.value, [_transaction1]);

      transactions.add(_transaction2);
      expect(transactions.value, [_transaction1, _transaction2]);
    });

    test('Removing a my_transaction', () {
      Transactions transactions = Transactions();
      transactions.add(_transaction1);
      expect(transactions.value, [_transaction1]);

      transactions.add(_transaction2);
      expect(transactions.value, [_transaction1, _transaction2]);

      bool result1 = transactions.remove(0);
      expect(result1, true);
      expect(transactions.value, [_transaction2]);

      bool result2 = transactions.remove(0);
      expect(result2, true);
      expect(transactions.value, []);
    });

    test('Removing an invalid my_transaction', () {
      Transactions transactions = Transactions();
      transactions.add(_transaction1);
      expect(transactions.value, [_transaction1]);

      bool result = transactions.remove(1);
      expect(result, false);
      expect(transactions.value, [_transaction1]);
    });
  });
}
