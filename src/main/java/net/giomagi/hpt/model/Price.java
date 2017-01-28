package net.giomagi.hpt.model;

public class Price extends ValueType implements CsvFriendly {
    public final int value;
    public final String currency;

    public Price(int value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public static Price of(int value, String currency) {
        return new Price(value, currency);
    }

    @Override
    public String asCsv() {
        return Integer.toString(value) + currency;
    }
}
