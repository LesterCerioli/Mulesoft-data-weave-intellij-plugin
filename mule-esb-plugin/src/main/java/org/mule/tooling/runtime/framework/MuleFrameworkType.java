package org.mule.tooling.runtime.framework;

import com.intellij.framework.FrameworkType;
import com.intellij.framework.FrameworkTypeEx;
import com.intellij.framework.addSupport.FrameworkSupportInModuleProvider;
import org.jetbrains.annotations.NotNull;
import org.mule.tooling.runtime.RuntimeIcons;

import javax.swing.*;

public class MuleFrameworkType extends FrameworkTypeEx {

    public static final String MULE_FRAMEWORK_NAME = "Mule Framework";
    public static final String MULE_FRAMEWORK_ID = "Mule";

    public MuleFrameworkType() {
        super(MULE_FRAMEWORK_ID);
    }

    public static FrameworkType getFrameworkType()
    {
        return new MuleFrameworkType();
    }

    @NotNull
    @Override
    public FrameworkSupportInModuleProvider createProvider() {
        return new MuleFrameworkSupportProvider();
    }

    @NotNull
    @Override
    public String getPresentableName() {
        return MULE_FRAMEWORK_NAME;
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return RuntimeIcons.MuleRunConfigIcon;
    }



}
