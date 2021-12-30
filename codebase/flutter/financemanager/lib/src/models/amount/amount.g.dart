// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'amount.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_Amount _$$_AmountFromJson(Map<String, dynamic> json) => _$_Amount(
      value: (json['value'] as num).toDouble(),
      id: json['id'] as int,
      unit: $enumDecodeNullable(_$UnitEnumMap, json['unit']) ?? Unit.inr,
    );

Map<String, dynamic> _$$_AmountToJson(_$_Amount instance) => <String, dynamic>{
      'value': instance.value,
      'id': instance.id,
      'unit': _$UnitEnumMap[instance.unit],
    };

const _$UnitEnumMap = {
  Unit.inr: 'inr',
  Unit.usd: 'usd',
};
