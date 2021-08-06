package car_rental.api.utils;

public interface DTOMapper<F, T> {
    T from(F from);
}
