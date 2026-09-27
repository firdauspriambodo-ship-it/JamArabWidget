package com.example.jamarabwidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClockWidget extends AppWidgetProvider {
    private static final String ACTION_TICK = "com.example.jamarabwidget.TICK";
    private static final int REQUEST_CODE = 1001;

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] ids) {
        updateAll(context, manager, ids);
        schedule(context);
    }

    @Override
    public void onEnabled(Context context) {
        updateAll(context, AppWidgetManager.getInstance(context),
                AppWidgetManager.getInstance(context).getAppWidgetIds(
                        new ComponentName(context, ClockWidget.class)));
        schedule(context);
    }

    @Override
    public void onDisabled(Context context) {
        cancel(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (ACTION_TICK.equals(action)
                || Intent.ACTION_BOOT_COMPLETED.equals(action)
                || Intent.ACTION_TIME_CHANGED.equals(action)
                || Intent.ACTION_TIMEZONE_CHANGED.equals(action)) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            updateAll(context, manager, manager.getAppWidgetIds(
                    new ComponentName(context, ClockWidget.class)));
            schedule(context);
        }
    }

    private static void updateAll(Context context, AppWidgetManager manager, int[] ids) {
        String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        time = toArabicDigits(time);

        for (int id : ids) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clock_widget);
            views.setTextViewText(R.id.clock_text, time);
            manager.updateAppWidget(id, views);
        }
    }

    private static String toArabicDigits(String value) {
        return value.replace('0','٠').replace('1','١').replace('2','٢')
                .replace('3','٣').replace('4','٤').replace('5','٥')
                .replace('6','٦').replace('7','٧').replace('8','٨').replace('9','٩');
    }

    private static void schedule(Context context) {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarm == null) return;

        Intent intent = new Intent(context, ClockWidget.class).setAction(ACTION_TICK);
        PendingIntent pi = PendingIntent.getBroadcast(
                context, REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Align the first refresh close to the next minute, then refresh once per minute.
        long now = System.currentTimeMillis();
        long nextWallClock = ((now / 60_000L) + 1L) * 60_000L;
        long nextElapsed = SystemClock.elapsedRealtime() + Math.max(1_000L, nextWallClock - now);
        alarm.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, nextElapsed, 60_000L, pi);
    }

    private static void cancel(Context context) {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarm == null) return;

        Intent intent = new Intent(context, ClockWidget.class).setAction(ACTION_TICK);
        PendingIntent pi = PendingIntent.getBroadcast(
                context, REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarm.cancel(pi);
    }
}
