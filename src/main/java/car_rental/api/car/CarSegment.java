package car_rental.api.car;

public enum CarSegment {
    A("Segment A"),
    B("Segment B"),
    C("Segment C"),
    D("Segment D"),
    E("Segment E"),
    F("Segment F"),
    M("Segment M"),
    J("Segment J"),
    S("Segment S");

private final String carSegment;

    CarSegment(String carSegment) {
        this.carSegment = carSegment;
    }
}
