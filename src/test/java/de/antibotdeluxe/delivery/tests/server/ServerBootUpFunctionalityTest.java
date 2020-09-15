package de.antibotdeluxe.delivery.tests.server;

import de.antibotdeluxe.delivery.server.DeliveryServer;
import de.antibotdeluxe.delivery.tests.packets.TestJaneDoePacket;
import de.antibotdeluxe.delivery.tests.packets.TestPacketVault;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if the {@link de.antibotdeluxe.delivery.server.DeliveryServer} will boot up properly and function without any problems.
 *
 * @author jhz
 */
public class ServerBootUpFunctionalityTest {

    private DeliveryServer server;

    /**
     * Initializing the test environment.
     */
    @Before
    public void before() {
        System.out.println("\n[Test] Initializing ServerBootUpFunctionalityTest..");
        this.server = new DeliveryServer(1337, new TestPacketVault());
    }

    /**
     * Cleaning up the test environment.
     */
    @After
    public void after() {
        System.out.println("[Test] Closing ServerBootUpFunctionalityTest..");
        this.server.endServer();
    }

    /**
     * Test if the {@link DeliveryServer} boots up properly.
     */
    @Test
    public void testServerBootUpFunctionality() {
        try {
            new DeliveryServer(1337, new TestPacketVault());
            Assert.fail("The port should be blocked! {port: '" + server.getPort() + "'}");
        } catch (Exception ignored) { }
        System.out.println("[Test] Running ServerBootUpFunctionalityTest..");
        try {
            this.server.runServer();
            Assert.assertEquals("This packet id was not expected", 0, this.server.getPacketVault().getPacketIdByClass(TestJaneDoePacket.class));
        } catch (Exception exception) {
            exception.printStackTrace();
            Assert.fail("Server could not boot!");
        }
        System.out.println("[Test] No Conflicts running ServerBootUpFunctionalityTest..");
    }

}
