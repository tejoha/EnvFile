package net.ashald.envfile;

import org.jetbrains.annotations.NotNull;


public interface EnvVarsProviderFactory {

    @NotNull
    EnvVarsProvider createProvider(boolean shouldSubstituteEnvVar, boolean setIpEnable, String selectedNetworkInterface);

    @NotNull String getTitle();

}
