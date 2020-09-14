package de.antibotdeluxe.delivery.tests;

import de.antibotdeluxe.delivery.misc.Utility;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Test if the methods<br><code>convertListToString(List list)</code><br><code>convertStringToList(String compressed)</code><br>
 * in the {@link Utility}'s class are working properly.
 *
 * @author jhz
 */
public class ConversionTest {

    private List<String> list;
    private String string;

    /**
     * Initializing the test environment.
     */
    @Before
    public void before() {
        System.out.println("\n[Test] Initializing ConversionTest..");
        this.list = Arrays.asList("Lisa", "is", "a", "beautiful", "woman");
        this.string = "ABC::DE::FGH";
    }

    /**
     * Closing the test environment.
     */
    @After
    public void after() {
        System.out.println("[Test] Closing ConversionTest..\n");
        this.list = null;
        this.string = null;
    }

    /**
     * Using the test environment to actually check the {@link Utility}'s methods:<br>
     * <code>convertListToString(List list)</code><br>
     * <code>convertStringToList(String compressed)</code>
     */
    @Test
    public void checkStringListAndBackConversion() {
        System.out.println("[Test] Running ConversionTest..");
        Assert.assertEquals("The compressed String did not match the expected String.", "Lisa::is::a::beautiful::woman",
                Utility.convertListToString(this.list));
        List<String> compressed = Utility.convertStringToList(this.string);
        Assert.assertEquals("The compressed String did not match the uncompressed result in the list", "ABC", compressed.get(0));
        Assert.assertEquals("The compressed String did not match the uncompressed result in the list.", "DE", compressed.get(1));
        Assert.assertEquals("The compressed String did not match the uncompressed result in the list.", "FGH", compressed.get(2));
        System.out.println("[Test] No Conflicts running ConversionTest..");
    }

}
