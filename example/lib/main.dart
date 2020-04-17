import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:zstd_flutter/zstd_flutter.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String show_test = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {

    // Platform messages may fail, so we use a try/catch PlatformException.
    String res;
    try {

      Uint8List ori=new Uint8List(100);
      ori[10]=10;
      Uint8List com = await ZstdFlutter.compress(ori, 3);
      int size = await ZstdFlutter.decompressed_size(com);
      Uint8List ori2 = await ZstdFlutter.decompress(com,size);
      if(ori.length==ori2.length&&ori[10]==ori2[10]){
        res="ok";
      }else{
        res="err";
      }
    } on PlatformException {
      res = 'Failed to zstd compress';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      show_test = res;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('zstd compress is: $show_test\n'),
        ),
      ),
    );
  }
}
