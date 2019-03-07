package net.ashald.envfile.platform.ui;

public class NetworkInterfaceItem {

    private String name;
    private String displayName;

    public NetworkInterfaceItem(String name, String displayName)
    {
        this.name = name;
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }

    public String getName()
    {
        return name;
    }

    public String getDisplayName()
    {
        return displayName;
    }
}
