package net.ashald.envfile.providers.dotenv;

import net.ashald.envfile.EnvVarsProvider;
import net.ashald.envfile.EnvVarsProviderFactory;
import org.jetbrains.annotations.NotNull;

public class DotEnvFileParserFactory implements EnvVarsProviderFactory {

    @Override
    public @NotNull
    EnvVarsProvider createProvider(boolean shouldSubstituteEnvVar, boolean setIpEnable, String selectedNetworkInterface) {
        return new DotEnvFileParser(shouldSubstituteEnvVar, setIpEnable, selectedNetworkInterface);
    }

    @Override
    public @NotNull String getTitle() {
        return ".env";
    }

}
