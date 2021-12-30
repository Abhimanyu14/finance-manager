import 'package:freezed_annotation/freezed_annotation.dart';

part 'source_category.freezed.dart';
part 'source_category.g.dart';

@freezed
class SourceCategory with _$SourceCategory {
  const factory SourceCategory() = DefaultSourceCategory;
  const factory SourceCategory.cash() = Cash;
  const factory SourceCategory.bank(String name) = Bank;
  const factory SourceCategory.eWallet(String name) = EWallet;
}

// enum SourceCategory { cash, bank, eWallet }
