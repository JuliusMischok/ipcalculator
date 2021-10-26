import java.util.Objects;
import java.util.Scanner;

public class IpCalculator {
    public static void main(String[] args) {
        IpAddress ip = askForIpAddress();
        Subnetmask snm = askForSubnetmask();

        System.out.println("Eingegebene IP: " + ip.toString());
        System.out.println("Eingegebene SNM: " + snm.toString());

        System.out.println("");

        System.out.println("IP (bin) : " + ip.toBinaryString());
        System.out.println("SNM (bin): " + snm.toBinaryString());

        System.out.println("");

        IpAddress netId = calculateNetId(ip, snm);
        System.out.println("Netz-ID: " + netId);
        System.out.println("Broadcast-IP: " + calculateBroadcastIp(netId, snm));
    }

    public static IpAddress calculateNetId(IpAddress ip, Subnetmask snm) {
        Objects.requireNonNull(ip);
        Objects.requireNonNull(snm);

        return ip.logicalAnd(snm);
    }

    private static IpAddress askForIpAddress() {
        System.out.println("Bitte IP Adresse eingeben: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        try {
            return new IpAddress(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Ungültige Eingabe, bitte wiederholen!");
            return askForIpAddress();
        }
    }

    private static Subnetmask askForSubnetmask() {
        System.out.println("Bitte Subnetzmaske eingeben: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        try {
            return new Subnetmask(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Ungültige Eingabe, bitte wiederholen!");
            return askForSubnetmask();
        }
    }

    public static IpAddress calculateBroadcastIp(IpAddress netId, Subnetmask subnetmask) {
        Objects.requireNonNull(netId);
        Objects.requireNonNull(subnetmask);

        IpAddress invertedSnm = subnetmask.invert();

        int first = netId.getFirst() + invertedSnm.getFirst();
        int second = netId.getSecond() + invertedSnm.getSecond();
        int third = netId.getThird() + invertedSnm.getThird();
        int fourth = netId.getFourth() + invertedSnm.getFourth();

        return new IpAddress(first, second, third, fourth);
    }
}
