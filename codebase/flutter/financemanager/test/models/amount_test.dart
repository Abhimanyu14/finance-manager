import 'package:financemanager/src/models/amount.dart';
import 'package:test/test.dart';

void main() {
  group('Amount Unit Tests : ', () {
    test('A new amount object is created with given values and default values', () {
      double value = 45.00;
      int id = 0;
      Unit defaultUnit = Unit.INR;
      Amount amount = Amount(
        value: value,
        id: id,
      );

      expect(amount.value, value);
      expect(amount.id, id);
      expect(amount.unit, defaultUnit);
    });

    test('A new amount object is created with given values', () {
      double value = 45.00;
      int id = 0;
      Unit unit = Unit.USD;
      Amount amount = Amount(
        value: value,
        id: id,
        unit: unit,
      );

      expect(amount.value, value);
      expect(amount.id, id);
      expect(amount.unit, unit);
    });

    test('Update amount value', () {
      double value = 45.00;
      int id = 0;
      Amount amount = Amount(
        value: value,
        id: id,
      );
      expect(amount.value, value);

      double updatedValue = 55.00;
      bool result = amount.updateValue(updatedValue: updatedValue);
      expect(result, true);
      expect(amount.value, updatedValue);
    });

    test('Update amount with an invalid value', () {
      double value = 45.00;
      int id = 0;
      Amount amount = Amount(
        value: value,
        id: id,
      );
      expect(amount.value, value);

      double updatedValue = -55.00;
      bool result = amount.updateValue(updatedValue: updatedValue);
      expect(result, false);
    });

    test('Update amount unit', () {
      double value = 45.00;
      int id = 0;
      Unit defaultUnit = Unit.INR;
      Amount amount = Amount(
        value: value,
        id: id,
      );
      expect(amount.unit, defaultUnit);

      Unit updatedUnit = Unit.USD;
      bool result = amount.updateUnit(updatedUnit: updatedUnit);
      expect(result, true);
      expect(amount.unit, updatedUnit);
    });
  });
}
