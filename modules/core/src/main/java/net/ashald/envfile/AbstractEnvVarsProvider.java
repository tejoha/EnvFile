package net.ashald.envfile;

import org.apache.commons.text.StringSubstitutor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class AbstractEnvVarsProvider implements EnvVarsProvider {

    private boolean isEnvVarSubstitutionEnabled;

    private boolean setIpEnable;
    private String selectedNetworkInterface;

    public AbstractEnvVarsProvider(boolean shouldSubstituteEnvVar, boolean setIpEnable, String selectedNetworkInterface) {
        this.isEnvVarSubstitutionEnabled = shouldSubstituteEnvVar;
        this.setIpEnable = setIpEnable;
        this.selectedNetworkInterface = selectedNetworkInterface;
    }

    @NotNull
    protected abstract Map<String, String> getEnvVars(@NotNull Map<String, String> runConfigEnv, String path) throws EnvFileErrorException, IOException;

    @Override
    public boolean isEditable() {
        return true;
    }

    @NotNull
    @Override
    public Map<String, String> process(@NotNull Map<String, String> runConfigEnv, String path, @NotNull Map<String, String> aggregatedEnv) throws EnvFileErrorException, IOException {
        Map<String, String> result = new HashMap<>(aggregatedEnv);
        Map<String, String> overrides = getEnvVars(runConfigEnv, path);

        for (Map.Entry<String, String> entry : overrides.entrySet()) {
            result.put(entry.getKey(), renderValue(entry.getValue(), result));
        }

        return result;
    }

    @NotNull
    private String renderValue(String template, @NotNull Map<String, String> context) {
        if (setIpEnable) {
            if (isIpValue(template)) {
                try {
                    template = NetworkInterfaceProvider.getInstance().getIpv4(selectedNetworkInterface);
                } catch (NoSuchElementException e) {
                    template = getDefaultIpValue(template);
                }
            }
        } else if (isIpValue(template)){
            template = getDefaultIpValue(template);
        }

        if (!isEnvVarSubstitutionEnabled) {
            return template;
        }
        // resolve taking into account default values
        String stage1 = new StringSubstitutor(context).replace(template);
        // if ${FOO} was not resolved - replace it with empty string as it would've worked in bash
        String stage2 = new StringSubstitutor(key -> context.getOrDefault(key, "")).replace(stage1);

        return stage2;
    }

    private boolean isIpValue(@NotNull String value) {
        return value.toLowerCase().startsWith("{{ip|") && value.toLowerCase().endsWith("}}");
    }

    private String getDefaultIpValue(@NotNull String value) {
        return value.toLowerCase().replace("{{ip|", "").replace("}}","");
    }
}
