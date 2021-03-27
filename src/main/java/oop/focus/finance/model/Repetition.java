package oop.focus.finance.model;

import org.joda.time.LocalDate;

import java.util.function.Function;

public enum Repetition {

    /**
     * it is not repeated.
     */
    ONCE("Once", i -> 0, i -> 0, d -> d),
    /**
     * it is repeated daily.
     */
    DAILY("Daily", i -> (int) (i * 30.42),    i -> i * 365,               d -> d.plusDays(1)),
    /**
     * it is repeated every week.
     */
    WEEKLY("Weekly", i -> (int) (i * 4.35),    i -> (int) (i * 52.14),     d -> d.plusWeeks(1)),
    /**
     * it is repeated every month.
     */
    MONTHLY("Monthly", i -> i,                  i -> i * 12,                d -> d.plusMonths(1)),
    /**
     * it is repeated every two months.
     */
    BIMONTHLY("Bimonthly", i -> i / 2,            i -> i * 6,                 d -> d.plusMonths(2)),
    /**
     * it is repeated every three months.
     */
    QUARTERLY("Quaterly", i -> i / 3,            i -> i * 4,                 d -> d.plusMonths(3)),
    /**
     * it is repeated every six months.
     */
    HALF_YEARLY("Half_yearly", i -> i / 6,          i -> i * 2,                 d -> d.plusMonths(6)),
    /**
     * it is repeated every year.
     */
    YEARLY("Yearly", i -> i / 12,              i -> i,                     d -> d.plusYears(1));

    private final String name;
    private final Function<Integer, Integer> perMonthFunction;
    private final Function<Integer, Integer> perYearFunction;
    private final Function<LocalDate, LocalDate> nextRenewalFunction;

    Repetition(final String name, final Function<Integer, Integer> perMonthFunction, final Function<Integer, Integer> perYearFunction,
               final Function<LocalDate, LocalDate> nextRenewalFunction) {
        this.name = name;
        this.perMonthFunction = perMonthFunction;
        this.perYearFunction = perYearFunction;
        this.nextRenewalFunction = nextRenewalFunction;
    }

    /**
     * @return the function to calculate the average yearly expense
     */
    public Function<Integer, Integer> getPerMonthFunction() {
        return this.perMonthFunction;
    }

    /**
     * @return the function to calculate the average monthly expense
     */
    public Function<Integer, Integer> getPerYearFunction() {
        return this.perYearFunction;
    }

    /**
     * @return the function to calculate the day of the next renewal
     */
    public Function<LocalDate, LocalDate> getNextRenewalFunction() {
        return this.nextRenewalFunction;
    }

    public final String getName() {
        return this.name;
    }
}
