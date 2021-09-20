package car_rental.api.currency;

public enum Currency {

    PLN("PLN"),
    EUR("EUR"),
    USD("USD");

    private final String name;

    Currency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
