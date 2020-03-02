import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:barometer_plugin/barometer_plugin.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  double _barometerReading = -1.0;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    try {
     await BarometerPlugin.initialise();
    } on PlatformException {

    }

    if (!mounted) return;

    setState(() {
      _barometerReading = 0.0;
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
            child: Column(
                mainAxisSize: MainAxisSize.max,
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
              Text('Barometer on: $_barometerReading', style: TextStyle(fontSize: 26)),
              RaisedButton(
                  onPressed: () async {
                    final reading = await BarometerPlugin.reading;
                    setState(() {
                      _barometerReading = reading;
                    });
                  },
                  child: Text('Get pressure')),
            ])),
      ),
    );
  }
}
