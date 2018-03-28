package localPlayerPlugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.content.Context;
import android.app.Activity;
import android.widget.Toast;
import android.app.Dialog;

class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
  Context mycontext;
  Dialog mydialog;

  MyPlayerOnCompletionListener(Dialog dialog) {
    this.mydialog = dialog;
  }

  MyPlayerOnCompletionListener(Context context) {
    this.mycontext = context;
  }

  MyPlayerOnCompletionListener() {
  }

  @Override
  public void onCompletion(MediaPlayer mp) {

    //Toast.makeText( this.mycontext, "finish", Toast.LENGTH_SHORT).show();
    this.mydialog.dismiss();
  }
}