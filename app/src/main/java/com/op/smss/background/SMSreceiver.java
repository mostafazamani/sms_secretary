package com.op.smss.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.op.smss.model.Items;

import java.util.ArrayList;
import java.util.List;

public class SMSreceiver extends BroadcastReceiver
{
    private final String TAG = this.getClass().getSimpleName();
    ItemDataBase itemDataBase;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle extras = intent.getExtras();
        itemDataBase = ItemDataBase.getDatabase(context);

        String strMessage = "";

        if ( extras != null )
        {
            Object[] smsextras = (Object[]) extras.get( "pdus" );

            for ( int i = 0; i < smsextras.length; i++ )
            {
                SmsMessage smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i]);

                String strMsgBody = smsmsg.getMessageBody().toString();
                String strMsgSrc = smsmsg.getOriginatingAddress();



                strMessage += "SMS from " + strMsgSrc + " : " + strMsgBody;

                List<Items> items = itemDataBase.smswordDao().getItem(strMsgBody);
                if (items.size()>0){
                    String k = items.get(0).getKey();

                    if (k.equals(strMsgBody)){

                        String v = items.get(0).getValue();


                        SmsManager sms = SmsManager.getDefault();
                        ArrayList<String> parts = sms.divideMessage(v);
                        sms.sendMultipartTextMessage(strMsgSrc, null, parts, null,
                                null);
                    }


                }

                Log.i(TAG, strMessage);
            }

        }

    }

}