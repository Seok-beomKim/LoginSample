package salmon.tuna.brandon.loginsample;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by SBKim on 2016-07-08.
 */
public class BaseActivity extends AppCompatActivity {

    private static String TAG = BaseActivity.class.getName();

    @Override
    protected void onResume() {
        super.onResume();
        applicationStatusCheck();
    }

    private void applicationStatusCheck() {
        if (getApplication() instanceof LoginApplication) {
            if (((LoginApplication) getApplication()).isReturnedForground()) {
                Log.d(TAG, "isReturnedForground()");
                Fingerprint.verify(BaseActivity.this);
            }
        }
    }
}
