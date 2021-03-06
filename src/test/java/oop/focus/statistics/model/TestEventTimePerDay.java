package oop.focus.statistics.model;

import javafx.util.Pair;
import oop.focus.common.Repetition;
import oop.focus.db.DataSource;
import oop.focus.db.DataSourceImpl;
import oop.focus.db.exceptions.DaoAccessException;
import oop.focus.event.model.EventImpl;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestEventTimePerDay {

    private final DataSource dataSource = new DataSourceImpl();
    private int initialSize;

    @Before
    public void countInitial() {
        this.initialSize = this.dataSource.getEvents().getAll().size();
    }

    @After
    public void checkSize() {
        assertEquals(this.initialSize, this.dataSource.getEvents().getAll().size());
    }

    @Test
    public void testSingleDay() {
        var events = this.dataSource.getEvents();
        String evtName = "evt1";
        LocalDateTime s1 = new LocalDateTime(2018, 1, 1, 7, 0);
        LocalDateTime f1 = new LocalDateTime(2018, 1, 1, 9, 0);
        LocalDateTime s2 = new LocalDateTime(2018, 1, 1, 10, 0);
        LocalDateTime f2 = new LocalDateTime(2018, 1, 1, 15, 0);
        var evt1 = new EventImpl(evtName, s1, f1, Repetition.DAILY);
        var evt2 = new EventImpl(evtName, s2, f2, Repetition.DAILY);
        assertEquals(evt1.getStartDate(), evt2.getStartDate());
        var factory = new EventsStatisticFactoryImpl(this.dataSource.getEvents().getAll());
        var dataset = factory.eventTimePerDay(evtName);
        try {
            events.save(evt1);
            events.save(evt2);
            var data = dataset.get();
            assertEquals(1, data.size());
            var tmp = data.stream().findAny().orElseThrow();
            assertEquals(7 * 60, (int) tmp.getValue());

        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
        try {
            events.delete(evt1);
            events.delete(evt2);
        } catch (DaoAccessException e) {
            fail();
            e.printStackTrace();
        }
    }
    @Test
    public void testMultipleDays1() {
        var events = this.dataSource.getEvents();
        String evtName = "evt1";
        LocalDateTime s1 = new LocalDateTime(2018, 1, 1, 7, 0);
        LocalDateTime f1 = new LocalDateTime(2018, 1, 1, 7, 30);
        LocalDateTime s2 = new LocalDateTime(2018, 1, 2, 18, 0);
        LocalDateTime f2 = new LocalDateTime(2018, 1, 2, 22, 0);
        LocalDateTime s3 = new LocalDateTime(2018, 2, 3, 10, 0);
        LocalDateTime f3 = new LocalDateTime(2018, 2, 3, 15, 0);
        var evt1 = new EventImpl(evtName, s1, f1, Repetition.DAILY);
        var evt2 = new EventImpl(evtName, s2, f2, Repetition.DAILY);
        var evt3 = new EventImpl(evtName, s3, f3, Repetition.WEEKLY);
        var factory = new EventsStatisticFactoryImpl(this.dataSource.getEvents().getAll());
        var dataset = factory.eventTimePerDay(evtName);
        try {
            events.save(evt1);
            events.save(evt2);
            events.save(evt3);
        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
        var data = dataset.get();
        assertEquals(3, data.size());
        assertEquals(Set.of(30, 4 * 60, 5 * 60),
                data.stream().map(Pair::getValue).collect(Collectors.toSet()));

        try {
            events.delete(evt1);
            events.delete(evt2);
            events.delete(evt3);
        } catch (DaoAccessException e) {
            fail();
            e.printStackTrace();
        }
    }
    @Test
    public void testMultipleDays2() {
        var events = this.dataSource.getEvents();
        String evtName = "evt1";
        LocalDateTime s1 = new LocalDateTime(2018, 1, 1, 7, 0);
        LocalDateTime f1 = new LocalDateTime(2018, 1, 3, 3, 0);
        LocalDateTime s2 = new LocalDateTime(2018, 1, 3, 22, 0);
        LocalDateTime f2 = new LocalDateTime(2018, 1, 4, 4, 0);
        LocalDateTime s3 = new LocalDateTime(2018, 2, 2, 10, 0);
        LocalDateTime f3 = new LocalDateTime(2018, 2, 3, 11, 0);
        var evt1 = new EventImpl(evtName, s1, f1, Repetition.DAILY);
        var evt2 = new EventImpl(evtName, s2, f2, Repetition.DAILY);
        var evt3 = new EventImpl("AnotherName", s3, f3, Repetition.WEEKLY);
        var factory = new EventsStatisticFactoryImpl(this.dataSource.getEvents().getAll());
        var dataset = factory.eventTimePerDay(evtName);
        try {
            events.save(evt1);
            events.save(evt2);
            events.save(evt3);
        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
        var data = dataset.get();
        assertEquals(4, data.size());
        assertEquals(List.of(17 * 60, 24 * 60, 5 * 60, 4 * 60).stream().sorted().collect(Collectors.toList()),
                data.stream().map(Pair::getValue).sorted().collect(Collectors.toList()));

        try {
            events.delete(evt1);
            events.delete(evt2);
            events.delete(evt3);
        } catch (DaoAccessException e) {
            fail();
            e.printStackTrace();
        }
    }
    @Test
    public void testMultipleDays3() {
        var events = this.dataSource.getEvents();
        String evtName = "evt1";
        LocalDateTime s1 = new LocalDateTime(2018, 2, 1, 7, 45);
        LocalDateTime f1 = new LocalDateTime(2018, 2, 1, 9, 45);
        LocalDateTime s2 = new LocalDateTime(2018, 2, 3, 22, 30);
        LocalDateTime f2 = new LocalDateTime(2018, 2, 4, 0, 30);
        LocalDateTime s3 = new LocalDateTime(2018, 2, 2, 23, 0);
        LocalDateTime f3 = new LocalDateTime(2018, 2, 3, 4, 0);
        var evt1 = new EventImpl(evtName, s1, f1, Repetition.DAILY);
        var evt2 = new EventImpl(evtName, s2, f2, Repetition.DAILY);
        var evt3 = new EventImpl(evtName, s3, f3, Repetition.WEEKLY);
        var factory = new EventsStatisticFactoryImpl(this.dataSource.getEvents().getAll());
        var dataset = factory.eventTimePerDay(evtName);
        try {
            events.save(evt1);
            events.save(evt2);
            events.save(evt3);
        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
        var data = dataset.get();
        assertEquals(List.of(2 * 60, 60, 330, 30).stream().sorted().collect(Collectors.toList()),
                data.stream().map(Pair::getValue).sorted().collect(Collectors.toList()));
        assertEquals(4, data.size());


        try {
            events.delete(evt1);
            events.delete(evt2);
            events.delete(evt3);
        } catch (DaoAccessException e) {
            fail();
            e.printStackTrace();
        }
    }
    @Test
    public void testBounded(){
        var events = this.dataSource.getEvents();
        String evtName = "evt1";
        LocalDateTime s1 = new LocalDateTime(2018, 2, 1, 7, 45);
        LocalDateTime f1 = new LocalDateTime(2018, 2, 1, 9, 45);
        LocalDateTime s2 = new LocalDateTime(2018, 2, 3, 22, 30);
        LocalDateTime f2 = new LocalDateTime(2018, 2, 4, 0, 30);
        LocalDateTime s3 = new LocalDateTime(2018, 2, 2, 23, 0);
        LocalDateTime f3 = new LocalDateTime(2018, 2, 3, 4, 0);
        var evt1 = new EventImpl(evtName, s1, f1, Repetition.DAILY);
        var evt2 = new EventImpl(evtName, s2, f2, Repetition.DAILY);
        var evt3 = new EventImpl(evtName, s3, f3, Repetition.WEEKLY);
        var start = new LocalDate(2018, 2, 1);
        var end = new LocalDate(2018, 2, 3);
        var factory = new EventsStatisticFactoryImpl(this.dataSource.getEvents().getAll());
        var dataset = factory.eventTimePerDay(evtName,
                start, end);
        try {
            events.save(evt1);
            events.save(evt2);
            events.save(evt3);
        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
        var data = dataset.get();
        assertEquals(3, data.size());
        assertEquals(List.of(2 * 60, 60, 330).stream().sorted().collect(Collectors.toList()),
                data.stream().map(Pair::getValue).sorted().collect(Collectors.toList()));

        try {
            events.delete(evt1);
            events.delete(evt2);
            events.delete(evt3);
        } catch (DaoAccessException e) {
            fail();
            e.printStackTrace();
        }
    }
    @Test
    public void testAutoUpdate() {
        var events = this.dataSource.getEvents();
        String evtName = "evt1";
        LocalDateTime s1 = new LocalDateTime(2018, 2, 1, 7, 45);
        LocalDateTime f1 = new LocalDateTime(2018, 2, 1, 9, 45);
        LocalDateTime s2 = new LocalDateTime(2018, 2, 3, 22, 30);
        LocalDateTime f2 = new LocalDateTime(2018, 2, 4, 0, 30);
        LocalDateTime s3 = new LocalDateTime(2018, 2, 2, 23, 0);
        LocalDateTime f3 = new LocalDateTime(2018, 2, 3, 4, 0);
        var evt1 = new EventImpl(evtName, s1, f1, Repetition.DAILY);
        var evt2 = new EventImpl(evtName, s2, f2, Repetition.DAILY);
        var evt3 = new EventImpl(evtName, s3, f3, Repetition.WEEKLY);
        var factory = new EventsStatisticFactoryImpl(this.dataSource.getEvents().getAll());
        var dataset = factory.eventTimePerDay(evtName);

        try {
            events.save(evt1);
            events.save(evt2);
        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
        final var data = dataset.get();
        assertEquals(3, data.size());
        assertEquals(List.of(2 * 60, 90, 30).stream().sorted().collect(Collectors.toList()),
                data.stream().map(Pair::getValue).sorted().collect(Collectors.toList()));
        try {
            events.save(evt3);
        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
        assertEquals(4, dataset.get().size());
        assertEquals(List.of(2 * 60, 60, 330, 30).stream().sorted().collect(Collectors.toList()),
                dataset.get().stream().map(Pair::getValue).sorted().collect(Collectors.toList()));

        try{
            events.delete(evt3);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
        assertEquals(3, dataset.get().size());
        assertEquals(List.of(2*60, 90, 30).stream().sorted().collect(Collectors.toList()),
                data.stream().map(Pair::getValue).sorted().collect(Collectors.toList()));
        try {
            events.delete(evt1);
            events.delete(evt2);
        } catch (DaoAccessException e) {
            fail();
            e.printStackTrace();
        }
    }
}
