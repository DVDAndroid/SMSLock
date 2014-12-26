package com.dvd.android.smslock;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Bundle;

public class LockAdminActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean active = SMSReceiver.deviceManger
				.isAdminActive(SMSReceiver.compName);
		while (!active) {
			Intent intent = new Intent(
					DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
					SMSReceiver.compName);
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
					(this.getString(R.string.perm)));
			startActivityForResult(intent, 1);
			System.exit(2);
		}

		SMSReceiver.deviceManger.lockNow();
		finish();
	}
}
