package car_rental.api.rents;

import car_rental.api.exceptions.BadRequestException;
import car_rental.api.exceptions.PromotionCodeNotFoundException;
import car_rental.api.exceptions.RentNotFoundException;
import car_rental.api.exceptions.WrongArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public ResponseEntity<RentDTO> createRent(@RequestBody RentDTO rent, @RequestParam(required = false) String promotionCode){
        try{
        if (promotionCode == null){
            return new ResponseEntity<>(rentService.createOrUpdateRent(rent), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(rentService.createOrUpdateRent(rent, promotionCode), HttpStatus.CREATED);
    }catch(PromotionCodeNotFoundException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping("/rents")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<List<RentDTO>> getAllRents(@RequestParam(required = false) String rentStatus){

        List<RentDTO> rents;
        if (rentStatus == null){
            rents = rentService.getAllRents();
            if (rents.isEmpty()){
                throw new RentNotFoundException("There are no rents");
            }
            return new ResponseEntity<>(rents, HttpStatus.OK);
        }
        if ("active".equals(rentStatus)){
            rents = rentService.getActiveRents();
            if (rents.isEmpty()){
                throw new RentNotFoundException("There are no active rents");
            }
            return new ResponseEntity<>(rents, HttpStatus.OK);
        }
        if ("finished".equals(rentStatus)){
            rents = rentService.getFinishedRents();
            if (rents.isEmpty()){
                throw new RentNotFoundException("There are no finished rents");
            }
            return new ResponseEntity<>(rents, HttpStatus.OK);
        }
        throw new BadRequestException("Wrong input parameter. Input rent status: " + rentStatus);
    }

    @GetMapping("/rents/rent")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<RentDTO> getRentById(@RequestParam Long id){
        RentDTO rent = rentService.getRentDTOById(id);
        if (rent == null){
            throw new RentNotFoundException("There is no rent with id: " + id);
        }
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @PutMapping("/rents/extend")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public ResponseEntity<RentDTO> extendPlannedRentDays(@RequestParam Long id, @RequestParam int days){
        try{
            return new ResponseEntity<>(rentService.extendPlannedRentDays(id,days), HttpStatus.OK);
        }catch(WrongArgumentException e){
            throw new RentNotFoundException(e.getMessage());
        }
    }

    @PutMapping("/rents/update")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public ResponseEntity<RentDTO> updatePlannedRentDate(@RequestParam Long id, @RequestParam String returndate){
        try{
            return new ResponseEntity<>(rentService.updatePlannedReturnDate(id, returndate), HttpStatus.OK);
        }catch(WrongArgumentException e){
            throw new RentNotFoundException(e.getMessage());
        }
    }

    @DeleteMapping("/rents/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Rent> deleteRentById(@RequestParam Long id){
        if (rentService.deleteRentById(id) > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }



}
