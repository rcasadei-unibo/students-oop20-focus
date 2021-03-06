package oop.focus.statistics.model;

import javafx.util.Pair;
import oop.focus.common.Repetition;
import oop.focus.db.DataSource;
import oop.focus.db.DataSourceImpl;
import oop.focus.db.exceptions.DaoAccessException;
import oop.focus.event.model.EventImpl;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TestEventsOccurrences {

    private final DataSource dataSource = new DataSourceImpl();

    @Test
    public void testEventsOccurrences1() {
        var events = this.dataSource.getEvents();
        var factory = new EventsStatisticFactoryImpl(this.dataSource.getEvents().getAll());
        String evt1Name = "evto1";
        String evt2Name = "evto2";
        String evt3Name = "evto3";
        String evt4Name = "evto4";
        LocalDateTime s1 = new LocalDateTime(2018, 1, 1, 7, 0);
        LocalDateTime f1 = new LocalDateTime(2018, 1, 1, 9, 0);
        LocalDateTime s2 = new LocalDateTime(2018, 1, 1, 10, 0);
        LocalDateTime f2 = new LocalDateTime(2018, 1, 1, 15, 0);
        var evt11 = new EventImpl(evt1Name, s1, f1, Repetition.ONCE);
        var evt12 = new EventImpl(evt1Name, s2, f2, Repetition.ONCE);
        var evt21 = new EventImpl(evt2Name, s1, f1, Repetition.ONCE);
        var evt22 = new EventImpl(evt2Name, s2, f2, Repetition.ONCE);
        var evt31 = new EventImpl(evt3Name, s1, f1, Repetition.ONCE);
        var evt41 = new EventImpl(evt4Name, s2, f2, Repetition.ONCE);

        var dataset = factory.eventsOccurrences();
        try {
            events.save(evt11);
            events.save(evt12);
            events.save(evt21);
            events.save(evt22);
            events.save(evt31);
            events.save(evt41);
        } catch (DaoAccessException e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(dataset.get().stream().map(Pair::getValue).sorted().collect(Collectors.toList())
                .containsAll(List.of(1, 1, 2, 2)));

        try {
            events.delete(evt11);
            events.delete(evt12);
            events.delete(evt21);
            events.delete(evt22);
            events.delete(evt31);
            events.delete(evt41);
        } catch (DaoAccessException e) {
            e.printStackTrace();
            fail();
        }
    }
}
