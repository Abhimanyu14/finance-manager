import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/models/amount/amount.dart';
import 'package:financemanager/src/models/category/category.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'my_transaction.freezed.dart';
part 'my_transaction.g.dart';

@freezed
class MyTransaction with _$MyTransaction {
  const MyTransaction._();
  const factory MyTransaction({
    @Default(
      Amount(
        value: 0.0,
        id: 0,
      ),
    )
        Amount amount,
    @Default(
      Category(
        id: 0,
        description: defaultCategoryDescription,
        title: defaultCategoryTitle,
      ),
    )
        Category category,
    @Default(0)
        int id,
    @Default('')
        String description,
    @Default('')
        String title,
    // required Timestamp creationTimestamp,
    // required Timestamp transactionTimestamp,
  }) = _MyTransaction;

  factory MyTransaction.fromJson(Map<String, dynamic> json) =>
      _$MyTransactionFromJson(json);
}

/*
class MyTransaction {
  MyTransaction({
    amount,
    category,
    id,
    description,
    title,
    creationTimestamp,
    transactionTimestamp,
  }) {
    _amount = amount ?? defaultAmount;
    _category = category ?? defaultCategory;
    _id = id ?? 0;
    _description = description;
    _title = title;
    _creationTimestamp = creationTimestamp ??
        Timestamp.fromDateTime(
          dateTime: DateTime.now(),
        );
    _transactionTimestamp = transactionTimestamp ??
        Timestamp.fromDateTime(
          dateTime: DateTime.now(),
        );
  }

  Amount _amount = defaultAmount;
  Category _category = defaultCategory;
  int _id = 0;
  String _description = '';
  String _title = '';
  Timestamp _creationTimestamp = defaultTimestamp;
  Timestamp _transactionTimestamp = defaultTimestamp;

  Amount get amount => _amount;

  Category get category => _category;

  int get id => _id;

  String get description => _description;

  String get title => _title;

  Timestamp get creationTimestamp => _creationTimestamp;

  Timestamp get transactionTimestamp => _transactionTimestamp;

  bool updateAmountValue(double updatedAmountValue) {
    _amount = _amount.copyWith(
      value: updatedAmountValue,
    );
    return true;
  }

  bool updateAmountUnit(Unit updatedAmountUnit) {
    _amount = _amount.copyWith(
      unit: updatedAmountUnit,
    );
    return true;
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
    return other is MyTransaction &&
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
    return '${objectRuntimeType(this, 'Transaction')}'
        '('
        '$_amount, '
        '$_category, '
        '$_id, '
        '$_description, '
        '$_title, '
        '$_creationTimestamp, '
        '$_transactionTimestamp'
        ')';
  }

  // Convert a my_transaction into a Map.
  // The keys must correspond to the names of the columns in the database.
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
    };
  }
}
*/
