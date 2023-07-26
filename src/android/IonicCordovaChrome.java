package cordova.plugin.ionic.chrome;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;
import androidx.browser.customtabs.CustomTabsIntent;

/**
 * This class echoes a string called from JavaScript.
 */
public class IonicCordovaChrome extends CordovaPlugin {
  final String TAG = "IonicCordovaChromeTag";
  int minVersionOfWebView = 0;
  String webViewUrl = "";
  int CHROME_CUSTOM_TAB_REQUEST_CODE = minVersionOfWebView;

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
  }

  public IonicCordovaChrome() {
  }

  @Override
  public void pluginInitialize() {
    cordova.setActivityResultCallback(this);
    ConfigXmlParser parser = new ConfigXmlParser();
    parser.parse(cordova.getContext());
    CordovaPreferences preferences = parser.getPreferences();
    webViewUrl = preferences.getString("webview-url", "");
    String minWebViewVersion = preferences.getString("minimum-webview-version", "61");
    minVersionOfWebView = Integer.parseInt(minWebViewVersion);
   Log.d(TAG, "pluginInitialize: "+minWebViewVersion);
   Log.d(TAG, "pluginInitialize: "+webViewUrl);
    CHROME_CUSTOM_TAB_REQUEST_CODE = minVersionOfWebView;
    if (!isMinimumWebViewInstalled()) {
      openChrome(webViewUrl);
    }
  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    Log.d(TAG, "execute: ");
    return false;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == CHROME_CUSTOM_TAB_REQUEST_CODE) {
      if (resultCode == Activity.RESULT_OK) {
      }
      if (resultCode == Activity.RESULT_CANCELED) {
       this.cordova.getActivity()).finish();
      }
    }
  }

  private void openChrome(String url) {
    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder().enableUrlBarHiding();
    intentBuilder.setToolbarColor(Color.TRANSPARENT);
    intentBuilder.setShowTitle(true);
    CustomTabsIntent intent = intentBuilder.build();
    intent.intent.setData(Uri.parse(url));
    this.cordova.getActivity().startActivityForResult(intent.intent, CHROME_CUSTOM_TAB_REQUEST_CODE);
  }

  @SuppressLint("WebViewApiAvailability")
  public boolean isMinimumWebViewInstalled() {
    PackageManager pm = cordova.getActivity().getPackageManager();
    // Check getCurrentWebViewPackage() directly if above Android 8
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      PackageInfo info = WebView.getCurrentWebViewPackage();
      Log.d(TAG, "isMinimumWebViewInstalled: " + info.packageName);
      if (info.packageName.equals("com.huawei.webview")) {
        String majorVersionStr = info.versionName.split("\\.")[0];
        int majorVersion = Integer.parseInt(majorVersionStr);
        Log.d(TAG, "isMinimumWebViewInstalled1: " + majorVersion);
        return majorVersion >= minVersionOfWebView;
      }
      String majorVersionStr = info.versionName.split("\\.")[0];
      int majorVersion = Integer.parseInt(majorVersionStr);
      Log.d(TAG, "isMinimumWebViewInstalled2: " + majorVersion);
      return majorVersion >= minVersionOfWebView;
    }
    // Otherwise manually check WebView versions
    try {
      String webViewPackage = "com.google.android.webview";
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        webViewPackage = "com.android.chrome";
      }
      PackageInfo info = getPackageInfo(pm, webViewPackage);
      Log.d(TAG, "isMinimumWebViewInstalled: " + info.packageName);
      String majorVersionStr = info.versionName.split("\\.")[0];
      int majorVersion = Integer.parseInt(majorVersionStr);
      Log.d(TAG, "isMinimumWebViewInstalled3: " + majorVersion);
      return majorVersion >= minVersionOfWebView;
    } catch (Exception ex) {
      Log.d(TAG, "isMinimumWebViewInstalled: Unable to get package info for 'com.google.android.webview'" + ex.toString());
    }

    try {
      PackageInfo info = getPackageInfo(pm, "com.android.webview");
      Log.d(TAG, "isMinimumWebViewInstalled: " + info.packageName);
      String majorVersionStr = info.versionName.split("\\.")[0];
      int majorVersion = Integer.parseInt(majorVersionStr);
      Log.d(TAG, "isMinimumWebViewInstalled4: " + majorVersion);
      return majorVersion >= minVersionOfWebView;
    } catch (Exception ex) {
      Log.d(TAG, "isMinimumWebViewInstalled: Unable to get package info for 'com.android.webview'" + ex.toString());
    }

    // Could not detect any webview, return false
    return false;
  }

  public PackageInfo getPackageInfo(PackageManager pm, String packageInfo) throws PackageManager.NameNotFoundException {
    return pm.getPackageInfo(packageInfo, 0);
  }

}
