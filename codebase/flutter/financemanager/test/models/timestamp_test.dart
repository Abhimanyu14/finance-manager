import 'package:financemanager/src/models/timestamp.dart';
import 'package:test/test.dart';

void main() {
  group('Timestamp Unit Tests : ', () {
    test(
        'A new timestamp object is created with given values and default values',
        () {
      String date = '20-05-2021';
      String time = '15:45:30';
      Timestamp timestamp = Timestamp(
        date: date,
        time: time,
      );
      expect(timestamp.date, date);
      expect(timestamp.time, time);
    });

    test('A new timestamp object is created using DateTime object', () {
      DateTime dateTime = DateTime.utc(
        2021,
        5,
        31,
        15,
        0,
        8,
      );
      String date = '31-05-2021';
      String time = '15:00:08';
      Timestamp timestamp = Timestamp.fromDateTime(
        dateTime: dateTime,
      );
      expect(timestamp.date, date);
      expect(timestamp.time, time);
    });
  });
}
