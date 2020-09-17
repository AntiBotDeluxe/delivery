package de.antibotdeluxe.delivery.tests.utility;

import de.antibotdeluxe.delivery.misc.Utility;
import de.antibotdeluxe.delivery.misc.exceptions.PortAlreadyBoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Test if the method <br><code>checkPortAvailability(int port)</code><br> in the {@link Utility}'s class is working properly.
 *
 * @author jhz
 */

public class PortAvailabilityTest {

    private ServerSocket serverSocket;

    /**
     * Initialize the test environment.
     *
     * @throws Exception {@link IOException} from {@link ServerSocket}
     */
    @Before
    public void before() throws Exception {
        System.out.println("\n[Test] Initializing PortAvailabilityTest..");
        this.serverSocket = new ServerSocket(1337);
    }

    /**
     * Cleaning up the test environment.
     *
     * @throws Exception {@link IOException} from {@link ServerSocket}
     */
    @After
    public void after() throws Exception {
        System.out.println("[Test] Closing PortAvailabilityTest..\n");
        if (this.serverSocket != null)
            this.serverSocket.close();
    }

    /**
     * Using the test environment to actually check for any errors.
     * <p>
     * A suitable port has to be:<br>
     * <code> - at least 4 digits long </code><br>
     * <code> - at most 5 digits long </code><br>
     * <code> - above +1.023 and below +65.535 </code>
     */
    @Test
    public void testPortAvailabilityTest() {
        System.out.println("[Test] Running PortAvailabilityTest..");
        try {
            Utility.checkPortAvailability(-1);
            Assert.fail("The port did not not meet the port regulations. {port: -1}");
        } catch (IllegalArgumentException ignored) { }
        try {
            Utility.checkPortAvailability(0);
            Assert.fail("The port did not not meet the port regulations. {port: 0}");
        } catch (IllegalArgumentException ignored) { }
        try {
            Utility.checkPortAvailability(1020);
            Assert.fail("The port did not not meet the port regulations. {port: 1020}");
        } catch (IllegalArgumentException ignored) { }
        try {
            Utility.checkPortAvailability(90000);
            Assert.fail("The port did not not meet the port regulations. {port: 90000}");
        } catch (IllegalArgumentException ignored) { }
        try {
            Utility.checkPortAvailability(1337);
            Assert.fail("The port is already in use but was detected as 'not in use'.");
        } catch (PortAlreadyBoundException ignored) { }
        try {
            this.serverSocket.close();
        } catch (IOException ignored) { }
        Assert.assertTrue("Port is not in use but was detected as 'in use'.", Utility.checkPortAvailability(1337));
        System.out.println("[Test] No conflicts running PortAvailabilityTest..");
    }

}
