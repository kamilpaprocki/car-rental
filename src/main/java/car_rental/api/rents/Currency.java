package car_rental.api.rents;

public enum Currency {

    PLN("PLN"),
    EUR("EUR"),
    USD("USD");

    private String name;

    Currency(String name) {
        this.name = name;
    }


}
