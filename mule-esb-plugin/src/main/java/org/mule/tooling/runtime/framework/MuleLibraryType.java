package org.mule.tooling.runtime.framework;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.libraries.LibraryType;
import com.intellij.openapi.roots.libraries.NewLibraryConfiguration;
import com.intellij.openapi.roots.libraries.ui.LibraryEditorComponent;
import com.intellij.openapi.roots.libraries.ui.LibraryPropertiesEditor;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mule.tooling.runtime.RuntimeIcons;
import org.mule.tooling.runtime.sdk.MuleSdk;
import org.mule.tooling.runtime.sdk.ui.MuleSdkSelectionDialog;

import javax.swing.*;

public class MuleLibraryType extends LibraryType<MuleLibraryProperties>
{


    public MuleLibraryType()
    {
        super(MuleLibraryKind.MULE_LIBRARY_KIND);
    }

    public static MuleLibraryType getInstance()
    {
        return (MuleLibraryType) LibraryType.findByKind(MuleLibraryKind.MULE_LIBRARY_KIND);
    }

    @Nullable
    @Override
    public Icon getIcon(@Nullable MuleLibraryProperties properties)
    {
        return RuntimeIcons.MuleRunConfigIcon;
    }

    @Nullable
    @Override
    public String getCreateActionName()
    {
        return "Mule SDK";
    }

    @Nullable
    @Override
    public NewLibraryConfiguration createNewLibrary(@NotNull JComponent parentComponent, @Nullable VirtualFile virtualFile, @NotNull Project project)
    {
        final MuleSdk muleSdk = new MuleSdkSelectionDialog(parentComponent).open();
        if (muleSdk == null)
        {
            return null;
        }
        return new MuleLibraryDescription.MuleLibraryConfiguration(muleSdk);
    }

    @Nullable
    @Override
    public LibraryPropertiesEditor createPropertiesEditor(@NotNull LibraryEditorComponent<MuleLibraryProperties> libraryEditorComponent)
    {
        return null;
    }
}
