package car_rental.api.rents;

import org.springframework.stereotype.Controller;

@Controller
public class RentFrontController {

    private final RentService rentService;

    public RentFrontController(RentService rentService) {
        this.rentService = rentService;
    }


}
