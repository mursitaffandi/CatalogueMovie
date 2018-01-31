package com.mursitaffandi.cataloguemovie.setting;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mursitaffandi.cataloguemovie.R;
import com.mursitaffandi.cataloguemovie.util.alarm.AlarmReceiver;
import com.mursitaffandi.cataloguemovie.util.scheduler.SchedulerTask;

import java.util.Calendar;

import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    @BindString(R.string.key_notif_daily)
    String notif_daily;
    @BindString(R.string.key_notif_nowplaying)
    String notif_nowplaying;
    @BindString(R.string.key_setting_language)
    String setting_language;

    AlarmReceiver alarmReceiver = new AlarmReceiver();
    final Calendar currentDate = Calendar.getInstance();
    SchedulerTask schedulerTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_list);
        ButterKnife.bind(this, getActivity());
        schedulerTask = new SchedulerTask(getActivity());

        // triger callback on changes each item setting
        findPreference(notif_daily).setOnPreferenceChangeListener(this);
        findPreference(notif_nowplaying).setOnPreferenceChangeListener(this);
        findPreference(setting_language).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
            }
        });


    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String key_pref = preference.getKey().toString().toString().toString().toString();
        final String messageToast;
        boolean switch_state = (boolean) o;
        if (key_pref.equals(notif_daily)) {
            if (switch_state) {
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        alarmReceiver.setRepeatingAlarm(getActivity(), hourOfDay, minute);
                        StringBuilder builder = new StringBuilder()
                                .append(getString(R.string.message_toast_set_daily_reminder_on))
                                .append(String.valueOf(hourOfDay))
                                .append(":")
                                .append(String.valueOf(minute));

                        Toast.makeText(getActivity(), builder.toString(), Toast.LENGTH_SHORT).show();

                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();

            } else {
                alarmReceiver.cancelAlarm(getActivity());
                messageToast = getResources().getString(R.string.message_toast_set_daily_reminder_off);
                Toast.makeText(getActivity(), messageToast, Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (key_pref.equals(notif_nowplaying)) {
            if (switch_state) {
                schedulerTask.createPeriodicTask();
                messageToast = getResources().getString(R.string.message_toast_set_scheduler_on);

            } else {
                schedulerTask.cancelPeriodicTask();
                messageToast = getResources().getString(R.string.message_toast_set_scheduler_off);
            }
            Toast.makeText(getActivity(), messageToast, Toast.LENGTH_SHORT).show();

            return true;
        }
        return false;
    }
}
