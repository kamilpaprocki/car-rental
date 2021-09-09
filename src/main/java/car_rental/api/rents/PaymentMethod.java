package car_rental.api.rents;

public enum PaymentMethod {

    CASH("Cash"),
    CREDIT_CARD("Credit card"),
    BANK_TRANSFER("Bank transfer"),
    BLIK("Blik");

    private String name;

    PaymentMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
