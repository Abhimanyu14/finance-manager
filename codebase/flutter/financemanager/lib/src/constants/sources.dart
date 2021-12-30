import 'package:financemanager/src/models/amount/amount.dart';
import 'package:financemanager/src/models/source/source.dart';
import 'package:financemanager/src/models/source_category/source_category.dart';

final List<Source> sources = [
  const Source(
    name: 'Cash',
    sourceCategory: SourceCategory.cash,
    balanceAmount: Amount(
      value: 500.0,
      id: 0,
    ),
  ),
  const Source(
    name: 'Axis',
    sourceCategory: SourceCategory.bank,
    balanceAmount: Amount(
      value: 1000.0,
      id: 1,
    ),
  ),
  const Source(
    name: 'ICICI',
    sourceCategory: SourceCategory.bank,
    balanceAmount: Amount(
      value: 2000.0,
      id: 2,
    ),
  ),
  const Source(
    name: 'CUB',
    sourceCategory: SourceCategory.bank,
    balanceAmount: Amount(
      value: 3000.0,
      id: 3,
    ),
  ),
  const Source(
    name: 'IOB',
    sourceCategory: SourceCategory.bank,
    balanceAmount: Amount(
      value: 4000.0,
      id: 4,
    ),
  ),
  const Source(
    name: 'HDFC',
    sourceCategory: SourceCategory.bank,
    balanceAmount: Amount(
      value: 5000.0,
      id: 5,
    ),
  ),
  const Source(
    name: 'PayTm',
    sourceCategory: SourceCategory.eWallet,
    balanceAmount: Amount(
      value: 6000.0,
      id: 6,
    ),
  ),
  const Source(
    name: 'Amazon',
    sourceCategory: SourceCategory.eWallet,
    balanceAmount: Amount(
      value: 7000.0,
      id: 7,
    ),
  ),
  const Source(
    name: 'PhonePe',
    sourceCategory: SourceCategory.eWallet,
    balanceAmount: Amount(
      value: 8000.0,
      id: 8,
    ),
  ),
];
