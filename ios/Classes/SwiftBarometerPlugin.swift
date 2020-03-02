import Flutter
import UIKit

public class SwiftBarometerPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "barometer_plugin", binaryMessenger: registrar.messenger())
    let instance = SwiftBarometerPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    if "initialiseBarometer" == call.name
    
  }
}
