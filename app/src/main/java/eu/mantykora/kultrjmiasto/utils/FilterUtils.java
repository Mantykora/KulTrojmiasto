package eu.mantykora.kultrjmiasto.utils;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Filter;

import eu.mantykora.kultrjmiasto.model.CategoryEnum;
import eu.mantykora.kultrjmiasto.model.Event;

public class FilterUtils {
    public static class FilterInput {
        public static class CheckboxesState {
            private boolean gdanskCheckboxChecked;
            private boolean gdyniaCheckboxChecked;
            private boolean sopotCheckboxChecked;
            private boolean otherCitiesCheckboxChecked;
            private boolean dateSwitchEnabled;

            public CheckboxesState(boolean gdanskCheckboxChecked, boolean gdyniaCheckboxChecked, boolean sopotCheckboxChecked, boolean otherCitiesCheckboxChecked, boolean dateSwitchEnabled) {
                this.gdanskCheckboxChecked = gdanskCheckboxChecked;
                this.gdyniaCheckboxChecked = gdyniaCheckboxChecked;
                this.sopotCheckboxChecked = sopotCheckboxChecked;
                this.otherCitiesCheckboxChecked = otherCitiesCheckboxChecked;
                this.dateSwitchEnabled = dateSwitchEnabled;
            }

            public boolean isAnyCityCheckboxChecked() {
                return gdanskCheckboxChecked || gdyniaCheckboxChecked || sopotCheckboxChecked || otherCitiesCheckboxChecked;
            }

            public boolean isGdanskCheckboxChecked() {
                return gdanskCheckboxChecked;
            }

            public boolean isGdyniaCheckboxChecked() {
                return gdyniaCheckboxChecked;
            }

            public boolean isSopotCheckboxChecked() {
                return sopotCheckboxChecked;
            }

            public boolean isOtherCitiesCheckboxChecked() {
                return otherCitiesCheckboxChecked;
            }

            public boolean isDateSwitchEnabled() {
                return dateSwitchEnabled;
            }
        }

        private CheckboxesState checkboxesState;

        public static class FilterDate {
            private int year;
            private int month;
            private int day;
            private String dateString;

            public FilterDate(int year, int month, int day) {
                this.year = year;
                this.month = month;
                this.day = day;
                dateString = buildCurrentDate(this);
            }

            public String getDateString() {
                return dateString;
            }

            private String buildCurrentDate(FilterInput.FilterDate date) {
                int monthValue = date.month + 1;
                String month = monthValue < 10 ? "0" + monthValue : Integer.toString(monthValue);
                String day = date.day < 10 ? "0" + date.day : Integer.toString(date.day);
                return date.year + "-" + month + "-" + day;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }
        }

        private FilterDate date;

        private Set<CategoryEnum> selectedCategories;

        public CheckboxesState getCheckboxesState() {
            return checkboxesState;
        }

        public FilterDate getDate() {
            return date;
        }

        public Set<CategoryEnum> getSelectedCategories() {
            return selectedCategories;
        }

        public FilterInput(CheckboxesState checkboxesState, FilterDate date, Set<CategoryEnum> selectedCategories) {
            this.checkboxesState = checkboxesState;
            this.date = date;
            this.selectedCategories = selectedCategories;
        }

        public boolean isAnyFilterEnabled() {
            return checkboxesState.isAnyCityCheckboxChecked() || checkboxesState.isDateSwitchEnabled() || selectedCategories.size() != 0;
        }
    }

    private static class EventFilterPredicate implements Predicate<Event> {
        private FilterInput data;
        FilterInput.CheckboxesState checkboxes;

        public EventFilterPredicate(FilterInput data) {
            this.data = data;
            this.checkboxes = data.checkboxesState;
        }

        private final String[] CITIES = {"Gdańsk", "Gdynia", "Sopot"};

        private boolean otherFilterApplies(Event input) {
            String cityName = input.getLocation().getAddress().getCity();
            boolean result = true;
            for (String city : CITIES) {
                if (city.equals(cityName)) {
                    result = false;
                    break;
                }
            }

            return data.checkboxesState.isOtherCitiesCheckboxChecked() && result;
        }

        private boolean filterApplies(Boolean checkboxChecked, String cityName, Event input) {
            return input.getLocation() != null && checkboxChecked && cityName.equals(input.getLocation().getAddress().getCity());
        }

        private boolean filterAppliesToDate(Event input) {
            String dateString = input.getStartDate().substring(0, 10);
            return data.date.dateString.equals(dateString);
        }


        private boolean filterAppliesToCategories(Event input) {
            Integer eventCategoryId = input.getCategoryId();
            if (eventCategoryId == null) {
                return false;
            }

            eu.mantykora.kultrjmiasto.model.CategoryEnum enumValue = eu.mantykora.kultrjmiasto.model.CategoryEnum.forCode(eventCategoryId.intValue());

            return data.selectedCategories.contains(enumValue);
        }

        @Override
        public boolean apply(Event input) {
            return citiesFilter(input) && dateFilter(input) && categoriesFilter(input);
        }

        private boolean dateFilter(Event input) {
            if (!checkboxes.dateSwitchEnabled) {
                return true; // allow all dates since calendarSwitch is unchecked
            } else {
                return filterAppliesToDate(input);
            }
        }

        private boolean categoriesFilter(Event input) {
            if (data.selectedCategories.size() == 0) {
                return true; // allow all categories since nothing is selected right now
            } else {
                return filterAppliesToCategories(input);
            }
        }

        private boolean citiesFilter(Event input) {
            if (!checkboxes.isAnyCityCheckboxChecked()) {
                return true; // allow all cities since there is no filter for that
            } else {
                boolean gdanskFilter = filterApplies(checkboxes.gdanskCheckboxChecked, "Gdańsk", input);
                boolean gdyniaFilter = filterApplies(checkboxes.gdyniaCheckboxChecked, "Gdynia", input);
                boolean sopotFilter = filterApplies(checkboxes.sopotCheckboxChecked, "Sopot", input);
                boolean otherFilter = otherFilterApplies(input);

                return gdanskFilter || gdyniaFilter || sopotFilter || otherFilter;
            }
        }
    }

    public static List<Event> filterEvents(List<Event> events, final FilterInput filterInput) {
        if(filterInput.isAnyFilterEnabled()) {
            return new ArrayList<>(Collections2.filter(events, new EventFilterPredicate(filterInput)));
        } else {
            return new ArrayList<>(events);
        }
    }
}
