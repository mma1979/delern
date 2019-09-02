import 'dart:core';

import 'package:built_value/built_value.dart';
import 'package:delern_flutter/models/base/model.dart';

part 'fcm.g.dart';

abstract class FCM implements Built<FCM, FCMBuilder>, ReadonlyModel {
  String get key;
  String get uid;
  String get name;
  String get language;

  factory FCM([void Function(FCMBuilder) updates]) = _$FCM;
  FCM._();
}
