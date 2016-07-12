package salmon.tuna.brandon.loginsample;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by SBKim on 2016-07-08.
 */
public class LoginApplication extends Application {

    private ApplicationStatus status = ApplicationStatus.BACKGROUND;

    public enum ApplicationStatus {
        FOREGROUND, // In foreground.
        BACKGROUND, // In background.
        RETURNED_TO_FOREGROUND; // Returned to foreground or first launch.
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new LoginActivityLifecycleCallbacks());
    }

    public LoginApplication getContext(Context context) {
        return (LoginApplication) context.getApplicationContext();
    }

    public ApplicationStatus getApplicationStatus() {
        return status;
    }

    public boolean isReturnedForground() {
        return status.ordinal() == ApplicationStatus.RETURNED_TO_FOREGROUND.ordinal();
    }

    public boolean isBackground() {
        return status.ordinal() == ApplicationStatus.BACKGROUND.ordinal();
    }

    public class LoginActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        private int running = 0; // Running activity count

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (++running == 1) {
                // Running activity is 1,
                // Application must be returned from background just now or first launch.
                status = ApplicationStatus.RETURNED_TO_FOREGROUND;
            } else if (running > 1) {
                // 2 or more running activities should be foreground already.
                status = ApplicationStatus.FOREGROUND;
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (--running == 0) {
                // no active activity => application goes to background.
                status = ApplicationStatus.BACKGROUND;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    }
}
