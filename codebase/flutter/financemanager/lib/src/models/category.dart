// TODO: Handle sub categories later
// import 'package:financemanager/src/models/sub_category.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

// TODO: Handle sub categories later
// import 'package:collection/collection.dart';

class Category {
  Category({
    @required id,
    // TODO: Handle sub categories later
    // subCategories = const <SubCategory>[],
    description,
    @required title,
  }) {
    this._id = id;
    // TODO: Handle sub categories later
    // this._subCategories = subCategories;
    this._description = description;
    this._title = title;
  }

  int _id;

  // TODO: Handle sub categories later
  // List<SubCategory> _subCategories;
  String _description;
  String _title;

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
    return '${objectRuntimeType(this, 'Category')}($_id, $_description, $_title)';
  }
}
