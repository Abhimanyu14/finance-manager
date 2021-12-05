import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

enum Unit { INR, USD }

class Amount {
  Amount({
    value = 0.0,
    id = 0,
    unit = Unit.INR,
  }) {
    assert(value >= 0.0);
    assert(id >= 0);
    this._value = value;
    this._id = id;
    this._unit = unit;
  }

  double _value;
  int _id;
  Unit _unit;

  double get value => _value;

  int get id => _id;

  Unit get unit => _unit;

  bool updateValue({
    double updatedValue,
  }) {
    if (updatedValue < 0) {
      return false;
    }
    _value = updatedValue;
    return true;
  }

  bool updateUnit({
    Unit updatedUnit,
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
    return '${objectRuntimeType(this, 'Amount')}($_value, $_id, $_unit)';
  }
}
