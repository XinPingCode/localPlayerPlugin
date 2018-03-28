var exec = require('cordova/exec');

exports.localPlayer = function(arg0, success, error) {
    exec(success, error, "localPlayerPlugin", "localPlayer", [arg0]);
};
exports.getDuration = function(arg0, success, error) {
    exec(success, error, "localPlayerPlugin", "getDuration", [arg0]);
};
