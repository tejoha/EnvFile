package net.ashald.envfile.providers.yaml;

import net.ashald.envfile.EnvVarsProvider;
import net.ashald.envfile.EnvVarsProviderFactory;
import org.jetbrains.annotations.NotNull;

public class YamlEnvFileParserFactory implements EnvVarsProviderFactory {

    @Override
    public @NotNull
    EnvVarsProvider createProvider(boolean shouldSubstituteEnvVar, boolean setIpEnable, String selectedNetworkInterface) {
        return new YamlEnvFileParser(shouldSubstituteEnvVar, setIpEnable, selectedNetworkInterface);
    }

    @NotNull
    public String getTitle() {
        return "JSON/YAML";
    }

}
