import 'dart:core';

import 'package:meta/meta.dart';

import 'base/keyed_list.dart';
import 'base/model.dart';

class FCM implements KeyedListItem, Model {
  String key;
  String uid;
  String name;
  String language;

  FCM(this.uid, {@required this.name, @required this.language});

  Map<String, dynamic> toMap(bool isNew) => {
        'fcm/$uid/$key': {
          'name': name,
          'language': language,
        },
      };

  @override
  String get rootPath => 'fcm/$uid';
}