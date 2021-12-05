import 'package:financemanager/src/models/category.dart';
import 'package:test/test.dart';

void main() {
  group('Category Unit Tests : ', () {
    test('A new category object is created with given values and default values', () {
      int id = 0;
      // TODO: Handle sub categories later
      // List<SubCategory> defaultSubCategories = <SubCategory>[];
      String title = 'Food';
      Category category = Category(
        id: id,
        title: title,
      );
      expect(category.id, id);
      // TODO: Handle sub categories later
      // expect(category.subCategories, defaultSubCategories);
      expect(category.description, null);
      expect(category.title, title);
    });

    test('A new category object is created with given values', () {
      int id = 0;
      // TODO: Handle sub categories later
      // SubCategory subCategory = SubCategory(
      //   id: 0,
      //   title: 'Breakfast',
      // );
      // TODO: Handle sub categories later
      // List<SubCategory> subCategories = [subCategory];
      String description = 'All food expenses';
      String title = 'Food';
      Category category = Category(
        id: id,
        // TODO: Handle sub categories later
        // subCategories: subCategories,
        description: description,
        title: title,
      );
      expect(category.id, id);
      // TODO: Handle sub categories later
      // expect(category.subCategories, subCategories);
      expect(category.description, description);
      expect(category.title, title);
    });
  });
}
