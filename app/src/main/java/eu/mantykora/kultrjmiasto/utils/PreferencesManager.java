package eu.mantykora.kultrjmiasto.utils;

import android.content.Context;
import android.icu.util.Calendar;
import android.preference.PreferenceManager;

import java.util.logging.Filter;

public class PreferencesManager {
    public static int getIntValue(Context context, String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
    }

    public static boolean getBooleanValue(Context context, String key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static FilterUtils.FilterInput.CheckboxesState getCheckboxesState(Context context) {
        return new FilterUtils.FilterInput.CheckboxesState(
                getBooleanValue(context, "checkBoxGdansk", false),
                getBooleanValue(context, "checkBoxGdynia", false),
                getBooleanValue(context, "checkBoxSopot", false),
                getBooleanValue(context, "checkBoxOther", false),
                getBooleanValue(context, "calendarSwitch", false));
    }

    public static FilterUtils.FilterInput.FilterDate getFilterDate(Context context) {
        return new FilterUtils.FilterInput.FilterDate(
                getIntValue(context, "year", 0),
                getIntValue(context, "month", 0),
                getIntValue(context, "day", 0));
    }

}
