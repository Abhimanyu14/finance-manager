import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/models/my_transaction/my_transaction.dart';
import 'package:financemanager/src/models/transactions.dart';
import 'package:financemanager/src/screens/add_transaction/add_transaction_page_body.dart';
import 'package:financemanager/src/screens/add_transaction/add_transaction_page_floating_action_button.dart';
import 'package:financemanager/src/widgets/my_app_bar.dart';
import 'package:financemanager/src/widgets/scaffold_body_container.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class AddTransactionPage extends StatefulWidget {
  const AddTransactionPage({
    Key? key,
  }) : super(key: key);

  @override
  _AddTransactionPageState createState() => _AddTransactionPageState();
}

class _AddTransactionPageState extends State<AddTransactionPage> {
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  final TextEditingController _categoryTextEditingController =
      TextEditingController();
  final TextEditingController _dateTextEditingController =
      TextEditingController();

  final MyTransaction _transaction = const MyTransaction();
  late Transactions transactions;

  @override
  void dispose() {
    _categoryTextEditingController.dispose();
    // _dateTextEditingController.dispose();
    super.dispose();
  }

  @override
  Widget build(
    BuildContext context,
  ) {
    transactions = Provider.of<Transactions>(context);
    _categoryTextEditingController.text = _transaction.category.title;
    // _dateTextEditingController.text = intl.DateFormat.yMMMd().format(
    //   _transaction.transactionTimestamp.toDateTime(),
    // );

    return Scaffold(
      appBar: const MyAppBar(
        title: addTransactionPageAppBarTitle,
      ),
      body: ScaffoldBodyContainer(
        child: AddTransactionPageBody(
          formKey: _formKey,
          categoryTextEditingController: _categoryTextEditingController,
          dateTextEditingController: _dateTextEditingController,
        ),
      ),
      floatingActionButton: AddTransactionPageFloatingActionButton(
        formKey: _formKey,
        transactions: transactions,
        transaction: _transaction,
      ),
    );
  }
}
