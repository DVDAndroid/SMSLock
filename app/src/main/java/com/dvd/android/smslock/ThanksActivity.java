package com.dvd.android.smslock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class ThanksActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SMSReceiver.deviceManger.lockNow();

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				ThanksActivity.this);

		alertDialogBuilder.setTitle(getApplicationContext().getString(
                R.string.hi));
		alertDialogBuilder.setIcon(R.drawable.ic_launcher);
		alertDialogBuilder.setMessage(getApplicationContext().getString(
                R.string.cred));
		alertDialogBuilder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						finish();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.setCancelable(false);
		alertDialog.show();
	}
}
