package de.antibotdeluxe.delivery.tests.event;

import de.antibotdeluxe.delivery.codec.DeliveryPacket;
import de.antibotdeluxe.delivery.event.DeliveryEventHandler;
import de.antibotdeluxe.delivery.event.DeliveryEventManager;
import de.antibotdeluxe.delivery.tests.event.packets.JaneDoePacket;
import de.antibotdeluxe.delivery.tests.event.packets.LisaEmberPacket;
import io.netty.channel.ChannelHandlerContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test if the <code>event system</code> functions properly.
 *
 * @author jhz
 */

public class EventTest {

    private DeliveryEventManager eventManager;
    private String resultOne;
    private String resultTwo;
    private String resultThree;

    /**
     * Initializing the test environment.
     */
    @Before
    public void before() {
        System.out.println("\n[Test] Initializing EventTest..");
        this.eventManager = new DeliveryEventManager();
        eventManager.bindHandlers(new DeliveryEventHandler("[TestHandler-001]") {
            @Override
            public void call(DeliveryPacket typeOf, ChannelHandlerContext ctx) {
                JaneDoePacket packet = (JaneDoePacket) typeOf;
                resultOne = packet.sayName();
                System.out.println("[Test] TestHandler-001 fired");
            }

            @Override
            public Class<? extends DeliveryPacket> getPacketCapture() {
                return JaneDoePacket.class;
            }
        });

        eventManager.bindHandlers(new DeliveryEventHandler("[TestHandler-002]") {
            @Override
            public void call(DeliveryPacket typeOf, ChannelHandlerContext ctx) {
                LisaEmberPacket packet = (LisaEmberPacket) typeOf;
                resultTwo = packet.sayName();
                System.out.println("[Test] TestHandler-002 fired");
            }

            @Override
            public Class<? extends DeliveryPacket> getPacketCapture() {
                return LisaEmberPacket.class;
            }
        });

        eventManager.bindHandlers(new DeliveryEventHandler("[TestHandler-003]") {
            @Override
            public void call(DeliveryPacket typeOf, ChannelHandlerContext ctx) {
                resultThree = "Global";
                System.out.println("[Test] TestHandler-003 fired");
            }

            @Override
            public Class<? extends DeliveryPacket> getPacketCapture() {
            return DeliveryPacket.class;
          }
        });

        eventManager.bindHandlers(new DeliveryEventHandler("[TestHandler-004]") {
            @Override
            public void call(DeliveryPacket typeOf, ChannelHandlerContext ctx) {
                JaneDoePacket packet = (JaneDoePacket) typeOf;
                resultOne = packet.sayName();
                System.out.println("[Test] TestHandler-004 fired");
            }

            @Override
            public Class<? extends DeliveryPacket> getPacketCapture() {
                return JaneDoePacket.class;
            }
        });
    }

    /**
     * Closing the test environment.
     */
    @After
    public void after() { }

    /**
     * Using the test environment to actually test the <code>event system</code>.
     */
    @Test
    public void checkEventSystemFunctionality() {
        System.out.println("[Test] Running EventTest..");
        eventManager.callHandlers(new JaneDoePacket(), null);
        eventManager.callHandlers(new LisaEmberPacket(), null);
        Assert.assertEquals("The result was not 'Jane Doe'", "Jane Doe", resultOne);
        Assert.assertEquals("The result was not 'Lisa Ember'", "Lisa Ember", resultTwo);
        Assert.assertEquals("The result was not 'Global'", "Global", resultThree);
        System.out.println("[Test] No conflicts running EventTest..");
    }

}
