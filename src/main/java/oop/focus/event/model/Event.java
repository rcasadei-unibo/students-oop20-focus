package oop.focus.event.model;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import oop.focus.calendar.persons.model.Person;
import oop.focus.common.Repetition;

/**
 * This interface model an event.
 * An event has a name a start hour , an end hour , a start day , an end day.
 * Also, an event can be repeated every day, every week, every month or every year , and can be carried out in the company of one or more people.
 */
public interface Event extends EventExtension {

    /**
     * This method is used to know both the end date and the end time of an event.
     * @return LocalDateTime.
     */
    LocalDateTime getEnd();

    /**
     * This method is used for get the event end day.
     *  @return a LocalDate.
     */
    LocalDate getEndDate();

    /**
     * This method is used for get the event end hour.
     *  @return a LocalDateTime.
     */
    LocalTime getEndHour();

    /**
     * This method is used for get the event name.
     * @return a String.
     */
    String getName();

    /**
     * This method is used to know the list of people who will attend the event.
     * @return the persons list, return an empty list if no other people will attend.
     */
    List<Person> getPersons();
 
    /**
     * This method is used to know both the start date and the start time of an event.
     * @return LocalDateTime.
     */
    LocalDateTime getStart();

    /**
     * This method is used for get the event start day.
     *  @return a LocalDate.
     */
    LocalDate getStartDate();

    /**
     * This method is used for get the event start hour.
     * @return a LocalDateTime.
     */
    LocalTime getStartHour();

    /**
     * This method is used to know if an event repeats itself or not, and if it recurs, to know how often.
     *  @return a member of the Repetition enumeration.
     */
    Repetition getRepetition();

    /**
     * This method is utilized when we want to stop repeting an event.
     * @return an event.
     */
    Event getNextRenewal();

    /**
     * This method is used to know the status of the repetitions.
     * @return true if the event is repeated false otherwise.
     */
    boolean isRepeated();

    /**
     * This method stop the repetition.
     */
    void stopRepeat();

    /**
     * This method is used to get the start time of the event.
     * @return String that represent the start time of the event.
     */
    String getStartTime();

    /**
     * This method is used to get the start day of the event.
     * @return String that represent the start day of the event.
     */
    String getStartDay();

}
