import 'package:financemanager/src/models/category.dart';
import 'package:flutter/foundation.dart' hide Category;
import 'package:flutter/material.dart';

class SubCategory {
  SubCategory({
    parentCategory,
    @required id,
    description,
    @required title,
  }) {
    this._parentCategory = parentCategory;
    this._id = id;
    this._description = description;
    this._title = title;
  }

  Category _parentCategory;
  int _id;
  String _description;
  String _title;

  Category get parentCategory => _parentCategory;

  int get id => _id;

  String get description => _description;

  String get title => _title;

  @override
  bool operator ==(other) {
    if (other.runtimeType != runtimeType) {
      return false;
    }
    return other is SubCategory &&
        other.parentCategory == _parentCategory &&
        other.id == _id &&
        other.description == _description &&
        other.title == _title;
  }

  @override
  int get hashCode {
    return hashValues(
      _parentCategory,
      _id,
      _description,
      _title,
    );
  }

  @override
  String toString() {
    return '${objectRuntimeType(this, 'SubCategory')}($_parentCategory, $_id, $_description,$_title)';
  }
}
