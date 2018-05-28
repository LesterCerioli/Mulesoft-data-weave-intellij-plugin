package org.mule.tooling.runtime.util;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class MuleIcons {
    public static final Icon MuleIcon = IconLoader.findIcon("/mulesoft-icon.png");
    public static final Icon MUnitIcon = IconLoader.findIcon("/munit-icon.png");
    public static final Icon MuleFlow = IconLoader.findIcon("/flow.png");
    public static final Icon MUnitTest = IconLoader.findIcon("/test.png");
    public static final Icon MuleSubFlow = IconLoader.findIcon("/sub-flow.png");
    public static final Icon MuleFileType = IconLoader.findIcon("/mule_type.png");
    public static final Icon MUnitFileType = IconLoader.findIcon("/munit_type.png");
    public static final Icon DataFileType = IconLoader.findIcon("/weave_type.png");
    public static final Icon MelFileType = IconLoader.findIcon("/mel_type.png");
    public static final Icon RamlFileType = IconLoader.findIcon("/raml_type.png");
    //public static final Icon DataWeaveIcon = IconLoader.findIcon("/dataweave.png");
    public static final Icon ConnectorIcon = IconLoader.findIcon("/connector.png");
    public static final Icon MuleRemoteDebugIcon = IconLoader.findIcon("/mule-remote-debug.png");

    private MuleIcons() {
        super();
    }

}