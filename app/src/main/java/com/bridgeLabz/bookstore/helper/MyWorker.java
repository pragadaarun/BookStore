package com.bridgeLabz.bookstore.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import com.bridgeLabz.bookstore.R;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    public static final String NOTIFICATION_CHANNEL_ID =   "notification_channel_id";
    public static final String NOTIFICATION_CHANNEL = "notification_channel";
    public static final String NOTIFICATION_TITLE = "notification_title";
    public static final String NOTIFICATION_MESSAGE = "notification_message";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String notificationChannelId = getInputData().getString(NOTIFICATION_CHANNEL_ID);
        String notificationChannel = getInputData().getString(NOTIFICATION_CHANNEL);
        String notificationTitle = getInputData().getString(NOTIFICATION_TITLE);
        String notificationMessage = getInputData().getString(NOTIFICATION_MESSAGE);

        displayNotification(notificationChannelId, notificationChannel, notificationTitle, notificationMessage);
        return Result.success();
    }

    private void displayNotification(String notificationChannelId, String notificationChannel, String notificationTitle, String notificationMessage) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(notificationChannelId, notificationChannel, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "cart")
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
    }
}
