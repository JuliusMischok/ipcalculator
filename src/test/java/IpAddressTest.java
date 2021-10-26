import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IpAddressTest {

    @Test
    public void testComponentRangeFirst() {
        IpAddress ip = new IpAddress();

        ip.setFirst(230);
        assertEquals(230, ip.getFirst());

        try {
            ip.setFirst(-1);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("'first'"));
        }

        try {
            ip.setFirst(256);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("'first'"));
        }
    }

    @Test
    public void testComponentRangeThird() {
        IpAddress ip = new IpAddress();

        try {
            ip.setThird(Integer.MIN_VALUE);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("'third'"));
        }

        try {
            ip.setThird(Integer.MAX_VALUE);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("'third'"));
        }
    }

    @Test
    public void testParseIpAddressFailures() {
        assertThrows(IllegalArgumentException.class, () -> new IpAddress(null));
        assertThrows(IllegalArgumentException.class, () -> new IpAddress(""));
        assertThrows(IllegalArgumentException.class, () -> new IpAddress("192.168.2"));
        assertThrows(IllegalArgumentException.class, () -> new IpAddress("192.168.2.1."));
        assertThrows(IllegalArgumentException.class, () -> new IpAddress("192/168/2/1"));
        assertThrows(IllegalArgumentException.class, () -> new IpAddress("192.xyz.2.1."));
        assertThrows(IllegalArgumentException.class, () -> new IpAddress("192.-1.2.1"));
        assertThrows(IllegalArgumentException.class, () -> new IpAddress("256.0.0.1"));
    }

    @Test
    public void testParseIpAddressSuccess() {
        IpAddress ip = new IpAddress("192.168.2.1");

        assertEquals(192, ip.getFirst());
        assertEquals(168, ip.getSecond());
        assertEquals(2, ip.getThird());
        assertEquals(1, ip.getFourth());
    }

    @Test
    public void testParseSubnetmaskFailure() {
        assertThrows(IllegalArgumentException.class, () -> new Subnetmask("0.0.0.0"));
        assertThrows(IllegalArgumentException.class, () -> new Subnetmask("255.191.0.0"));
        assertThrows(IllegalArgumentException.class, () -> new Subnetmask("255.255.0.1"));
        assertThrows(IllegalArgumentException.class, () -> new Subnetmask("255.255.1.0"));
    }

    @Test
    public void testToBinaryString() {
        assertEquals("11111111111111110000000000000000", new Subnetmask("255.255.0.0").toBinaryString());
    }

    @Test
    public void testCalculateNetId() {
        IpAddress ip = new IpAddress("192.168.1.1");
        Subnetmask snm = new Subnetmask("255.255.0.0");

        assertEquals("192.168.0.0", IpCalculator.calculateNetId(ip, snm).toString());
        assertEquals(new IpAddress("192.168.0.0"), IpCalculator.calculateNetId(ip, snm));
    }

    @Test
    public void testCalculateBroadcastIP() {
        IpAddress ip = new IpAddress("192.134.2.1");
        Subnetmask snm = new Subnetmask("255.192.0.0");

        IpAddress netId = IpCalculator.calculateNetId(ip, snm);

        assertEquals(new IpAddress("192.191.255.255"), IpCalculator.calculateBroadcastIp(netId, snm));
    }
}
