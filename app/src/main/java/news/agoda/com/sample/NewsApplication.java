package news.agoda.com.sample;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

import timber.log.Timber;

public class NewsApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();

        // plant debugging tree
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        // initialize Fresco
        Fresco.initialize(this);
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
            // TODO: log to some crash reporting system, e.g. BugSnag
        }
    }

}
