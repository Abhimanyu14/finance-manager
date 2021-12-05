import 'package:financemanager/src/constants/category_list.dart';
import 'package:financemanager/src/models/amount.dart';
import 'package:financemanager/src/models/category.dart';
import 'package:financemanager/src/models/timestamp.dart';
import 'package:flutter/foundation.dart' hide Category;
import 'package:flutter/material.dart';

class Transaction {
  Transaction({
    amount,
    category,
    id,
    description,
    title,
    creationTimestamp,
    transactionTimestamp,
  }) {
    this._amount = amount ?? defaultAmount;
    this._category = category ?? defaultCategory;
    this._id = id ?? 0;
    this._description = description;
    this._title = title;
    this._creationTimestamp = creationTimestamp ??
        Timestamp.fromDateTime(
          dateTime: DateTime.now(),
        );
    this._transactionTimestamp = transactionTimestamp ??
        Timestamp.fromDateTime(
          dateTime: DateTime.now(),
        );
  }

  Amount _amount;
  Category _category;
  int _id;
  String _description;
  String _title;
  Timestamp _creationTimestamp;
  Timestamp _transactionTimestamp;

  Amount get amount => _amount;

  Category get category => _category;

  int get id => _id;

  String get description => _description;

  String get title => _title;

  Timestamp get creationTimestamp => _creationTimestamp;

  Timestamp get transactionTimestamp => _transactionTimestamp;

  bool updateAmountValue(double updatedAmountValue) {
    return _amount.updateValue(
      updatedValue: updatedAmountValue,
    );
  }

  bool updateAmountUnit(Unit updatedAmountUnit) {
    return _amount.updateUnit(
      updatedUnit: updatedAmountUnit,
    );
  }

  bool setCategory(Category newCategory) {
    _category = newCategory;
    return true;
  }

  bool setDescription(String newDescription) {
    _description = newDescription;
    return true;
  }

  bool setTitle(String newTitle) {
    _title = newTitle;
    return true;
  }

  bool setTransactionTimestamp(Timestamp newTransactionTimestamp) {
    _transactionTimestamp = newTransactionTimestamp;
    return true;
  }

  @override
  bool operator ==(other) {
    if (other.runtimeType != runtimeType) {
      return false;
    }
    return other is Transaction &&
        other.amount == _amount &&
        other.category == _category &&
        other.id == _id &&
        other.description == _description &&
        other.title == _title &&
        other.creationTimestamp == _creationTimestamp &&
        other.transactionTimestamp == _transactionTimestamp;
  }

  @override
  int get hashCode {
    return hashValues(
      _amount,
      _category,
      _id,
      _description,
      _title,
      _creationTimestamp,
      _transactionTimestamp,
    );
  }

  @override
  String toString() {
    return '${objectRuntimeType(this, 'Transaction')}($_amount, $_category, $_id,$_description, $_title, $_creationTimestamp, $_transactionTimestamp)';
  }
}
