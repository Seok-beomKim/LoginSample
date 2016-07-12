package salmon.tuna.brandon.loginsample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.github.brandon.fingerprintauthenticationdialog.FingerprintCallback;
import com.github.brandon.fingerprintauthenticationdialog.FingerprintDialog;

/**
 * Created by SBKim on 2016-07-08.
 */
public class Fingerprint {
    private static String TAG = Fingerprint.class.getName();

    public static void verify(final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(activity, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED) {
            FingerprintManager fingerprintManager = (FingerprintManager) activity.getSystemService(Context.FINGERPRINT_SERVICE);
            boolean fingerprintEnabled = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(activity.getString(R.string.fingerprint_setting_key), true);
            if (fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints() && fingerprintEnabled) {
                FingerprintDialog.Builder builder = new FingerprintDialog.Builder(activity);
                builder.build("LoginSample", "Touch me to login", new FingerprintCallback() {
                    @Override
                    public void onAuthenticated() {
                        Log.d(TAG, "onAuthenticated()");
                    }

                    @Override
                    public void onError(int msgId) {
                        Log.d(TAG, "onError()");
                        if (msgId == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT)
                            activity.finish();
                    }
                });
                builder.show();
            } else if (fingerprintManager.isHardwareDetected() && !fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(activity, "Settings -> Security -> Fingerprints........", Toast.LENGTH_SHORT).show();
            } else {
                // .......
            }
        }
    }
}
