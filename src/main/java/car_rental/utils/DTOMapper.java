package car_rental.utils;

public interface DTOMapper<F, T> {
    T from(F from);
}
