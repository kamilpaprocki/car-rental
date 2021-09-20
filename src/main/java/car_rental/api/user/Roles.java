package car_rental.api.user;

public enum Roles {
    ADMIN("admin"),
    USER("user"),
    WORKER("worker");

    private String name;

    Roles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
