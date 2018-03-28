# localPlayerPlugin
a cordova plugin to play local video and get video duration only for android

cordova.plugins.localPlayerPlugin.localPlayer(url,result =>{
			console.log("result:",result);
		},error =>console.log("error:",error));
    
cordova.plugins.localPlayerPlugin.getDuration(url,result =>{
			console.log("result:",result);
		},error =>console.log("error:",error));
