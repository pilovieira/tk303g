package br.com.pilovieira.tk303g.persist;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import br.com.pilovieira.tk303g.R;

public class Prefs {

	private Context context;

	public Prefs(Context context) {
		this.context = context;
	}
	
	public String getTrackerNumber() {
		return getSharedPreferences().getString(context.getString(R.string.PREF_TRACKER_NUMBER), "");
	}

	public void setTrackerNumber(String trackerNumber) {
		String oldNumber = getTrackerNumber();
		if (trackerNumber.equals(oldNumber))
			return;

		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(context.getString(R.string.PREF_TRACKER_NUMBER), trackerNumber);
		editor.apply();
	}

	public String getPassword() {
		return getSharedPreferences().getString(context.getString(R.string.PREF_PASSWORD), "");
	}

	public void setPassword(String trackerNumber) {
		String oldNumber = getTrackerNumber();
		if (trackerNumber.equals(oldNumber))
			return;

		SharedPreferences.Editor editor = getSharedPreferences().edit();
		editor.putString(context.getString(R.string.PREF_PASSWORD), trackerNumber);
		editor.apply();
	}

	private SharedPreferences getSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(this.context);
	}
}
