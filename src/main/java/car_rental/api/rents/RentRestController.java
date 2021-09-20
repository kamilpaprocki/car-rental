package car_rental.api.rents;

import car_rental.api.exceptions.BadRequestException;
import car_rental.api.exceptions.RentNotFoundException;
import car_rental.api.promotionCode.WrongPromotionCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RentRestController {

    private final RentService rentService;

    public RentRestController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping("/rents")
    public ResponseEntity<Rent> createRent(@RequestBody Rent rent, @RequestParam(required = false) String promotionCode){
        try{
        if (promotionCode == null){
            return new ResponseEntity<>(rentService.createOrUpdateRent(rent), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(rentService.createOrUpdateRent(rent, promotionCode), HttpStatus.CREATED);
    }catch(WrongPromotionCodeException e){
            throw new BadRequestException(e.getMessage());
        }
    }


    @GetMapping("/rents")
    public ResponseEntity<List<Rent>> getAllRents(){
        List<Rent> rents = rentService.getAllRents();
        if (rents.isEmpty()){
            throw new RentNotFoundException("There are no rents");
        }
        return new ResponseEntity<>(rents, HttpStatus.OK);
    }

    @GetMapping("/rents/{id}")
    public ResponseEntity<Rent> getRentById(@PathVariable Long id){
        Rent rent = rentService.getRentById(id);
        if (rent == null){
            throw new RentNotFoundException("There is no rent with id: " + id);
        }
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @PutMapping("/rents/{id}/extend")
    public ResponseEntity<Rent> extendPlannedRentDays(@PathVariable Long id, @RequestParam int days){
        try{
            return new ResponseEntity<>(rentService.extendPlannedRentDays(id,days), HttpStatus.OK);
        }catch(WrongRentException e){
            throw new RentNotFoundException(e.getMessage());
        }
    }

    @PutMapping("/rents/{id}/update")
    public ResponseEntity<Rent> updatePlannedRentDate(@PathVariable Long id, @RequestParam String returndate){
        try{
            return new ResponseEntity<>(rentService.updatePlannedReturnDate(id, returndate), HttpStatus.OK);
        }catch(WrongRentException e){
            throw new RentNotFoundException(e.getMessage());
        }
    }

    @DeleteMapping("/rents/{id}/delete")
    public ResponseEntity<Rent> deleteRentById(@PathVariable Long id){
        if (rentService.deleteRentById(id) > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }



}
