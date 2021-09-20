package car_rental.api.rents;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetRentalCostController {

    @ResponseBody
    @PostMapping("/getrentalcost")
public String test(@RequestParam("cost") String rentalCostJSON){
        return rentalCostJSON;
    }

}
