package ie.bask.niftysecondhandshop;

import org.junit.Test;

import ie.bask.niftysecondhandshop.models.Advert;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by cecobask on 13-Mar-18.
 */

public class AdvertTest {

    private Advert iphone = new Advert("content://media/external/images/media/11", "iPhone 7", 600.0, "Tramore", "Perfect condition. Looks like new! :)");
    private Advert samsung = new Advert("content://media/external/images/media/13", "Samsung Galaxy S9", 999.9, "Waterford", "Bought as a present. They didn't like it, so that's why I'm selling it.");

    @Test
    public void testCreate() {
        assertEquals("content://media/external/images/media/11", iphone.getImageUri());
        assertEquals("iPhone 7", iphone.getProductTitle());
        assertEquals(600.0, iphone.getProductPrice());
        assertEquals("Tramore", iphone.getProductLocation());
        assertEquals("Perfect condition. Looks like new! :)", iphone.getProductDescription());

        assertEquals("content://media/external/images/media/13", samsung.getImageUri());
        assertEquals("Samsung Galaxy S9", samsung.getProductTitle());
        assertEquals(999.9, samsung.getProductPrice());
        assertEquals("Waterford", samsung.getProductLocation());
        assertEquals("Bought as a present. They didn't like it, so that's why I'm selling it.", samsung.getProductDescription());
    }

    @Test
    public void testToString() {
        assertEquals("Advert{productID=1, imageUri='content://media/external/images/media/11', productTitle='iPhone 7', productPrice=600.0, productLocation='Tramore', productDescription='Perfect condition. Looks like new! :)'}",
                iphone.toString());

        assertEquals("Advert{productID=2, imageUri='content://media/external/images/media/13', productTitle='Samsung Galaxy S9', productPrice=999.9, productLocation='Waterford', productDescription='Bought as a present. They didn't like it, so that's why I'm selling it.'}",
                samsung.toString());
    }

    @Test
    public void testNotSame() {
        assertNotSame("Samsung Galaxy S9", iphone.getProductTitle());
        assertNotSame(iphone, samsung);
        assertNotSame(600, iphone.getProductPrice());
        assertNotSame("Waterford", iphone.getProductLocation(), samsung.getProductLocation());
    }

    @Test
    public void testEquals() {
        assertEquals(iphone, iphone);
        assertFalse(iphone.equals(samsung));
        assertNotEquals(iphone, samsung);
        assertEquals(samsung, samsung);
    }
}
