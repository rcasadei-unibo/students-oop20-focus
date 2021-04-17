package oop.focus.diary.model;

import oop.focus.db.DataSourceImpl;
import oop.focus.homepage.model.EventManager;
import oop.focus.homepage.model.EventManagerImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StopwatchTest {
    private final EventManager me = new EventManagerImpl(new DataSourceImpl());
    private final CounterManager stopwatch = new CounterManagerImpl(this.me, false);
    private final TotalTimeEvent csc = new TotalTimeEventImpl(this.me);
    @Test
    public void testStopwatch() throws InterruptedException {
        final String cuc = "test3";
        this.stopwatch.createCounter(cuc);
        this.stopwatch.setStarterValue(0);
        this.stopwatch.startCounter();
        Thread.sleep(6000);
        this.stopwatch.stopCounter();
        Thread.sleep(1000);
        assertEquals(this.csc.computePeriod("test3").get().getSeconds(), 6);
        this.me.removeEvent(this.me.findByName(cuc).stream().findAny().get());
    }
}