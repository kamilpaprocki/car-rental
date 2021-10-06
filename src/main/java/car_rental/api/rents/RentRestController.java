package car_rental.api.rents;

import car_rental.api.exceptions.BadRequestException;
import car_rental.api.exceptions.PromotionCodeNotFoundException;
import car_rental.api.exceptions.RentNotFoundException;
import car_rental.api.exceptions.WrongArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RentRestController {

    private final RentService rentService;

    private final static Logger logger = LoggerFactory.getLogger(RentRestController.class);

    public RentRestController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping("/rents")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public ResponseEntity<RentDTO> createRent(@RequestBody RentDTO rent, @RequestParam(required = false) String promotionCode){
        try{
        if (promotionCode == null){
            logger.info("Create new rent without promotion code.");
            return new ResponseEntity<>(rentService.createOrUpdateRent(rent), HttpStatus.CREATED);
        }
        logger.info("Create new rent with promotion code.");
        return new ResponseEntity<>(rentService.createOrUpdateRent(rent, promotionCode), HttpStatus.CREATED);
    }catch(PromotionCodeNotFoundException e){
            logger.error(e.getMessage());
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
                logger.error("List of rents is empty.");
                throw new RentNotFoundException("There are no rents");
            }
            logger.info("Return {} rents.", rents.size());
            return new ResponseEntity<>(rents, HttpStatus.OK);
        }
        if ("active".equals(rentStatus)){
            rents = rentService.getActiveRents();
            if (rents.isEmpty()){
                logger.error("List of active rents is empty.");
                throw new RentNotFoundException("There are no active rents");
            }
            logger.info("Return {} active rents.", rents.size());
            return new ResponseEntity<>(rents, HttpStatus.OK);
        }
        if ("finished".equals(rentStatus)){
            rents = rentService.getFinishedRents();
            if (rents.isEmpty()){
                logger.error("List of finished rents is empty.");
                throw new RentNotFoundException("There are no finished rents");
            }
            logger.info("Return {} finished rents.", rents.size());
            return new ResponseEntity<>(rents, HttpStatus.OK);
        }
        logger.error("Wrong rent status: {}.", rentStatus);
        throw new BadRequestException("Wrong input parameter. Input rent status: " + rentStatus);
    }

    @GetMapping("/rents/rent")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<RentDTO> getRentById(@RequestParam Long id){
        RentDTO rent = rentService.getRentDTOById(id);
        if (rent == null){
            logger.error("Rent with id {} not found.", id);
            throw new RentNotFoundException("There is no rent with id: " + id);
        }
        logger.info("Return rent with id {}.", id);
        return new ResponseEntity<>(rent, HttpStatus.OK);
    }

    @PutMapping("/rents/extend")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public ResponseEntity<RentDTO> extendPlannedRentDays(@RequestParam Long id, @RequestParam int days){
        try{
            logger.info("Extend rent with id {} by {} days.", id, days);
            return new ResponseEntity<>(rentService.extendPlannedRentDays(id,days), HttpStatus.OK);
        }catch(WrongArgumentException e){
            logger.error(e.getMessage());
            throw new RentNotFoundException(e.getMessage());
        }
    }

    @PutMapping("/rents/update")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public ResponseEntity<RentDTO> updatePlannedRentDate(@RequestParam Long id, @RequestParam String returndate){
        try{
            logger.info("Extend rent with id {} to date {}.", id, returndate);
            return new ResponseEntity<>(rentService.updatePlannedReturnDate(id, returndate), HttpStatus.OK);
        }catch(WrongArgumentException e){
            logger.error(e.getMessage());
            throw new RentNotFoundException(e.getMessage());
        }
    }

    @DeleteMapping("/rents/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Rent> deleteRentById(@RequestParam Long id){
        if (rentService.deleteRentById(id) > 0){
            logger.info("Delete rent with id {}.", id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.info("Rent with id {} do not exist.", id);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }



}
