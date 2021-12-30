import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/models/unit/unit.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'amount.freezed.dart';
part 'amount.g.dart';

@freezed
class Amount with _$Amount {
  const Amount._();
  const factory Amount({
    required double value,
    required int id,
    @Default(Unit.inr) Unit unit,
  }) = _Amount;

  factory Amount.fromJson(Map<String, dynamic> json) => _$AmountFromJson(json);

  String showAmount() {
    return '$symbolInr $value';
  }
}

/*
class Amount {
  Amount({
    value = 0.0,
    id = 0,
    unit = Unit.inr,
  }) {
    assert(value >= 0.0);
    assert(id >= 0);
    _value = value;
    _id = id;
    _unit = unit;
  }

  double _value = 0.0;
  int _id = 0;
  Unit _unit = Unit.inr;

  double get value => _value;

  int get id => _id;

  Unit get unit => _unit;

  bool updateValue({
    required double updatedValue,
  }) {
    if (updatedValue < 0) {
      return false;
    }
    _value = updatedValue;
    return true;
  }

  bool updateUnit({
    required Unit updatedUnit,
  }) {
    _unit = updatedUnit;
    return true;
  }

  @override
  bool operator ==(other) {
    if (other.runtimeType != runtimeType) {
      return false;
    }
    return other is Amount &&
        other.value == _value &&
        other.id == _id &&
        other.unit == _unit;
  }

  @override
  int get hashCode {
    return hashValues(
      _value,
      _id,
      _unit,
    );
  }

  @override
  String toString() {
    return '${objectRuntimeType(this, 'Amount')}'
        '('
        '$_value, '
        '$_id, '
        '$_unit'
        ')';
  }

  Amount.fromJson(Map<String, dynamic> json)
      : _id = json['id'],
        _value = json['value'],
        _unit = json['unit'];

  Map<String, dynamic> toJson() => {
        'id': _id,
        'value': _value,
        'unit': _unit,
      };
}
*/
