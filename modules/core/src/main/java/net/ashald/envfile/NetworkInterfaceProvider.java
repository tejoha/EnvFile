package net.ashald.envfile;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkInterfaceProvider {

    private final static String IPv4_PATTERN = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";

    public static NetworkInterfaceProvider getInstance() {
        return new NetworkInterfaceProvider();
    }

    public String getIpv4(String interfaceName) throws NoSuchElementException {
        List<InetAddress> inetAddresses;
        try {
            inetAddresses = getIpv4Addresses(interfaceName);
        } catch (Exception e) {
            throw new NoSuchElementException("Found no Network Interface: " + interfaceName);
        }
        if (inetAddresses.size() == 0)
            throw new NoSuchElementException("Found no Ip v4 Address for Network Interface: " + interfaceName);

        return inetAddresses.get(0).toString().substring(1);
    }

    public Map<String, String> getNetworkInterfaceWithIp4() {
        Map<String, String> interfacesMap = new HashMap<>();
        try {
            for (Enumeration<NetworkInterface> inter = NetworkInterface.getNetworkInterfaces(); inter.hasMoreElements();) {
                NetworkInterface networkInterface = inter.nextElement();
                List<InetAddress> inetAddresses = getIpv4Addresses(networkInterface);
                if (inetAddresses.size() > 0)
                    interfacesMap.put(networkInterface.getName(), networkInterface.getDisplayName());
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return interfacesMap;
    }

    private List<InetAddress> getIpv4Addresses(String interfaceName) throws SocketException {
        NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);
        return getIpv4Addresses(networkInterface);
    }

    private List<InetAddress> getIpv4Addresses(NetworkInterface networkInterface) {
        List<InetAddress> networkInterfaces = new ArrayList<>();

        for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
            InetAddress inetAddress = enumIpAddr.nextElement();
            String ip = inetAddress.toString().substring(1);
            Pattern VALID_IPV4_PATTERN = Pattern.compile(IPv4_PATTERN, Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_IPV4_PATTERN.matcher(ip);
            if (matcher.matches() && !ip.equals("127.0.0.1"))
                networkInterfaces.add(inetAddress);
        }
        return networkInterfaces;
    }
}
