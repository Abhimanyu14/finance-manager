import 'package:financemanager/src/constants/strings.dart';
import 'package:financemanager/src/models/amount/amount.dart';
import 'package:financemanager/src/models/category/category.dart';
import 'package:financemanager/src/models/sub_category.dart';

List<Category> defaultCategories = [
  defaultCategory,
];

Amount defaultAmount = const Amount(
  value: 0.0,
  id: 0,
);

/*Timestamp defaultTimestamp = Timestamp(
  date: '01-01-2022',
  time: '05:00:00',
);*/

Category defaultCategory = const Category(
  id: 0,
  // subCategories: [
  //   defaultSubCategory,
  // ],
  description: defaultCategoryDescription,
  title: defaultCategoryTitle,
);

SubCategory defaultSubCategory = SubCategory(
  id: 1,
  title: defaultSubCategoryTitle,
  description: defaultSubCategoryDescription,
  // ignore: todo
  // TODO: Link sub category to main category
  // mainCategories: [
  //   mainCategoryDefault,
  // ],
);
