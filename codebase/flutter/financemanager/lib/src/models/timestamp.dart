import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class Timestamp {
  Timestamp({
    @required date,
    @required time,
  }) {
    RegExp dateRegExp = new RegExp(
      r"^\d{2}-\d{2}-\d{4}$",
    );
    assert(dateRegExp.hasMatch(date));
    RegExp timeRegExp = new RegExp(
      r"^\d{2}:\d{2}:\d{2}$",
    );
    assert(dateRegExp.hasMatch(date));
    assert(timeRegExp.hasMatch(time));
    this._date = date;
    this._time = time;
  }

  Timestamp.fromDateTime({DateTime dateTime}) {
    this._date =
        '${dateTime.day.toString().padLeft(2, '0')}-${dateTime.month.toString().padLeft(2, '0')}-${dateTime.year.toString().padLeft(2, '4')}';
    this._time =
        '${dateTime.hour.toString().padLeft(2, '0')}:${dateTime.minute.toString().padLeft(2, '0')}:${dateTime.second.toString().padLeft(2, '0')}';
  }

  /// Format - 'dd-MM-yyyy'
  String _date;

  /// Format - 'HH:mm:ss' (24 hour time)
  String _time;

  String get date => _date;

  String get time => _time;

  DateTime toDateTime() {
    List<String> dateSubStrings = _date.split('-');
    List<String> timeSubStrings = _time.split(':');
    return DateTime.utc(
      int.parse(dateSubStrings[2]),
      int.parse(dateSubStrings[1]),
      int.parse(dateSubStrings[0]),
      int.parse(timeSubStrings[0]),
      int.parse(timeSubStrings[1]),
      int.parse(timeSubStrings[2]),
    );
  }

  @override
  bool operator ==(Object other) {
    if (other.runtimeType != runtimeType) {
      return false;
    }
    return other is Timestamp && other.date == _date && other.time == _time;
  }

  @override
  int get hashCode {
    return hashValues(
      _date,
      _time,
    );
  }

  @override
  String toString() {
    return '${objectRuntimeType(this, 'Timestamp')}($_date, $_time)';
  }
}
