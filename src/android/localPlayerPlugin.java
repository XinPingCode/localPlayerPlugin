package localPlayerPlugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PermissionHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import java.io.File;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;
import java.lang.Exception;
import java.io.FileOutputStream;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.VideoView;
import android.widget.TextView;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaResourceApi;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.AssetFileDescriptor;
import android.view.Gravity;

/**
 * This class echoes a string called from JavaScript.
 */
public class localPlayerPlugin extends CordovaPlugin {
    String[] permissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE };
    private CallbackContext callbackContext = null;
    private Dialog dialog;
    public String mess;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("localPlayer")) {
            String message = args.getString(0);
            this.localPlayer(message, callbackContext);
            return true;
        } else if (action.equals("getDuration")) {
            String message = args.getString(0);
            this.getDuration(message, callbackContext);
            return true;
        }
        return false;
    }

    private void localPlayer(String path, CallbackContext callbackContext) {
        getPermission();
        mess = path;
        callbackContext.success(path);
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {

                openVideo(mess);

            }
        });
    }

    public void getPermission() {
        if (hasPermisssion()) {
        } else {
            PermissionHelper.requestPermissions(this, 0, permissions);
        }
    }

    public boolean hasPermisssion() {
        for (String p : permissions) {
            if (!PermissionHelper.hasPermission(this, p)) {
                return false;
            }
        }
        return true;
    }

    protected void openVideo(String path) {
        Uri uri = Uri.parse(path);
        Context context = this.cordova.getActivity().getApplicationContext();
        // Let's create the main dialog
        dialog = new Dialog(cordova.getActivity(), android.R.style.Theme_NoTitleBar);
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        //dialog.setOnDismissListener(this);
        //dialog.setOnDismissListener(context);

        // Main container layout
        LinearLayout main = new LinearLayout(cordova.getActivity());
        main.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        main.setOrientation(LinearLayout.VERTICAL);
        main.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        main.setVerticalGravity(Gravity.CENTER_VERTICAL);
        VideoView videoView = new VideoView(cordova.getActivity());
        videoView.setMediaController(new MediaController(context));
        //videoView.setOnCompletionListener(new MyPlayerOnCompletionListener(context));
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener(dialog));
        videoView.setVideoURI(uri);
        videoView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        videoView.start();
        main.addView(videoView);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.setContentView(main);
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    protected void getDuration(String path, CallbackContext callbackContext) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (Exception e) {

        }
        int duration = mediaPlayer.getDuration();
        String duration_s = String.valueOf(duration);
        callbackContext.success(duration_s);
    }
}
