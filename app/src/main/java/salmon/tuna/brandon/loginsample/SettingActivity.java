package salmon.tuna.brandon.loginsample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new FingerprintFragment()).commit();
    }

    public static class FingerprintFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.fingerprint);
            SwitchPreference switchPreference = (SwitchPreference) findPreference(getString(R.string.fingerprint_setting_key));
            // To do.....
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(getString(R.string.fingerprint_setting_key))) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(key, sharedPreferences.getBoolean(key, true));
                editor.commit();
            }
        }
    }
}
