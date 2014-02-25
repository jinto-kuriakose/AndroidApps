package com.project.taxcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText etBasicPay, etHRA, etRentPaid;
	private TextView exemption;
	private TableRow tvResult;
	private RelativeLayout tax;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etBasicPay = (EditText) findViewById(R.id.etv_basic_pay);
		etHRA = (EditText) findViewById(R.id.etv_hra);
		etRentPaid = (EditText) findViewById(R.id.etv_rent_paid);

		tax = (RelativeLayout) findViewById(R.id.tax);
		exemption = (TextView) findViewById(R.id.tv_exemption_result);
		tvResult = (TableRow) findViewById(R.id.id_result);
	}

	public void submit(View v) {

		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		
		Log.d(">>>", "basicPay = ssss");

		String payStr = etBasicPay.getText().toString();
		String hraStr = etHRA.getText().toString();
		String rentStr = etRentPaid.getText().toString();
		double basicPay = 0, hra = 0, rentPaid = 0, fourtyPercentageBP = 0;
		if (etBasicPay != null && !payStr.equalsIgnoreCase("")
				&& Integer.parseInt(payStr) > 0) {
			basicPay = Double.parseDouble(payStr);
			Log.d(">>>", "basicPay = " + basicPay);
			fourtyPercentageBP = (basicPay * 40) / 100;
			Log.d(">>>", "fourtyPercentageBP = " + fourtyPercentageBP);
		}
		if (etHRA != null && !hraStr.equalsIgnoreCase("")
				&& Integer.parseInt(hraStr) > 0) {
			hra = Double.parseDouble(hraStr);
		}
		if (etRentPaid != null && !rentStr.equalsIgnoreCase("")
				&& Integer.parseInt(rentStr) > 0) {
			rentPaid = Double.parseDouble(rentStr) - ((10 / 100) * basicPay);
		}

		if (basicPay <= 0)
			Toast.makeText(getApplicationContext(), "Enter basic pay value",
					Toast.LENGTH_LONG).show();
		else {
			final double result = getTaxExemption(fourtyPercentageBP, hra,
					rentPaid);

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					tax.setVisibility(View.VISIBLE);
					tvResult.setVisibility(View.VISIBLE);
					exemption.setText("" + result);
				}
			});
		}
	}

	private double getTaxExemption(Double basicPay, Double hra, Double rentPaid) {
		Log.d(">>>", "bp = " + basicPay);
		Log.d(">>>", "hra = " + hra);
		Log.d(">>>", "rp = " + rentPaid);
		if (hra <= 0 && rentPaid <= 0)
			return basicPay;
		else if (hra <= 0)
			return Math.min(basicPay, rentPaid);
		else if (rentPaid <= 0)
			return Math.min(basicPay, hra);
		else
			return Math.min(basicPay, Math.min(hra, rentPaid));
	}

	public void cancel(View v) {
		Log.d(">>>>", "Cancel");
	}
}
