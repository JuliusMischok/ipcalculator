import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class IpAddress {
    private int first;
    private int second;
    private int third;
    private int fourth;

    public IpAddress() {}

    public IpAddress(String input) {
        if (input == null || input.strip().equals("") || !input.contains(".") || input.startsWith(".") || input.endsWith(".")) {
            throw new IllegalArgumentException();
        }

        List<String> parts = Arrays.asList(input.split("\\."));

        if (parts.size() != 4) {
            throw new IllegalArgumentException();
        }

        List<Integer> components = new ArrayList<>();
        for (String part : parts) {
            components.add(Integer.parseInt(part));
        }

        /*
        List<Integer> components = parts.stream()
                .map(x -> Integer.parseInt(x))
                .collect(Collectors.toList());
        */

        this.setFirst(components.get(0));
        this.setSecond(components.get(1));
        this.setThird(components.get(2));
        this.setFourth(components.get(3));
    }

    public IpAddress(int first, int second, int third, int fourth) {
        this.setFirst(first);
        this.setSecond(second);
        this.setThird(third);
        this.setFourth(fourth);
    }

    public void setFirst(int paramFirst) {
        validateComponentRange(paramFirst, "first");

        this.first = paramFirst;
    }

    public int getFirst() {
        return this.first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        validateComponentRange(second, "second");

        this.second = second;
    }

    public int getThird() {
        return third;
    }

    public void setThird(int third) {
        validateComponentRange(third, "third");

        this.third = third;
    }

    public int getFourth() {
        return fourth;
    }

    public void setFourth(int fourth) {
        validateComponentRange(fourth, "fourth");

        this.fourth = fourth;
    }

    private void validateComponentRange(int param, String componentName) {
        if (param < 0 || param > 255) {
            throw new IllegalArgumentException("component '" + componentName + "' out of range");
        }
    }

    @Override
    public String toString() {
        return this.getFirst() + "." + this.getSecond() + "." + this.getThird() + "." + this.getFourth();
    }

    public String toBinaryString() {
        String result = "";

        result += componentToBinaryString(this.getFirst());
        result += componentToBinaryString(this.getSecond());
        result += componentToBinaryString(this.getThird());
        result += componentToBinaryString(this.getFourth());

        return result;
    }

    private String componentToBinaryString(int component) {
        String result = Integer.toBinaryString(component);

        while (result.length() <= 7) {
            result = "0" + result;
        }

        return result;
    }

    @Override
    public boolean equals(Object another) {
        if (another == null) {
            return false;
        } else if (!(another instanceof IpAddress)) {
            return false;
        } else {
            IpAddress otherIp = (IpAddress)another;

            return
                    this.getFirst() == otherIp.getFirst() &&
                    this.getSecond() == otherIp.getSecond() &&
                    this.getThird() == otherIp.getThird() &&
                    this.getFourth() == otherIp.getFourth();
        }
    }

    public IpAddress logicalAnd(IpAddress another) {
        Objects.requireNonNull(another);

        int first = this.getFirst() & another.getFirst();
        int second = this.getSecond() & another.getSecond();
        int third = this.getThird() & another.getThird();
        int fourth = this.getFourth() & another.getFourth();

        IpAddress result = new IpAddress();
        result.setFirst(first);
        result.setSecond(second);
        result.setThird(third);
        result.setFourth(fourth);

        return result;
    }
}
