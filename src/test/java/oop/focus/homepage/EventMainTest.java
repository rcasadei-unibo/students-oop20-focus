package oop.focus.homepage;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import oop.focus.homepage.model.Event;
import oop.focus.homepage.model.EventImpl;
import oop.focus.homepage.model.ManagerEvent;
import oop.focus.homepage.model.ManagerEventImpl;
import oop.focus.finance.Repetition;

public class EventMainTest {
	
	public static void main(final String... args) {
	    final Event first = new EventImpl("Shopping", new LocalDateTime(2021, 9, 26, 9, 30), new LocalDateTime(2021, 9, 26, 10, 30), Repetition.ONCE);
        final Event second = new EventImpl("Palestra", new LocalDateTime(2021, 9, 25, 8, 30), new LocalDateTime(2021, 9, 25, 10, 30), Repetition.ONCE);
        final Event third = new EventImpl("Università", new LocalDateTime(2021, 9, 25, 7, 30), new LocalDateTime(2021, 9, 25, 18, 30), Repetition.ONCE);
        final Event fourth = new EventImpl("Cinema", new LocalDateTime(2021, 9, 26, 19, 30), new LocalDateTime(2021, 9, 26, 22, 45), Repetition.ONCE);
        final Event fifth = new EventImpl("Spesa", new LocalDateTime(2021, 9, 25, 10, 00), new LocalDateTime(2021, 9, 25, 10, 30), Repetition.ONCE);
        final Event sixth = new EventImpl("Passeggiata", new LocalDateTime(2021, 9, 25, 6, 30), new LocalDateTime(2021, 9, 25, 10, 30), Repetition.ONCE);
        final Event seventh = new EventImpl("Acqua", new LocalDateTime(2021, 9, 26, 22, 30), new LocalDateTime(2021, 9, 26, 23, 30), Repetition.ONCE);
        final Event eighth = new EventImpl("Ikea", new LocalDateTime(2021, 9, 25, 19, 30), new LocalDateTime(2021, 9, 25, 22, 45), Repetition.ONCE);
        final Event ninth = new EventImpl("Passeggio", new LocalDateTime(2021, 9, 25, 12, 30), new LocalDateTime(2021, 9, 25, 12, 45), Repetition.ONCE);
        final Event ten = new EventImpl("Gita", new LocalDateTime(2021, 9, 20, 12, 00), new LocalDateTime(2021, 9, 26, 15, 00), Repetition.ONCE);
        final Event tempon = new EventImpl("Prova", new LocalDateTime(2021, 9, 26,14, 30 ), new LocalDateTime(2021, 9, 26, 15, 30), Repetition.ONCE);

        final ManagerEvent manager = new ManagerEventImpl();

        manager.addEvent(first);
        manager.addEvent(second);
        try{
        	manager.addEvent(third);
        } catch (IllegalStateException ignored) {}
        manager.addEvent(fourth);
        try{
        	manager.addEvent(fifth);
        } catch (IllegalStateException ignored) {}
        try{
        	manager.addEvent(sixth);
        } catch (IllegalStateException ignored) {}
        try{
        	manager.addEvent(seventh);
        } catch (IllegalStateException ignored) {}
        manager.addEvent(eighth);
        manager.addEvent(ninth);
        manager.addEvent(ten);
        manager.addEvent(tempon);

        final Set<Event> set = manager.getEvents();

        for(final Event event : set) {
        	System.out.println(" " + event.getName());
        }
        System.out.println(" ");

        List<Event> eventsList = manager.findByDate(new LocalDate(2021, 9, 26));
        for(final Event event : eventsList) {
        	System.out.println(" " + event.getName());
        }
        System.out.println(" ");

        eventsList = manager.takeOnly(eventsList);
        for(final Event event : eventsList) {
        	System.out.println(" " + event.getName());
        }
        System.out.println(" ");

        eventsList = manager.orderByHour(eventsList);
        for(final Event event : eventsList) {
        	System.out.println(" " + event.getName());
        }
        System.out.println(" ");

        eventsList = manager.findByDate(new LocalDate(2021, 9, 25));
        for(final Event event : eventsList) {
        	System.out.println(" " + event.getName());
        }
        System.out.println(" ");

        eventsList = manager.takeOnly(eventsList);
        for(final Event event : eventsList) {
        	System.out.println(" " + event.getName());
        }
        System.out.println(" ");

        eventsList = manager.orderByHour(eventsList);
        for(final Event event : eventsList) {
        	System.out.println(" " + event.getName());
        }
        System.out.println(" ");
	}
}