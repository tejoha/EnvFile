package net.ashald.envfile.platform;

import com.intellij.execution.configurations.RunConfigurationBase;
import net.ashald.envfile.EnvFileErrorException;
import net.ashald.envfile.EnvProvider;
import net.ashald.envfile.EnvProviderFactory;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Map;

public abstract class EnvEntry<T extends EnvProvider> {

    private final RunConfigurationBase runConfig;

    private boolean isEnabled;
    private boolean isSubstitutionEnabled;
    private final String parserId;


    public EnvEntry(RunConfigurationBase runConfig, String envFileParserId, boolean enabled, boolean substitutionEnabled) {
        this.runConfig = runConfig;
        parserId = envFileParserId;
        setEnable(enabled);
        setSubstitutionEnabled(substitutionEnabled);
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnable(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isSubstitutionEnabled() {
        return isSubstitutionEnabled;
    }

    public void setSubstitutionEnabled(boolean substitutionEnabled) {
        isSubstitutionEnabled = substitutionEnabled;
    }

    public RunConfigurationBase getRunConfig() {
        return runConfig;
    }

    public String getParserId() {
        return parserId;
    }

    public abstract Map<String, String> process(Map<String, String> runConfigEnv, Map<String, String> aggregatedEnv, boolean ignoreMissing) throws IOException, EnvFileErrorException;

    public String getTypeTitle() {
        EnvProviderFactory factory = getProviderFactory();
        return factory == null ? String.format("<%s>", parserId) : factory.getTitle();
    }

    public boolean isEditable() {
        T provider = getProvider();
        return provider != null && getProvider().isEditable();
    }

    @Nullable
    private EnvProviderFactory getProviderFactory() {
        EnvVarsProviderExtension extension = EnvVarsProviderExtension.getParserExtensionById(parserId);
        return extension == null ? null : extension.getFactory();
    }

    @Nullable
    protected T getProvider() {
        EnvProviderFactory factory = getProviderFactory();
        return factory == null ? null : (T) factory.createProvider(isSubstitutionEnabled);
    }

    public boolean validateType() {
        return getProvider() != null;
    }

}
