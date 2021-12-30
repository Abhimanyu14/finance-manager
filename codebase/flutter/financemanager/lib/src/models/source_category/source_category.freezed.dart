// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'source_category.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more informations: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
class _$SourceCategoryTearOff {
  const _$SourceCategoryTearOff();

  DefaultSourceCategory call() {
    return const DefaultSourceCategory();
  }

  Cash cash() {
    return const Cash();
  }

  Bank bank(String name) {
    return Bank(
      name,
    );
  }

  EWallet eWallet(String name) {
    return EWallet(
      name,
    );
  }
}

/// @nodoc
const $SourceCategory = _$SourceCategoryTearOff();

/// @nodoc
mixin _$SourceCategory {
  @optionalTypeArgs
  TResult when<TResult extends Object?>(
    TResult Function() $default, {
    required TResult Function() cash,
    required TResult Function(String name) bank,
    required TResult Function(String name) eWallet,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>(
    TResult Function()? $default, {
    TResult Function()? cash,
    TResult Function(String name)? bank,
    TResult Function(String name)? eWallet,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>(
    TResult Function()? $default, {
    TResult Function()? cash,
    TResult Function(String name)? bank,
    TResult Function(String name)? eWallet,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value) $default, {
    required TResult Function(Cash value) cash,
    required TResult Function(Bank value) bank,
    required TResult Function(EWallet value) eWallet,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value)? $default, {
    TResult Function(Cash value)? cash,
    TResult Function(Bank value)? bank,
    TResult Function(EWallet value)? eWallet,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value)? $default, {
    TResult Function(Cash value)? cash,
    TResult Function(Bank value)? bank,
    TResult Function(EWallet value)? eWallet,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $SourceCategoryCopyWith<$Res> {
  factory $SourceCategoryCopyWith(
          SourceCategory value, $Res Function(SourceCategory) then) =
      _$SourceCategoryCopyWithImpl<$Res>;
}

/// @nodoc
class _$SourceCategoryCopyWithImpl<$Res>
    implements $SourceCategoryCopyWith<$Res> {
  _$SourceCategoryCopyWithImpl(this._value, this._then);

  final SourceCategory _value;
  // ignore: unused_field
  final $Res Function(SourceCategory) _then;
}

/// @nodoc
abstract class $DefaultSourceCategoryCopyWith<$Res> {
  factory $DefaultSourceCategoryCopyWith(DefaultSourceCategory value,
          $Res Function(DefaultSourceCategory) then) =
      _$DefaultSourceCategoryCopyWithImpl<$Res>;
}

/// @nodoc
class _$DefaultSourceCategoryCopyWithImpl<$Res>
    extends _$SourceCategoryCopyWithImpl<$Res>
    implements $DefaultSourceCategoryCopyWith<$Res> {
  _$DefaultSourceCategoryCopyWithImpl(
      DefaultSourceCategory _value, $Res Function(DefaultSourceCategory) _then)
      : super(_value, (v) => _then(v as DefaultSourceCategory));

  @override
  DefaultSourceCategory get _value => super._value as DefaultSourceCategory;
}

/// @nodoc

class _$DefaultSourceCategory implements DefaultSourceCategory {
  const _$DefaultSourceCategory();

  @override
  String toString() {
    return 'SourceCategory()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is DefaultSourceCategory);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>(
    TResult Function() $default, {
    required TResult Function() cash,
    required TResult Function(String name) bank,
    required TResult Function(String name) eWallet,
  }) {
    return $default();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>(
    TResult Function()? $default, {
    TResult Function()? cash,
    TResult Function(String name)? bank,
    TResult Function(String name)? eWallet,
  }) {
    return $default?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>(
    TResult Function()? $default, {
    TResult Function()? cash,
    TResult Function(String name)? bank,
    TResult Function(String name)? eWallet,
    required TResult orElse(),
  }) {
    if ($default != null) {
      return $default();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value) $default, {
    required TResult Function(Cash value) cash,
    required TResult Function(Bank value) bank,
    required TResult Function(EWallet value) eWallet,
  }) {
    return $default(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value)? $default, {
    TResult Function(Cash value)? cash,
    TResult Function(Bank value)? bank,
    TResult Function(EWallet value)? eWallet,
  }) {
    return $default?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value)? $default, {
    TResult Function(Cash value)? cash,
    TResult Function(Bank value)? bank,
    TResult Function(EWallet value)? eWallet,
    required TResult orElse(),
  }) {
    if ($default != null) {
      return $default(this);
    }
    return orElse();
  }
}

abstract class DefaultSourceCategory implements SourceCategory {
  const factory DefaultSourceCategory() = _$DefaultSourceCategory;
}

/// @nodoc
abstract class $CashCopyWith<$Res> {
  factory $CashCopyWith(Cash value, $Res Function(Cash) then) =
      _$CashCopyWithImpl<$Res>;
}

/// @nodoc
class _$CashCopyWithImpl<$Res> extends _$SourceCategoryCopyWithImpl<$Res>
    implements $CashCopyWith<$Res> {
  _$CashCopyWithImpl(Cash _value, $Res Function(Cash) _then)
      : super(_value, (v) => _then(v as Cash));

  @override
  Cash get _value => super._value as Cash;
}

/// @nodoc

class _$Cash implements Cash {
  const _$Cash();

  @override
  String toString() {
    return 'SourceCategory.cash()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is Cash);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>(
    TResult Function() $default, {
    required TResult Function() cash,
    required TResult Function(String name) bank,
    required TResult Function(String name) eWallet,
  }) {
    return cash();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>(
    TResult Function()? $default, {
    TResult Function()? cash,
    TResult Function(String name)? bank,
    TResult Function(String name)? eWallet,
  }) {
    return cash?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>(
    TResult Function()? $default, {
    TResult Function()? cash,
    TResult Function(String name)? bank,
    TResult Function(String name)? eWallet,
    required TResult orElse(),
  }) {
    if (cash != null) {
      return cash();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value) $default, {
    required TResult Function(Cash value) cash,
    required TResult Function(Bank value) bank,
    required TResult Function(EWallet value) eWallet,
  }) {
    return cash(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value)? $default, {
    TResult Function(Cash value)? cash,
    TResult Function(Bank value)? bank,
    TResult Function(EWallet value)? eWallet,
  }) {
    return cash?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value)? $default, {
    TResult Function(Cash value)? cash,
    TResult Function(Bank value)? bank,
    TResult Function(EWallet value)? eWallet,
    required TResult orElse(),
  }) {
    if (cash != null) {
      return cash(this);
    }
    return orElse();
  }
}

abstract class Cash implements SourceCategory {
  const factory Cash() = _$Cash;
}

/// @nodoc
abstract class $BankCopyWith<$Res> {
  factory $BankCopyWith(Bank value, $Res Function(Bank) then) =
      _$BankCopyWithImpl<$Res>;
  $Res call({String name});
}

/// @nodoc
class _$BankCopyWithImpl<$Res> extends _$SourceCategoryCopyWithImpl<$Res>
    implements $BankCopyWith<$Res> {
  _$BankCopyWithImpl(Bank _value, $Res Function(Bank) _then)
      : super(_value, (v) => _then(v as Bank));

  @override
  Bank get _value => super._value as Bank;

  @override
  $Res call({
    Object? name = freezed,
  }) {
    return _then(Bank(
      name == freezed
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$Bank implements Bank {
  const _$Bank(this.name);

  @override
  final String name;

  @override
  String toString() {
    return 'SourceCategory.bank(name: $name)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is Bank &&
            const DeepCollectionEquality().equals(other.name, name));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, const DeepCollectionEquality().hash(name));

  @JsonKey(ignore: true)
  @override
  $BankCopyWith<Bank> get copyWith =>
      _$BankCopyWithImpl<Bank>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>(
    TResult Function() $default, {
    required TResult Function() cash,
    required TResult Function(String name) bank,
    required TResult Function(String name) eWallet,
  }) {
    return bank(name);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>(
    TResult Function()? $default, {
    TResult Function()? cash,
    TResult Function(String name)? bank,
    TResult Function(String name)? eWallet,
  }) {
    return bank?.call(name);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>(
    TResult Function()? $default, {
    TResult Function()? cash,
    TResult Function(String name)? bank,
    TResult Function(String name)? eWallet,
    required TResult orElse(),
  }) {
    if (bank != null) {
      return bank(name);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value) $default, {
    required TResult Function(Cash value) cash,
    required TResult Function(Bank value) bank,
    required TResult Function(EWallet value) eWallet,
  }) {
    return bank(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value)? $default, {
    TResult Function(Cash value)? cash,
    TResult Function(Bank value)? bank,
    TResult Function(EWallet value)? eWallet,
  }) {
    return bank?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value)? $default, {
    TResult Function(Cash value)? cash,
    TResult Function(Bank value)? bank,
    TResult Function(EWallet value)? eWallet,
    required TResult orElse(),
  }) {
    if (bank != null) {
      return bank(this);
    }
    return orElse();
  }
}

abstract class Bank implements SourceCategory {
  const factory Bank(String name) = _$Bank;

  String get name;
  @JsonKey(ignore: true)
  $BankCopyWith<Bank> get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $EWalletCopyWith<$Res> {
  factory $EWalletCopyWith(EWallet value, $Res Function(EWallet) then) =
      _$EWalletCopyWithImpl<$Res>;
  $Res call({String name});
}

/// @nodoc
class _$EWalletCopyWithImpl<$Res> extends _$SourceCategoryCopyWithImpl<$Res>
    implements $EWalletCopyWith<$Res> {
  _$EWalletCopyWithImpl(EWallet _value, $Res Function(EWallet) _then)
      : super(_value, (v) => _then(v as EWallet));

  @override
  EWallet get _value => super._value as EWallet;

  @override
  $Res call({
    Object? name = freezed,
  }) {
    return _then(EWallet(
      name == freezed
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$EWallet implements EWallet {
  const _$EWallet(this.name);

  @override
  final String name;

  @override
  String toString() {
    return 'SourceCategory.eWallet(name: $name)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is EWallet &&
            const DeepCollectionEquality().equals(other.name, name));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, const DeepCollectionEquality().hash(name));

  @JsonKey(ignore: true)
  @override
  $EWalletCopyWith<EWallet> get copyWith =>
      _$EWalletCopyWithImpl<EWallet>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>(
    TResult Function() $default, {
    required TResult Function() cash,
    required TResult Function(String name) bank,
    required TResult Function(String name) eWallet,
  }) {
    return eWallet(name);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>(
    TResult Function()? $default, {
    TResult Function()? cash,
    TResult Function(String name)? bank,
    TResult Function(String name)? eWallet,
  }) {
    return eWallet?.call(name);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>(
    TResult Function()? $default, {
    TResult Function()? cash,
    TResult Function(String name)? bank,
    TResult Function(String name)? eWallet,
    required TResult orElse(),
  }) {
    if (eWallet != null) {
      return eWallet(name);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value) $default, {
    required TResult Function(Cash value) cash,
    required TResult Function(Bank value) bank,
    required TResult Function(EWallet value) eWallet,
  }) {
    return eWallet(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value)? $default, {
    TResult Function(Cash value)? cash,
    TResult Function(Bank value)? bank,
    TResult Function(EWallet value)? eWallet,
  }) {
    return eWallet?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>(
    TResult Function(DefaultSourceCategory value)? $default, {
    TResult Function(Cash value)? cash,
    TResult Function(Bank value)? bank,
    TResult Function(EWallet value)? eWallet,
    required TResult orElse(),
  }) {
    if (eWallet != null) {
      return eWallet(this);
    }
    return orElse();
  }
}

abstract class EWallet implements SourceCategory {
  const factory EWallet(String name) = _$EWallet;

  String get name;
  @JsonKey(ignore: true)
  $EWalletCopyWith<EWallet> get copyWith => throw _privateConstructorUsedError;
}
