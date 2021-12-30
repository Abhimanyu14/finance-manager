// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'my_transaction.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_MyTransaction _$$_MyTransactionFromJson(Map<String, dynamic> json) =>
    _$_MyTransaction(
      amount: json['amount'] == null
          ? const Amount(value: 0.0, id: 0)
          : Amount.fromJson(json['amount'] as Map<String, dynamic>),
      category: json['category'] == null
          ? const Category(
              id: 0,
              description: defaultCategoryDescription,
              title: defaultCategoryTitle)
          : Category.fromJson(json['category'] as Map<String, dynamic>),
      id: json['id'] as int? ?? 0,
      description: json['description'] as String? ?? '',
      title: json['title'] as String? ?? '',
    );

Map<String, dynamic> _$$_MyTransactionToJson(_$_MyTransaction instance) =>
    <String, dynamic>{
      'amount': instance.amount,
      'category': instance.category,
      'id': instance.id,
      'description': instance.description,
      'title': instance.title,
    };
