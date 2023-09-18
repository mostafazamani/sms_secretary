package com.op.smss.background;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.op.smss.background.SMSreceiver;

public class ServiceCommunicator extends Service
{
    private SMSreceiver mSMSreceiver;
    private IntentFilter mIntentFilter;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.i("Service","Oncreated");
        //SMS event receiver
        mSMSreceiver = new SMSreceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSreceiver, mIntentFilter);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        // Unregister the SMS receiver
        unregisterReceiver(mSMSreceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}