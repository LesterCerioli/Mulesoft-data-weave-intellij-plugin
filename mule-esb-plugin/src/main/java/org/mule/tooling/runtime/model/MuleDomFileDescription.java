package org.mule.tooling.runtime.model;


import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mule.tooling.runtime.RuntimeIcons;
import org.mule.tooling.runtime.util.MuleConfigUtils;

import javax.swing.*;

public class MuleDomFileDescription extends DomFileDescription<Mule> {
    public MuleDomFileDescription() {
        super(Mule.class, "mule", "http://www.mulesoft.org/schema/mule/core");
    }

    @Override
    public boolean isMyFile(@NotNull XmlFile file, @Nullable Module module) {
        return MuleConfigUtils.isMuleFile(file) && !MuleConfigUtils.isMUnitFile(file);

    }

    @Nullable
    public Icon getFileIcon(@Iconable.IconFlags int flags) {
        return RuntimeIcons.MuleRunConfigIcon;
    }

    @Override
    protected void initializeFileDescription() {
        super.initializeFileDescription();
    }
}
