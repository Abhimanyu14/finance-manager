import 'package:financemanager/src/models/transaction.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

/// The [Transactions] class holds a list of transactions saved by the user.
class Transactions extends ChangeNotifier {
  final List<Transaction> _transactions = [];

  List<Transaction> get value => _transactions;

  bool add(Transaction transaction) {
    _transactions.add(transaction);
    notifyListeners();
    return true;
  }

  bool remove(int transactionId) {
    int transactionIndex = _transactions.indexWhere(
      (transaction) => transaction.id == transactionId,
    );
    if (transactionIndex == -1) {
      return false;
    }
    _transactions.removeAt(transactionIndex);
    notifyListeners();
    return true;
  }

  @override
  String toString() {
    return '${objectRuntimeType(this, 'Transactions')}($_transactions)';
  }
}
