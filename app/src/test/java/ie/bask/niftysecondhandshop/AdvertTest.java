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


    private Advert tv = new Advert("1", "https://firebasestorage.googleapis.com/v0/b/niftysecondhandshop.appspot.com/o/images%2F29e1a1bd-a97a-4483-a3cb-083f2e186b0b?alt=media&token=feea21ab-f443-4ab6-9b05-c7b5374ba9db",
            "Television", 1500.0, "Waterford", "No details.", "cecobask@abv.bg");
    private Advert samsung = new Advert("2", "https://firebasestorage.googleapis.com/v0/b/niftysecondhandshop.appspot.com/o/images%2F4c4e8da5-c9f1-4a27-8496-fe6ec9914cdc?alt=media&token=c20d5b14-3448-4f85-aded-0d37d2bbe865",
            "Samsung Galaxy S9", 999.9, "Waterford", "No details", "baskski@gmail.com");

    @Test
    public void testCreate() {
        assertEquals("https://firebasestorage.googleapis.com/v0/b/niftysecondhandshop.appspot.com/o/images%2F29e1a1bd-a97a-4483-a3cb-083f2e186b0b?alt=media&token=feea21ab-f443-4ab6-9b05-c7b5374ba9db", tv.getImageUri());
        assertEquals("Television", tv.getProductTitle());
        assertEquals(1500.0, tv.getProductPrice());
        assertEquals("Waterford", tv.getProductLocation());
        assertEquals("No details.", tv.getProductDescription());
        assertEquals("cecobask@abv.bg", tv.getUserEmail());

        assertEquals("https://firebasestorage.googleapis.com/v0/b/niftysecondhandshop.appspot.com/o/images%2F4c4e8da5-c9f1-4a27-8496-fe6ec9914cdc?alt=media&token=c20d5b14-3448-4f85-aded-0d37d2bbe865", samsung.getImageUri());
        assertEquals("Samsung Galaxy S9", samsung.getProductTitle());
        assertEquals(999.9, samsung.getProductPrice());
        assertEquals("Waterford", samsung.getProductLocation());
        assertEquals("No details", samsung.getProductDescription());
        assertEquals("baskski@gmail.com", samsung.getUserEmail());
    }

    @Test
    public void testToString() {
        assertEquals("Advert{productID='1', imageUri='https://firebasestorage.googleapis.com/v0/b/niftysecondhandshop.appspot.com/o/images%2F29e1a1bd-a97a-4483-a3cb-083f2e186b0b?alt=media&token=feea21ab-f443-4ab6-9b05-c7b5374ba9db', productTitle='Television', productPrice=1500.0, productLocation='Waterford', productDescription='No details.', userEmail='cecobask@abv.bg'}",
                tv.toString());

        assertEquals("Advert{productID='2', imageUri='https://firebasestorage.googleapis.com/v0/b/niftysecondhandshop.appspot.com/o/images%2F4c4e8da5-c9f1-4a27-8496-fe6ec9914cdc?alt=media&token=c20d5b14-3448-4f85-aded-0d37d2bbe865', productTitle='Samsung Galaxy S9', productPrice=999.9, productLocation='Waterford', productDescription='No details', userEmail='baskski@gmail.com'}",
                samsung.toString());
    }

    @Test
    public void testNotSame() {
        assertNotSame("Samsung Galaxy S9", tv.getProductTitle());
        assertNotSame(tv, samsung);
        assertNotSame(600, tv.getProductPrice());
        assertNotSame("Wexford", tv.getProductLocation());
    }

    @Test
    public void testEquals() {
        assertEquals(tv, tv);
        assertFalse(tv.equals(samsung));
        assertNotEquals(tv, samsung);
        assertEquals(samsung, samsung);
    }
}
