package com.skyshi.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by skyshi on 12/04/16.
 */
public class SmsReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "bundle";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras =intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }
            //Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            MainActivity ma = MainActivity.instance();
            ma.updateList(smsMessageStr);
        }
    }
}
