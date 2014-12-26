package com.dvd.android.smslock;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

	static DevicePolicyManager deviceManger;
	static ComponentName compName;
	ActivityManager activityManager;

	@Override
	public void onReceive(Context context, Intent intent) {
		deviceManger = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		compName = new ComponentName(context, MyAdmin.class);

		if (intent.getAction()
				.equals("android.provider.Telephony.SMS_RECEIVED")) {
			Bundle extras = intent.getExtras();
			if (extras != null) {

				SmsMessage[] messages = getMessagesFromIntent(intent);
				for (SmsMessage sms : messages) {
					String body = sms.getMessageBody();

					boolean active = deviceManger.isAdminActive(compName);

					if (body.equals("locknow")) {
						if (active) {
							Intent i_tk = new Intent(context,
									ThanksActivity.class);
							i_tk.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(i_tk);
						} else {
							Intent i_lock = new Intent(context,
									LockAdminActivity.class);
							i_lock.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(i_lock);
						}
					}
				}
			}
		}
	}

	private SmsMessage[] getMessagesFromIntent(Intent intent) {
		Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
		byte[][] pduObjs = new byte[messages.length][];

		for (int i = 0; i < messages.length; i++) {
			pduObjs[i] = (byte[]) messages[i];
		}
		byte[][] pdus = new byte[pduObjs.length][];
		int pduCount = pdus.length;
		SmsMessage[] msgs = new SmsMessage[pduCount];
		for (int i = 0; i < pduCount; i++) {
			pdus[i] = pduObjs[i];
			msgs[i] = SmsMessage.createFromPdu(pdus[i]);
		}
		return msgs;
	}

}