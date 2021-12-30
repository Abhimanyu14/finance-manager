// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'amount.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more informations: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

Amount _$AmountFromJson(Map<String, dynamic> json) {
  return _Amount.fromJson(json);
}

/// @nodoc
class _$AmountTearOff {
  const _$AmountTearOff();

  _Amount call({required double value, required int id, Unit unit = Unit.inr}) {
    return _Amount(
      value: value,
      id: id,
      unit: unit,
    );
  }

  Amount fromJson(Map<String, Object?> json) {
    return Amount.fromJson(json);
  }
}

/// @nodoc
const $Amount = _$AmountTearOff();

/// @nodoc
mixin _$Amount {
  double get value => throw _privateConstructorUsedError;
  int get id => throw _privateConstructorUsedError;
  Unit get unit => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AmountCopyWith<Amount> get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AmountCopyWith<$Res> {
  factory $AmountCopyWith(Amount value, $Res Function(Amount) then) =
      _$AmountCopyWithImpl<$Res>;
  $Res call({double value, int id, Unit unit});
}

/// @nodoc
class _$AmountCopyWithImpl<$Res> implements $AmountCopyWith<$Res> {
  _$AmountCopyWithImpl(this._value, this._then);

  final Amount _value;
  // ignore: unused_field
  final $Res Function(Amount) _then;

  @override
  $Res call({
    Object? value = freezed,
    Object? id = freezed,
    Object? unit = freezed,
  }) {
    return _then(_value.copyWith(
      value: value == freezed
          ? _value.value
          : value // ignore: cast_nullable_to_non_nullable
              as double,
      id: id == freezed
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int,
      unit: unit == freezed
          ? _value.unit
          : unit // ignore: cast_nullable_to_non_nullable
              as Unit,
    ));
  }
}

/// @nodoc
abstract class _$AmountCopyWith<$Res> implements $AmountCopyWith<$Res> {
  factory _$AmountCopyWith(_Amount value, $Res Function(_Amount) then) =
      __$AmountCopyWithImpl<$Res>;
  @override
  $Res call({double value, int id, Unit unit});
}

/// @nodoc
class __$AmountCopyWithImpl<$Res> extends _$AmountCopyWithImpl<$Res>
    implements _$AmountCopyWith<$Res> {
  __$AmountCopyWithImpl(_Amount _value, $Res Function(_Amount) _then)
      : super(_value, (v) => _then(v as _Amount));

  @override
  _Amount get _value => super._value as _Amount;

  @override
  $Res call({
    Object? value = freezed,
    Object? id = freezed,
    Object? unit = freezed,
  }) {
    return _then(_Amount(
      value: value == freezed
          ? _value.value
          : value // ignore: cast_nullable_to_non_nullable
              as double,
      id: id == freezed
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as int,
      unit: unit == freezed
          ? _value.unit
          : unit // ignore: cast_nullable_to_non_nullable
              as Unit,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_Amount extends _Amount {
  const _$_Amount({required this.value, required this.id, this.unit = Unit.inr})
      : super._();

  factory _$_Amount.fromJson(Map<String, dynamic> json) =>
      _$$_AmountFromJson(json);

  @override
  final double value;
  @override
  final int id;
  @JsonKey()
  @override
  final Unit unit;

  @override
  String toString() {
    return 'Amount(value: $value, id: $id, unit: $unit)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _Amount &&
            const DeepCollectionEquality().equals(other.value, value) &&
            const DeepCollectionEquality().equals(other.id, id) &&
            const DeepCollectionEquality().equals(other.unit, unit));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(value),
      const DeepCollectionEquality().hash(id),
      const DeepCollectionEquality().hash(unit));

  @JsonKey(ignore: true)
  @override
  _$AmountCopyWith<_Amount> get copyWith =>
      __$AmountCopyWithImpl<_Amount>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AmountToJson(this);
  }
}

abstract class _Amount extends Amount {
  const factory _Amount({required double value, required int id, Unit unit}) =
      _$_Amount;
  const _Amount._() : super._();

  factory _Amount.fromJson(Map<String, dynamic> json) = _$_Amount.fromJson;

  @override
  double get value;
  @override
  int get id;
  @override
  Unit get unit;
  @override
  @JsonKey(ignore: true)
  _$AmountCopyWith<_Amount> get copyWith => throw _privateConstructorUsedError;
}
