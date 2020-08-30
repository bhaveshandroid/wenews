package com.wenews.customlayout.utils;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.wenews.customlayout.MainActivity;
import com.wenews.customlayout.R;

public class NotificationUtils {

    private static NotificationUtils mNotificationUtils;

    public static NotificationUtils getInstance() {
        if (mNotificationUtils == null) {
            mNotificationUtils = new NotificationUtils();
        }
        return mNotificationUtils;
    }


    public void WeNewsNotification(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        String textmsg = activity.getString(R.string.notification_msg);
        String texttitle = activity.getString(R.string.notification_title);

        RemoteViews notificationLayout = new RemoteViews(activity.getPackageName(), R.layout.custom_layout_notification);
        notificationLayout.setTextViewText(R.id.title, texttitle);
        notificationLayout.setTextViewText(R.id.text, textmsg);
        String channelId = activity.getString(R.string.app_name);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(activity, channelId)
                        .setSmallIcon(R.drawable.ic_notification_icon)
                        .setCustomContentView(notificationLayout)
                        .setAutoCancel(false)
                        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Default",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }


}
