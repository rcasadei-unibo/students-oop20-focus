package oop.focus.statistics.model;

import javafx.util.Pair;
import oop.focus.common.Repetition;
import oop.focus.db.DataSourceImpl;
import oop.focus.finance.model.*;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestDailyExpenses {

    private final FinanceManager financeManager = new FinanceManagerImpl(new DataSourceImpl());

    @Test
    public void testDailyExpenses1() {
        var dataSource = new FinanceStatisticFactoryImpl(this.financeManager);
        var data = dataSource.dailyExpenses();
        // riferimenti ai conti
        var ac1 = new AccountImpl("AccountTDaily1", "ff6666", 150_000);
        var ac2 = new AccountImpl("AccountTDaily2", "ff6666", 10_000);
        this.financeManager.addAccount(ac1);
        this.financeManager.addAccount(ac2);

        this.financeManager.addTransaction(new TransactionImpl("TransactionT1",
                new CategoryImpl("Spesa", ""),
                new LocalDateTime(1960, 7, 1, 0, 0, 0),
                ac1, +2500000, Repetition.ONCE));
        this.financeManager.addTransaction(new TransactionImpl("TransactionT2",
                new CategoryImpl("Taxi", ""),
                new LocalDateTime(1960, 7, 5, 0, 0, 0),
                ac2, -2500000, Repetition.ONCE));
        this.financeManager.addTransaction(new TransactionImpl("TransactionT3",
                new CategoryImpl("Spesa", ""),
                new LocalDateTime(1960, 7, 6, 0, 0, 0),
                ac1, -2500000, Repetition.ONCE));


        assertTrue(data.get().stream().map(Pair::getValue).collect(Collectors.toList())
                .containsAll(List.of(-2500000, -2500000, 2500000)));

        this.financeManager.removeAccount(ac1);
        this.financeManager.removeAccount(ac2);
    }

    @Test
    public void testDailyExpenses2() {
        var dataSource = new FinanceStatisticFactoryImpl(this.financeManager);

        // riferimenti ai conti
        var ac1 = new AccountImpl("AccountT1", "ff6666", 150_000);
        var ac2 = new AccountImpl("AccountT2", "ff6666", 10_000);
        var data = dataSource.dailyAccountExpenses(ac1);
        this.financeManager.addAccount(ac1);
        this.financeManager.addAccount(ac2);

        this.financeManager.addTransaction(new TransactionImpl("TransactionT1",
                new CategoryImpl("Spesa", ""),
                new LocalDateTime(1960, 7, 1, 0, 0, 0),
                ac1, +250, Repetition.ONCE));
        this.financeManager.addTransaction(new TransactionImpl("TransactionT2",
                new CategoryImpl("Taxi", ""),
                new LocalDateTime(1960, 7, 5, 0, 0, 0),
                ac2, -250, Repetition.ONCE));
        this.financeManager.addTransaction(new TransactionImpl("TransactionT3",
                new CategoryImpl("Spesa", ""),
                new LocalDateTime(1960, 7, 1, 0, 0, 0),
                ac1, -250, Repetition.ONCE));


        assertEquals(List.of(0), data.get()
                .stream().map(Pair::getValue).collect(Collectors.toList()));

        this.financeManager.removeAccount(ac1);
        this.financeManager.removeAccount(ac2);
    }
}
