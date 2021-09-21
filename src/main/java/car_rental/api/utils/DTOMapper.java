package car_rental.api.utils;

public interface DTOMapper<F, T> {
    T mapToDTO(F from);
    F mapToDAO(T to);
}
