package com.skyshi.sms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by skyshi on 12/04/16.
 */
public class SmsReceiver extends BroadcastReceiver {
    final SmsManager sms = SmsManager.getDefault();
    public SmsMessage smsMessage;
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if(bundle!=null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                        smsMessage = msgs[0];
                    } else {
                        Object pdus[] = (Object[]) bundle.get("pdus");
                        smsMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);
                    }
                    String phoneNumber = smsMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = smsMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                    PendingIntent contentIntent = PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),0);
                    Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(phoneNumber)
                            .setContentText(message)
                            .setSound(sound);
                    mBuilder.setContentIntent(contentIntent);
                    mBuilder.setDefaults(Notification.DEFAULT_SOUND);
                    mBuilder.setAutoCancel(true);
                    NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1,mBuilder.build());
                }
            }
        }catch (Exception e){

        }
    }
}
