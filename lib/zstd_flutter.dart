import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/services.dart';

class ZstdFlutter {
  static const MethodChannel _channel =
      const MethodChannel('zstd_flutter');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<Uint8List> compress(Uint8List src,int level) async {
    final Uint8List out = await _channel.invokeMethod('compress',{"src":src,"level":level});
    return out;
  }

  static Future<Uint8List> decompress(Uint8List src,int original_size) async {
    final Uint8List out = await _channel.invokeMethod('decompress',{"src":src,"original_size":original_size});
    return out;
  }

  static Future<int> decompressed_size(Uint8List src) async {
    final int out = await _channel.invokeMethod('decompressed_size',{"src":src});
    return out;
  }
}
