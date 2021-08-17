package car_rental.api.utils;

public interface DTOMapper<F, T> {
    T map(F from);
    F reverse(T to);
}
