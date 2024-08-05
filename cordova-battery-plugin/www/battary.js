var exec = require('cordova/exec');

var BatteryPlugin = {
  getBatteryStatus: function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'BatteryPlugin', 'getBatteryStatus', []);
  }
};

module.exports = BatteryPlugin;
