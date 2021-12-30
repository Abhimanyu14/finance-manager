// TODO: Handle sub categories later
// import 'package:financemanager/src/models/sub_category.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

// TODO: Handle sub categories later
// import 'package:collection/collection.dart';

part 'category.freezed.dart';
part 'category.g.dart';

@freezed
class Category with _$Category {
  const Category._();
  const factory Category({
    required int id,
    @Default('') String description,
    required String title,
  }) = _Category;

  factory Category.fromJson(Map<String, dynamic> json) =>
      _$CategoryFromJson(json);
}

/*
class Category {
  Category({
    required id,
    // TODO: Handle sub categories later
    // subCategories = const <SubCategory>[],
    description,
    required title,
  }) {
    _id = id;
    // TODO: Handle sub categories later
    // this._subCategories = subCategories;
    _description = description;
    _title = title;
  }

  int _id = 0;

  // TODO: Handle sub categories later
  // List<SubCategory> _subCategories;
  String _description = '';
  String _title = '';

  int get id => _id;

  // TODO: Handle sub categories later
  // List<SubCategory> get subCategories => _subCategories;
  String get description => _description;

  String get title => _title;

  @override
  bool operator ==(other) {
    if (other.runtimeType != runtimeType) {
      return false;
    }
    return other is Category &&
        other.id == _id &&
        // TODO: Handle sub categories later
        // DeepCollectionEquality().equals(_subCategories, other.subCategories) &&
        other.description == _description &&
        other.title == _title;
  }

  @override
  int get hashCode {
    return hashValues(
      _id,
      // TODO: Handle sub categories later
      // hashList(_subCategories),
      _description,
      _title,
    );
  }

  @override
  String toString() {
    return '${objectRuntimeType(this, 'Category')}'
        '('
        '$_id, '
        '$_description, '
        '$_title'
        ')';
  }
}
*/
