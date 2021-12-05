import 'package:financemanager/src/constants/colors.dart';
import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/models/timestamp.dart';
import 'package:financemanager/src/models/transaction.dart';
import 'package:financemanager/src/models/transactions.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:intl/intl.dart' as intl;
import 'package:provider/provider.dart';

class AddTransactionPage extends StatefulWidget {
  @override
  _AddTransactionPageState createState() => _AddTransactionPageState();
}

class _AddTransactionPageState extends State<AddTransactionPage> {
  final _formKey = GlobalKey<FormState>();

  final TextEditingController _categoryTextEditingController =
      TextEditingController();
  final TextEditingController _dateTextEditingController =
      TextEditingController();

  Transaction _transaction = Transaction();
  Transactions transactions;

  @override
  void dispose() {
    _categoryTextEditingController.dispose();
    _dateTextEditingController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    transactions = Provider.of<Transactions>(context);
    _categoryTextEditingController.text = _transaction.category.title;
    _dateTextEditingController.text = intl.DateFormat.yMMMd().format(
      _transaction.transactionTimestamp.toDateTime(),
    );
    return Scaffold(
      appBar: AppBar(
        title: const Text(ADD_TRANSACTION_PAGE_APP_BAR_TITLE),
        brightness: Brightness.dark,
      ),
      body: SafeArea(
        child: Form(
          key: _formKey,
          child: Scrollbar(
            child: SingleChildScrollView(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.start,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  SizedBox(
                    height: 8.0,
                  ),
                  ...[
                    TextFormField(
                      decoration: buildTextFormFieldInputDecoration(
                        labelText:
                            ADD_TRANSACTION_PAGE_TEXT_FORM_FIELD_AMOUNT_LABEL,
                        hintText:
                            ADD_TRANSACTION_PAGE_TEXT_FORM_FIELD_AMOUNT_HINT,
                        prefixIcon: Icons.attach_money_rounded,
                      ),
                      keyboardType: TextInputType.numberWithOptions(
                        signed: false,
                        decimal: true,
                      ),
                      textInputAction: TextInputAction.next,
                      onChanged: (value) {
                        setState(() {
                          _transaction.updateAmountValue(double.parse(value));
                        });
                      },
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return ADD_TRANSACTION_PAGE_TEXT_FORM_FIELD_AMOUNT_ERROR;
                        }
                        return null;
                      },
                      inputFormatters: [
                        FilteringTextInputFormatter.allow(
                          RegExp(r'^\d*\.?\d{0,2}'),
                        ),
                      ],
                      autovalidateMode: AutovalidateMode.onUserInteraction,
                    ),
                    TextFormField(
                      decoration: buildTextFormFieldInputDecoration(
                        labelText:
                            ADD_TRANSACTION_PAGE_TEXT_FORM_FIELD_TITLE_LABEL,
                        hintText:
                            ADD_TRANSACTION_PAGE_TEXT_FORM_FIELD_TITLE_HINT,
                        prefixIcon: Icons.title_rounded,
                      ),
                      keyboardType: TextInputType.text,
                      textCapitalization: TextCapitalization.words,
                      textInputAction: TextInputAction.next,
                      maxLength: 36,
                      onChanged: (value) {
                        setState(() {
                          _transaction.setTitle(value);
                        });
                      },
                    ),
                    TextFormField(
                      decoration: buildTextFormFieldInputDecoration(
                        labelText:
                            ADD_TRANSACTION_PAGE_TEXT_FORM_FIELD_DESCRIPTION_LABEL,
                        hintText:
                            ADD_TRANSACTION_PAGE_TEXT_FORM_FIELD_DESCRIPTION_HINT,
                        prefixIcon: Icons.description_rounded,
                      ),
                      keyboardType: TextInputType.text,
                      textCapitalization: TextCapitalization.words,
                      textInputAction: TextInputAction.done,
                      maxLines: 2,
                      minLines: 1,
                      maxLength: 72,
                      onChanged: (value) {
                        setState(() {
                          _transaction.setDescription(value);
                        });
                      },
                    ),
                    TextFormField(
                      controller: _categoryTextEditingController,
                      decoration: buildTextFormFieldInputDecoration(
                        labelText:
                            ADD_TRANSACTION_PAGE_TEXT_FORM_FIELD_CATEGORY_LABEL,
                        hintText:
                            ADD_TRANSACTION_PAGE_TEXT_FORM_FIELD_CATEGORY_HINT,
                        prefixIcon: Icons.category_rounded,
                      ),
                      readOnly: true,
                      textInputAction: TextInputAction.next,
                      onTap: () {
                        showCategorySelectionBottomSheet(context);
                      },
                    ),
                    TextFormField(
                      controller: _dateTextEditingController,
                      decoration: buildTextFormFieldInputDecoration(
                        labelText:
                            ADD_TRANSACTION_PAGE_TEXT_FORM_FIELD_TRANSACTION_DATE_LABEL,
                        hintText:
                            ADD_TRANSACTION_PAGE_TEXT_FORM_FIELD_TRANSACTION_DATE_HINT,
                        prefixIcon: Icons.date_range_rounded,
                      ),
                      readOnly: true,
                      onTap: () async {
                        await handleTransactionDateSelection(context);
                      },
                    ),
                  ].expand(
                    (widget) => [
                      Padding(
                        padding: const EdgeInsets.symmetric(
                          horizontal: 16.0,
                          vertical: 8.0,
                        ),
                        child: widget,
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        child: const Icon(
          Icons.done_rounded,
        ),
        tooltip:
            ADD_TRANSACTION_PAGE_FLOATING_ACTION_BUTTON_TOOLTIP_SAVE_TRANSACTION,
        backgroundColor: PRIMARY_COLOR,
        onPressed: () {
          handleFormSubmission(context);
        },
      ),
    );
  }

  InputDecoration buildTextFormFieldInputDecoration({
    String labelText = '',
    String hintText = '',
    IconData prefixIcon,
  }) {
    return InputDecoration(
      labelText: labelText,
      hintText: hintText,
      prefixIcon: Icon(prefixIcon),
      filled: false,
      border: const OutlineInputBorder(),
    );
  }

  void showCategorySelectionBottomSheet(BuildContext context) {
    showModalBottomSheet(
      useRootNavigator: true,
      isScrollControlled: true,
      context: context,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(12.0),
      ),
      builder: (BuildContext context) {
        return ConstrainedBox(
          constraints: BoxConstraints(
            maxHeight: MediaQuery.of(context).size.height * 0.8,
          ),
          child: Container(
            decoration: BoxDecoration(
              borderRadius: BorderRadius.only(
                topLeft: Radius.circular(12.0),
                topRight: Radius.circular(12.0),
              ),
              color: Colors.white,
            ),
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    TextButton(
                      onPressed: () => Navigator.pop(context),
                      child: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: const Text(
                          ADD_TRANSACTION_PAGE_CATEGORY_SELECTION_BOTTOM_SHEET_SAVE_BUTTON,
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
        );
      },
    );
  }

  Future handleTransactionDateSelection(BuildContext context) async {
    DateTime newDate = await showDatePicker(
      context: context,
      initialDate: _transaction.transactionTimestamp.toDateTime(),
      firstDate: DateTime(1900),
      lastDate: DateTime(2100),
    );

    if (newDate != null) {
      setState(() {
        _dateTextEditingController.text =
            intl.DateFormat.yMMMd().format(newDate);
        _transaction.setTransactionTimestamp(
          Timestamp.fromDateTime(
            dateTime: newDate,
          ),
        );
      });
    }
  }

  void handleFormSubmission(BuildContext context) {
    transactions.add(_transaction);
    if (_formKey.currentState.validate()) {
      Navigator.pop(context);
    }
  }
}
