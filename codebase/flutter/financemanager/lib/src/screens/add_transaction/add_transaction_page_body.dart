import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/utilities/my_navigator.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class AddTransactionPageBody extends StatelessWidget {
  const AddTransactionPageBody({
    Key? key,
    required GlobalKey<FormState> formKey,
    required TextEditingController categoryTextEditingController,
    required TextEditingController dateTextEditingController,
  })  : _formKey = formKey,
        _categoryTextEditingController = categoryTextEditingController,
        _dateTextEditingController = dateTextEditingController,
        super(key: key);

  final GlobalKey<FormState> _formKey;
  final TextEditingController _categoryTextEditingController;
  final TextEditingController _dateTextEditingController;

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: Scrollbar(
        child: SingleChildScrollView(
          physics: const BouncingScrollPhysics(),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              const SizedBox(
                height: 8.0,
              ),
              ...[
                TextFormField(
                  decoration: buildTextFormFieldInputDecoration(
                    labelText: addTransactionPageTextFormFieldAmountLabel,
                    hintText: addTransactionPageTextFormFieldAmountHint,
                    prefixIcon: Icons.attach_money_rounded,
                  ),
                  keyboardType: const TextInputType.numberWithOptions(
                    signed: false,
                    decimal: true,
                  ),
                  textInputAction: TextInputAction.next,
                  onChanged: (value) {
                    // setState(() {
                    //   _transaction.updateAmountValue(double.parse(value));
                    // });
                  },
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return addTransactionPageTextFormFieldAmountError;
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
                    labelText: addTransactionPageTextFormFieldTitleLabel,
                    hintText: addTransactionPageTextFormFieldTitleHint,
                    prefixIcon: Icons.title_rounded,
                  ),
                  keyboardType: TextInputType.text,
                  textCapitalization: TextCapitalization.words,
                  textInputAction: TextInputAction.next,
                  maxLength: 36,
                  onChanged: (value) {
                    // setState(() {
                    //   _transaction.setTitle(value);
                    // });
                  },
                ),
                TextFormField(
                  decoration: buildTextFormFieldInputDecoration(
                    labelText: addTransactionPageTextFormFieldDescriptionLabel,
                    hintText: addTransactionPageTextFormFieldDescriptionHint,
                    prefixIcon: Icons.description_rounded,
                  ),
                  keyboardType: TextInputType.text,
                  textCapitalization: TextCapitalization.words,
                  textInputAction: TextInputAction.done,
                  maxLines: 2,
                  minLines: 1,
                  maxLength: 72,
                  onChanged: (value) {
                    // setState(() {
                    //   _transaction.setDescription(value);
                    // });
                  },
                ),
                TextFormField(
                  controller: _categoryTextEditingController,
                  decoration: buildTextFormFieldInputDecoration(
                    labelText: addTransactionPageTextFormFieldCategoryLabel,
                    hintText: addTransactionPageTextFormFieldCategoryHint,
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
                        addTransactionPageTextFormFieldTransactionDateLabel,
                    hintText:
                        addTransactionPageTextFormFieldTransactionDateHint,
                    prefixIcon: Icons.date_range_rounded,
                  ),
                  readOnly: true,
                  onTap: () async {
                    // await handleTransactionDateSelection(context);
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
    );
  }

  /*
  // TODO: Timestamp fix
  Future handleTransactionDateSelection(
    BuildContext context,
  ) async {
    DateTime? newDate = await showDatePicker(
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
  */
}

void showCategorySelectionBottomSheet(
  BuildContext context,
) {
  showModalBottomSheet(
    useRootNavigator: true,
    isScrollControlled: true,
    context: context,
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(12.0),
    ),
    builder: (
      BuildContext context,
    ) {
      return ConstrainedBox(
        constraints: BoxConstraints(
          maxHeight: MediaQuery.of(context).size.height * 0.8,
        ),
        child: Container(
          decoration: const BoxDecoration(
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
                    onPressed: () => navigateBack(context),
                    child: const Padding(
                      padding: EdgeInsets.all(8.0),
                      child: Text(
                        addTransactionPageCategorySelectionBottomSheetSaveButton,
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

InputDecoration buildTextFormFieldInputDecoration({
  String labelText = '',
  String hintText = '',
  required IconData prefixIcon,
}) {
  return InputDecoration(
    labelText: labelText,
    hintText: hintText,
    prefixIcon: Icon(prefixIcon),
    filled: false,
    border: const OutlineInputBorder(),
  );
}
