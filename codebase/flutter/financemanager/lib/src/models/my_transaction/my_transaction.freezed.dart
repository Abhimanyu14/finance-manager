// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'my_transaction.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more informations: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

MyTransaction _$MyTransactionFromJson(Map<String, dynamic> json) {
  return _MyTransaction.fromJson(json);
}

/// @nodoc
class _$MyTransactionTearOff {
  const _$MyTransactionTearOff();

  _MyTransaction call(
      {Amount amount = const Amount(value: 0.0, id: 0),
      Category category = const Category(
          id: 0,
          description: defaultCategoryDescription,
          title: defaultCategoryTitle),
      int id = 0,
      String description = '',
      String title = ''}) {
    return _MyTransaction(
      amount: amount,
      category: category,
      id: id,
      description: description,
      title: title,
    );
  }

  MyTransaction fromJson(Map<String, Object?> json) {
    return MyTransaction.fromJson(json);
  }
}

/// @nodoc
const $MyTransaction = _$MyTransactionTearOff();

/// @nodoc
mixin _$MyTransaction {
  Amount get amount => throw _privateConstructorUsedError;
  Category get category => throw _privateConstructorUsedError;
  int get id => throw _privateConstructorUsedError;
  String get description => throw _privateConstructorUsedError;
  String get title => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MyTransactionCopyWith<MyTransaction> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MyTransactionCopyWith<$Res> {
  factory $MyTransactionCopyWith(
          MyTransaction value, $Res Function(MyTransaction) then) =
      _$MyTransactionCopyWithImpl<$Res>;
  $Res call(
      {Amount amount,
      Category category,
      int id,
      String description,
      String title});

  $AmountCopyWith<$Res> get amount;
  $CategoryCopyWith<$Res> get category;
}

/// @nodoc
class _$MyTransactionCopyWithImpl<$Res>
    implements $MyTransactionCopyWith<$Res> {
  _$MyTransactionCopyWithImpl(this._value, this._then);

  final MyTransaction _value;
  // ignore: unused_field
  final $Res Function(MyTransaction) _then;

  @override
  $Res call({
    Object? amount = freezed,
    Object? category = freezed,
    Object? id = freezed,
    Object? description = freezed,
    Object? title = freezed,
  }) {
    return _then(_value.copyWith(
      amount: amount == freezed
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as Amount,
      category: category == freezed
          ? _value.category
          : category // ignore: cast_nullable_to_non_nullable
              as Category,
      id: id == freezed
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int,
      description: description == freezed
          ? _value.description
          : description // ignore: cast_nullable_to_non_nullable
              as String,
      title: title == freezed
          ? _value.title
          : title // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }

  @override
  $AmountCopyWith<$Res> get amount {
    return $AmountCopyWith<$Res>(_value.amount, (value) {
      return _then(_value.copyWith(amount: value));
    });
  }

  @override
  $CategoryCopyWith<$Res> get category {
    return $CategoryCopyWith<$Res>(_value.category, (value) {
      return _then(_value.copyWith(category: value));
    });
  }
}

/// @nodoc
abstract class _$MyTransactionCopyWith<$Res>
    implements $MyTransactionCopyWith<$Res> {
  factory _$MyTransactionCopyWith(
          _MyTransaction value, $Res Function(_MyTransaction) then) =
      __$MyTransactionCopyWithImpl<$Res>;
  @override
  $Res call(
      {Amount amount,
      Category category,
      int id,
      String description,
      String title});

  @override
  $AmountCopyWith<$Res> get amount;
  @override
  $CategoryCopyWith<$Res> get category;
}

/// @nodoc
class __$MyTransactionCopyWithImpl<$Res>
    extends _$MyTransactionCopyWithImpl<$Res>
    implements _$MyTransactionCopyWith<$Res> {
  __$MyTransactionCopyWithImpl(
      _MyTransaction _value, $Res Function(_MyTransaction) _then)
      : super(_value, (v) => _then(v as _MyTransaction));

  @override
  _MyTransaction get _value => super._value as _MyTransaction;

  @override
  $Res call({
    Object? amount = freezed,
    Object? category = freezed,
    Object? id = freezed,
    Object? description = freezed,
    Object? title = freezed,
  }) {
    return _then(_MyTransaction(
      amount: amount == freezed
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as Amount,
      category: category == freezed
          ? _value.category
          : category // ignore: cast_nullable_to_non_nullable
              as Category,
      id: id == freezed
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int,
      description: description == freezed
          ? _value.description
          : description // ignore: cast_nullable_to_non_nullable
              as String,
      title: title == freezed
          ? _value.title
          : title // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_MyTransaction extends _MyTransaction {
  const _$_MyTransaction(
      {this.amount = const Amount(value: 0.0, id: 0),
      this.category = const Category(
          id: 0,
          description: defaultCategoryDescription,
          title: defaultCategoryTitle),
      this.id = 0,
      this.description = '',
      this.title = ''})
      : super._();

  factory _$_MyTransaction.fromJson(Map<String, dynamic> json) =>
      _$$_MyTransactionFromJson(json);

  @JsonKey()
  @override
  final Amount amount;
  @JsonKey()
  @override
  final Category category;
  @JsonKey()
  @override
  final int id;
  @JsonKey()
  @override
  final String description;
  @JsonKey()
  @override
  final String title;

  @override
  String toString() {
    return 'MyTransaction(amount: $amount, category: $category, id: $id, description: $description, title: $title)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _MyTransaction &&
            const DeepCollectionEquality().equals(other.amount, amount) &&
            const DeepCollectionEquality().equals(other.category, category) &&
            const DeepCollectionEquality().equals(other.id, id) &&
            const DeepCollectionEquality()
                .equals(other.description, description) &&
            const DeepCollectionEquality().equals(other.title, title));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(amount),
      const DeepCollectionEquality().hash(category),
      const DeepCollectionEquality().hash(id),
      const DeepCollectionEquality().hash(description),
      const DeepCollectionEquality().hash(title));

  @JsonKey(ignore: true)
  @override
  _$MyTransactionCopyWith<_MyTransaction> get copyWith =>
      __$MyTransactionCopyWithImpl<_MyTransaction>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_MyTransactionToJson(this);
  }
}

abstract class _MyTransaction extends MyTransaction {
  const factory _MyTransaction(
      {Amount amount,
      Category category,
      int id,
      String description,
      String title}) = _$_MyTransaction;
  const _MyTransaction._() : super._();

  factory _MyTransaction.fromJson(Map<String, dynamic> json) =
      _$_MyTransaction.fromJson;

  @override
  Amount get amount;
  @override
  Category get category;
  @override
  int get id;
  @override
  String get description;
  @override
  String get title;
  @override
  @JsonKey(ignore: true)
  _$MyTransactionCopyWith<_MyTransaction> get copyWith =>
      throw _privateConstructorUsedError;
}
