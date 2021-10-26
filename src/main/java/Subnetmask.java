public class Subnetmask extends IpAddress {

    public Subnetmask(String input) {
        super(input);

        if (this.getFirst() != 255) {
            throw new IllegalArgumentException();
        }

        String binary = this.toBinaryString();

        if (!(binary.startsWith("1") && binary.endsWith("0") && !binary.contains("01"))) {
            throw new IllegalArgumentException("invalid subnetmask: " + binary);
        }
    }

    /**
     * Inverts a valid subnetmask bitwise.
     * @return The inverted subnetmask in ip representation.
     */
    public IpAddress invert() {
        int first = ~this.getFirst() & 255; // cut off leading zeros
        int second = ~this.getSecond() & 255; // cut off leading zeros
        int third = ~this.getThird() & 255; // cut off leading zeros
        int fourth = ~this.getFourth() & 255; // cut off leading zeros

        return new IpAddress(first, second, third, fourth);
    }
}
