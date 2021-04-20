package oop.focus.homepage;


import oop.focus.homepage.model.EventManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;
import oop.focus.homepage.model.Event;
import oop.focus.homepage.model.EventImpl;
import oop.focus.homepage.model.EventManagerImpl;
import oop.focus.homepage.model.Filter;
import oop.focus.db.DataSource;
import oop.focus.db.DataSourceImpl;
import oop.focus.common.Repetition;


public class EventTest {

	private final DataSource dsi = new DataSourceImpl();
    private final EventManager eventi = new EventManagerImpl(dsi);

    private final Event first = new EventImpl("Shopping", new LocalDateTime(2021, 9, 26, 9, 30), new LocalDateTime(2021, 9, 26, 10, 30), Repetition.ONCE);
    private final Event second = new EventImpl("Palestra", new LocalDateTime(2021, 9, 25, 8, 30), new LocalDateTime(2021, 9, 25, 9, 00), Repetition.ONCE);
    private final Event third = new EventImpl("Università", new LocalDateTime(2021, 9, 26, 11, 30), new LocalDateTime(2021, 9, 26, 18, 30), Repetition.ONCE);
    private final Event fourth = new EventImpl("Ikea", new LocalDateTime(2021, 9, 26, 9, 30), new LocalDateTime(2021, 9, 25, 10, 30), Repetition.ONCE);
    private final Event fifth = new EventImpl("Spesa", new LocalDateTime(2021, 9, 26, 9, 30), new LocalDateTime(2021, 9, 26, 6, 30), Repetition.ONCE);
    private final Event sixth = new EventImpl("Estetista", new LocalDateTime(2021, 9, 26, 9, 00), new LocalDateTime(2021, 9, 27, 10, 00), Repetition.ONCE);
    
    @Test
    public void addingAndRemovingEventTest() {
        try {
            this.eventi.addEvent(first);
            this.eventi.addEvent(third);
            this.eventi.addEvent(fourth);
            this.eventi.addEvent(fifth);
        } catch(IllegalStateException ignored) {}

        this.eventi.addEvent(second);

        assertTrue(this.eventi.getAll().contains(first));
        assertTrue(this.eventi.getAll().contains(second));
        assertTrue(this.eventi.getAll().contains(third));

        this.eventi.removeEvent(first);
        this.eventi.removeEvent(second);
        this.eventi.removeEvent(third);
    }

    @Test
    public void equalsEventsTest() {

    	final Event firstCopy = new EventImpl("Shopping", new LocalDateTime(2021, 9, 26, 9, 30), new LocalDateTime(2021, 9, 26, 10, 30), Repetition.DAILY);
    	assertEquals(first, firstCopy);

    	final Event secondCopy = new EventImpl("Palestra", new LocalDateTime(2021, 9, 25, 8, 30), new LocalDateTime(2021, 9, 25, 9, 00), Repetition.BIMONTHLY);
    	assertEquals(second, secondCopy);
    }

    @Test
    public void findEventsTest() {
        this.eventi.addEvent(sixth);
        for (final Event e : this.eventi.getAll()){
            System.out.println(e.getName());
        }
        assertTrue(Filter.takeOnlyDailyEvent(this.eventi.getAll().stream().collect(Collectors.toList())).contains(sixth));
        this.eventi.removeEvent(sixth);
    }

    @Test
    public void findByDateTest() {
        try {
            this.eventi.addEvent(first);
            this.eventi.addEvent(third);
            this.eventi.addEvent(sixth);
        }catch(IllegalStateException ignored){}
 
        assertTrue(this.eventi.findByDate(new LocalDate(2021, 9, 26)).contains(first));
        assertTrue(this.eventi.findByDate(new LocalDate(2021, 9, 26)).contains(third));
        assertTrue(this.eventi.findByDate(new LocalDate(2021, 9, 26)).contains(sixth));

        this.eventi.removeEvent(first);
        this.eventi.removeEvent(third);
        this.eventi.removeEvent(sixth);
    }

    @Test
    public void verificationOfTimeAccuracyTest() {

        assertEquals(first.getStartHour(), new LocalTime(9, 30));
        assertEquals(first.getEndHour(), new LocalTime(10, 30));

        assertEquals(second.getStartHour(), new LocalTime(8, 30));
        assertEquals(second.getEndHour(), new LocalTime(9, 00));

        assertEquals(third.getStartHour(), new LocalTime(11, 30));
        assertEquals(third.getEndHour(), new LocalTime(18, 30));
    }

}
