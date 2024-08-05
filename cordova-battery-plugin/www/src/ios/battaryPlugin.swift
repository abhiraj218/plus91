import UIKit
import Cordova

@objc(BatteryPlugin) class BatteryPlugin: CDVPlugin {
  @objc(getBatteryStatus:)
  func getBatteryStatus(command: CDVInvokedUrlCommand) {
    UIDevice.current.isBatteryMonitoringEnabled = true
    let batteryLevel = UIDevice.current.batteryLevel
    let batteryState = UIDevice.current.batteryState

    let isCharging = batteryState == .charging || batteryState == .full

    let result = [
      "level": batteryLevel * 100,
      "isCharging": isCharging
    ] as [String : Any]

    let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: result)
    self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
  }
}
