package org.mule.tooling.runtime.launcher.configuration;


import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.lang.StringUtils;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.idea.maven.dom.MavenVersionComparable;
import org.json.JSONObject;
import org.mule.tooling.runtime.launcher.configuration.runner.MuleRunnerCommandLineState;
import org.mule.tooling.runtime.launcher.configuration.ui.MuleRunnerEditor;
import org.mule.tooling.runtime.sdk.MuleSdk;
import org.mule.tooling.runtime.sdk.MuleSdkManager;
import org.mule.tooling.runtime.sdk.MuleSdkManagerStore;
import org.mule.tooling.runtime.util.MuleModuleUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class MuleConfiguration extends ModuleBasedConfiguration implements ModuleRunProfile, RunConfigurationWithSuppressedDefaultDebugAction {

    public static final String PREFIX = "MuleESBConfig-";
    public static final String VM_ARGS_FIELD = PREFIX + "VmArgs";
    public static final String MULE_HOME_FIELD = PREFIX + "MuleHome";
    public static final String CLEAR_DATA_FIELD = PREFIX + "ClearData";
    public static final String USE_CONTAINER_FOR_DEPLOY_FIELD = PREFIX + "UseContainerForDeploy";

    private String vmArgs;
    private String muleHome;
    private String clearData;
    private boolean deployInContainer;

    private Module[] modules = new Module[]{};

    private Project project;
    private String port = "6666";

    protected MuleConfiguration(String name, @NotNull ConfigurationFactory factory, Project project) {
        //super(name, new JavaRunConfigurationModule(project, true), factory);
        super(name, new MuleRunConfigurationModule(project, true), factory);
        this.project = project;
    }


    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new MuleRunnerEditor(this);
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        return new MuleRunnerCommandLineState(executionEnvironment, this);
    }

    @Override
    public void readExternal(Element element) throws InvalidDataException {
        super.readExternal(element);
        this.vmArgs = JDOMExternalizerUtil.readField(element, VM_ARGS_FIELD);
        this.muleHome = JDOMExternalizerUtil.readField(element, MULE_HOME_FIELD);
        this.clearData = JDOMExternalizerUtil.readField(element, CLEAR_DATA_FIELD);
        this.deployInContainer = Boolean.valueOf(JDOMExternalizerUtil.readField(element, USE_CONTAINER_FOR_DEPLOY_FIELD));

        getConfigurationModule().readExternal(element);
    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);
        // Stores the values of this class into the parent
        JDOMExternalizerUtil.writeField(element, VM_ARGS_FIELD, this.getVmArgs());
        JDOMExternalizerUtil.writeField(element, MULE_HOME_FIELD, this.getMuleHome());
        JDOMExternalizerUtil.writeField(element, CLEAR_DATA_FIELD, this.getClearData());
        JDOMExternalizerUtil.writeField(element, USE_CONTAINER_FOR_DEPLOY_FIELD, Boolean.toString(this.isDeployInContainer()));

        getConfigurationModule().writeExternal(element);
    }

    @Override
    public Collection<Module> getValidModules() {
        //TODO - Filter to only include Mule App modules, not domains or anything else
        return Arrays.asList(ModuleManager.getInstance(project).getModules());
    }


    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
        final String muleHome = getMuleHome();
        if (StringUtils.isBlank(muleHome)) {
            throw new RuntimeConfigurationException("Please select Mule Runtime version");
        }
        if (!new File(muleHome).exists()) {
            throw new RuntimeConfigurationException("Mule Runtime does not exist : " + muleHome);
        }

        if (getModules().length < 1) {
            throw new RuntimeConfigurationException("Please select at least one module from the list");
        }

        MuleSdk sdk = MuleSdkManagerStore.getInstance().findSdk(muleHome);
        if (sdk != null) {
            String sdkVersion = sdk.getVersion();

            for (Module m : getModules()) {
                if (m != null) {
                    VirtualFile jsonArtifact = MuleModuleUtils.getMuleArtifactJson(m);
                    if (jsonArtifact != null) {
                        try {
                            String jsonString = new String(jsonArtifact.contentsToByteArray());
                            JSONObject jsonObject = new JSONObject(jsonString);
                            String minVersion = jsonObject.getString("minMuleVersion");

                            //TODO Add quickfix to change the minMuleVersion in mule-artifact.json???
                            if (new MavenVersionComparable(minVersion).compareTo(new MavenVersionComparable(sdkVersion)) > 0) {
                                throw new RuntimeConfigurationWarning("Selected Mule Runtime version " + sdkVersion +
                                        " is older than minimum version " + minVersion +
                                        " required by the module " + m.getName());
                            }

                            //TODO - how to check if the SDK is EE or CE?
                        } catch (IOException ioe) {

                        }
                    }
                }
            }
        }
        super.checkConfiguration();
    }

    @Nullable
    public String getVmArgs() {
        return vmArgs;
    }

    @Nullable
    public String getMuleHome() {
        return muleHome;
    }

    public void setVmArgs(@Nullable String vmArgs) {
        this.vmArgs = vmArgs;
    }

    public void setMuleHome(@Nullable String muleHome) {
        this.muleHome = muleHome;
    }

    public boolean isDeployInContainer() {
        return deployInContainer;
    }

    public void setDeployInContainer(boolean deployInContainer) {
        this.deployInContainer = deployInContainer;
    }

    @Nullable
    public String getClearData() {
        return clearData;
    }

    public void setClearData(@Nullable String clearData) {
        this.clearData = clearData;
    }

    @NotNull
    @Override
    public Module[] getModules() {
        MuleRunConfigurationModule configurationModule = (MuleRunConfigurationModule) this.getConfigurationModule();
        return configurationModule.getModules();
    }

    public void setModules(Module[] modules) {
        MuleRunConfigurationModule configurationModule = (MuleRunConfigurationModule) this.getConfigurationModule();
        configurationModule.setModules(modules);
    }

    public String getDebugPort() {
        return port;
    }

    public void setDebugPort(String port) {

        this.port = port;
    }
}
