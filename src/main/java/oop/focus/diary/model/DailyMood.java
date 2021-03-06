package oop.focus.diary.model;

import org.joda.time.LocalDate;

/**
 * The interface DailyMood can be used to register the daily mood.
 * It defines methods to set/get the date and the id representative of the mood.
 *
 */
public interface DailyMood {
    /**
     * @return  the id relatives to the daily mood
     */
    int getMoodValue();
    /**
     * Sets the id relatives to the daily mood.
     * @param moodValue    the value relatives to the daily mood
     */
    void setMoodValue(int moodValue);
    /**
     * @return the date of the registration of the mood 
     */
    LocalDate getDate();

}
