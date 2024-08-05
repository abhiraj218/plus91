package com.example.batteryplugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryPlugin extends CordovaPlugin {

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("getBatteryStatus")) {
      this.getBatteryStatus(callbackContext);
      return true;
    }
    return false;
  }

  private void getBatteryStatus(CallbackContext callbackContext) {
    Context context = cordova.getActivity().getApplicationContext();
    IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    Intent batteryStatus = context.registerReceiver(null, ifilter);

    int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
    int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

    boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

    JSONObject result = new JSONObject();
    try {
      result.put("level", (level / (float) scale) * 100);
      result.put("isCharging", isCharging);
    } catch (JSONException e) {
      callbackContext.error("Error retrieving battery status");
      return;
    }

    callbackContext.success(result);
  }
}
