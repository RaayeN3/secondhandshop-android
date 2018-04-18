package ie.bask.niftysecondhandshop;

import org.junit.Test;

import ie.bask.niftysecondhandshop.models.AdvertCar;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by cecobask on 13-Mar-18.
 */

public class AdvertCarTest {

    private AdvertCar toyota = new AdvertCar("1", "content://media/external/images/media/99", "Toyota", "Prius", 2012, 5000.0, "Waterford", "Car is well-kept. Mileage is only 50,000!", "cecobask@abv.bg");
    private AdvertCar honda = new AdvertCar("2", "content://media/external/images/media/150", "Honda", "Fit", 2014, 3500.0, "Dunmore East", "What a garbage. Trying to get rid of it...", "baskski@gmail.com");

    @Test
    public void testCreate() {
        assertEquals("content://media/external/images/media/99", toyota.getImageUri());
        assertEquals("Toyota", toyota.getCarMake());
        assertEquals("Prius", toyota.getCarModel());
        assertEquals(2012, toyota.getCarYear());
        assertEquals(5000.0, toyota.getCarPrice());
        assertEquals("Waterford", toyota.getCarLocation());
        assertEquals("Car is well-kept. Mileage is only 50,000!", toyota.getCarDescription());
        assertEquals("cecobask@abv.bg", toyota.getUserEmail());

        assertEquals("content://media/external/images/media/150", honda.getImageUri());
        assertEquals("Honda", honda.getCarMake());
        assertEquals("Fit", honda.getCarModel());
        assertEquals(2014, honda.getCarYear());
        assertEquals(3500.0, honda.getCarPrice());
        assertEquals("Dunmore East", honda.getCarLocation());
        assertEquals("What a garbage. Trying to get rid of it...", honda.getCarDescription());
        assertEquals("baskski@gmail.com", honda.getUserEmail());
    }

    @Test
    public void testToString() {
        assertEquals("AdvertCar{carID='1', imageUri='content://media/external/images/media/99', carMake='Toyota', carModel='Prius', carYear=2012, carPrice=5000.0, carLocation='Waterford', carDescription='Car is well-kept. Mileage is only 50,000!', userEmail='cecobask@abv.bg'}",
                toyota.toString());

        assertEquals("AdvertCar{carID='2', imageUri='content://media/external/images/media/150', carMake='Honda', carModel='Fit', carYear=2014, carPrice=3500.0, carLocation='Dunmore East', carDescription='What a garbage. Trying to get rid of it...', userEmail='baskski@gmail.com'}",
                honda.toString());
    }

    @Test
    public void testNotSame() {
        assertNotSame("Honda", toyota.getCarMake());
        assertNotSame(toyota, honda);
        assertNotSame(5000, toyota.getCarPrice());
        assertNotSame("Waterford", toyota.getCarLocation(), honda.getCarLocation());
    }

    @Test
    public void testEquals() {
        assertEquals(toyota, toyota);
        assertFalse(toyota.equals(honda));
        assertNotEquals(toyota, honda);
        assertEquals(honda, honda);
    }
}
