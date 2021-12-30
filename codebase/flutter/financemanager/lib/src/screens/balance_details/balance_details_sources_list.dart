import 'package:financemanager/src/models/source/source.dart';
import 'package:flutter/material.dart';

class BalanceDetailsSourcesList extends StatelessWidget {
  const BalanceDetailsSourcesList({
    Key? key,
    required this.sources,
  }) : super(key: key);

  final List<Source> sources;

  @override
  Widget build(
    BuildContext context,
  ) {
    return ListView.builder(
      physics: const BouncingScrollPhysics(),
      itemCount: sources.length,
      itemBuilder: (
        BuildContext context,
        int index,
      ) {
        return ListTile(
          title: Text(
            sources[index].balanceAmount.showAmount(),
          ),
          subtitle: Text(
            sources[index].name,
          ),
        );
      },
    );
  }
}
