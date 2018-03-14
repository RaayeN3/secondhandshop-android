package ie.bask.niftysecondhandshop;

import org.junit.Test;

import ie.bask.niftysecondhandshop.models.AdvertFashion;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by cecobask on 13-Mar-18.
 */

public class AdvertFashionTest {

    private AdvertFashion adidas = new AdvertFashion("content://media/external/images/media/39", "Adidas Superstar", 90.0, "Shoes", "43", "Fenor", "Got them as a present, but they didn't fit me...");
    private AdvertFashion nike = new AdvertFashion("content://media/external/images/media/96", "Nike AirMax", 130.0, "Shoes", "45", "Dunmore East", "I got sick of them, want new ones.");

    @Test
    public void testCreate() {
        assertEquals("content://media/external/images/media/39", adidas.getImageUri());
        assertEquals("Adidas Superstar", adidas.getProductTitle());
        assertEquals(90.0, adidas.getProductPrice());
        assertEquals("Shoes", adidas.getProductType());
        assertEquals("43", adidas.getProductSize());
        assertEquals("Fenor", adidas.getProductLocation());
        assertEquals("Got them as a present, but they didn't fit me...", adidas.getProductDescription());

        assertEquals("content://media/external/images/media/96", nike.getImageUri());
        assertEquals("Nike AirMax", nike.getProductTitle());
        assertEquals(130.0, nike.getProductPrice());
        assertEquals("Shoes", nike.getProductType());
        assertEquals("45", nike.getProductSize());
        assertEquals("Dunmore East", nike.getProductLocation());
        assertEquals("I got sick of them, want new ones.", nike.getProductDescription());
    }

    @Test
    public void testToString() {
        assertEquals("AdvertFashion{productID=1, imageUri='content://media/external/images/media/39', productTitle='Adidas Superstar', productPrice=90.0, productType='Shoes', productSize='43', productLocation='Fenor', productDescription='Got them as a present, but they didn't fit me...'}",
                adidas.toString());

        assertEquals("AdvertFashion{productID=2, imageUri='content://media/external/images/media/96', productTitle='Nike AirMax', productPrice=130.0, productType='Shoes', productSize='45', productLocation='Dunmore East', productDescription='I got sick of them, want new ones.'}",
                nike.toString());
    }

    @Test
    public void testNotSame() {
        assertNotSame("Samsung Galaxy S9", adidas.getProductTitle());
        assertNotSame(adidas, nike);
        assertNotSame(43, adidas.getProductSize());
        assertNotSame("Waterford", adidas.getProductLocation(), nike.getProductLocation());
    }

    @Test
    public void testEquals() {
        assertEquals(adidas, adidas);
        assertFalse(adidas.equals(nike));
        assertNotEquals(adidas, nike);
        assertEquals(nike, nike);
    }
}
