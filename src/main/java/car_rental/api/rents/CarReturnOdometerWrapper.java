package car_rental.api.rents;

import car_rental.api.utils.CarRentOdometerChecker;

@CarRentOdometerChecker
public class CarReturnOdometerWrapper {

    private String currentOdometer;

    private String lastOdometer;

    public String getCurrentOdometer() {
        return currentOdometer;
    }

    public void setCurrentOdometer(String currentOdometer) {
        this.currentOdometer = currentOdometer;
    }

    public String getLastOdometer() {
        return lastOdometer;
    }

    public void setLastOdometer(String lastOdometer) {
        this.lastOdometer = lastOdometer;
    }
}

