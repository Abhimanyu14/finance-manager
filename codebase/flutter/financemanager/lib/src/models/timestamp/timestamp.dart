/*
part 'timestamp.freezed.dart';
part 'timestamp.g.dart';

// TODO: To add icon for each source
@freezed
class Timestamp with _$Timestamp {
  const Timestamp._();
  @Assert('RegExp(r"^\\d{2}-\\d{2}-\\d{4}\$").hasMatch(date)')
  const factory Timestamp({
    required String date,
    required String time,
  }) = _Timestamp;
  const factory Timestamp({
    required DateTime dateTime,
  }) {
    _date = '${dateTime.day.toString().padLeft(2, '0')}'
        '-'
        '${dateTime.month.toString().padLeft(2, '0')}'
        '-'
        '${dateTime.year.toString().padLeft(2, '4')}';
    _time = '${dateTime.hour.toString().padLeft(2, '0')}'
        ':'
        '${dateTime.minute.toString().padLeft(2, '0')}'
        ':'
        '${dateTime.second.toString().padLeft(2, '0')}';
  }


  factory Timestamp.fromJson(Map<String, dynamic> json) => _$TimestampFromJson(json);
}
*/

/*
class Timestamp {
  const Timestamp({
    required date,
    required time,
  }) {
    RegExp dateRegExp = RegExp(
      r'^\d{2}-\d{2}-\d{4}$',
    );
    assert(dateRegExp.hasMatch(date));
    RegExp timeRegExp = RegExp(
      r'^\d{2}:\d{2}:\d{2}$',
    );
    assert(timeRegExp.hasMatch(time));
    _date = date;
    _time = time;
  }

  Timestamp.fromDateTime({
    required DateTime dateTime,
  }) {
    _date = '${dateTime.day.toString().padLeft(2, '0')}'
        '-'
        '${dateTime.month.toString().padLeft(2, '0')}'
        '-'
        '${dateTime.year.toString().padLeft(2, '4')}';
    _time = '${dateTime.hour.toString().padLeft(2, '0')}'
        ':'
        '${dateTime.minute.toString().padLeft(2, '0')}'
        ':'
        '${dateTime.second.toString().padLeft(2, '0')}';
  }

  /// Format - 'dd-MM-yyyy'
  final String _date = '';

  /// Format - 'HH:mm:ss' (24 hour time)
  final String _time = '';

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
    return '${objectRuntimeType(this, 'Timestamp')}'
        '('
        '$_date, '
        '$_time'
        ')';
  }
}
*/
