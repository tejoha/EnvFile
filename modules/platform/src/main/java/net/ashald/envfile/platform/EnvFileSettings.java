package net.ashald.envfile.platform;

import java.util.Collections;
import java.util.List;

public class EnvFileSettings {

    private final boolean pluginEnabled;
    private final boolean envVarsSubstitutionEnabled;
    private final boolean pathMacroSupported;
    private final boolean ignoreMissing;
    private final List<EnvFileEntry> entries;

    private final boolean setIpEnable;
    private final String selectedNetworkInterface;


    public EnvFileSettings(boolean isEnabled, boolean substituteVars, boolean pathMacroSupported,
                           List<EnvFileEntry> envFileEntries, boolean ignoreMissing,
                           boolean setIpEnable, String selectedNetworkInterface) {
        pluginEnabled = isEnabled;
        envVarsSubstitutionEnabled = substituteVars;
        this.pathMacroSupported = pathMacroSupported;
        this.ignoreMissing = ignoreMissing;
        entries = envFileEntries;

        this.setIpEnable = setIpEnable;
        this.selectedNetworkInterface = selectedNetworkInterface;
    }

    public boolean isEnabled() {
        return pluginEnabled;
    }

    public boolean isSubstituteEnvVarsEnabled() {
        return envVarsSubstitutionEnabled;
    }

    public boolean isPathMacroSupported() {
        return pathMacroSupported;
    }

    public boolean isIgnoreMissing() {
        return ignoreMissing;
    }

    public List<EnvFileEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public boolean isSetIpEnable() {
        return setIpEnable;
    }

    public String getSelectedNetworkInterface() {
        return selectedNetworkInterface;
    }
}
