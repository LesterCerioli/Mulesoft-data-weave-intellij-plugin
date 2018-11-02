package org.mule.tooling.runtime.framework;

import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.LibraryKind;
import com.intellij.openapi.roots.libraries.NewLibraryConfiguration;
import com.intellij.openapi.roots.ui.configuration.libraries.CustomLibraryDescription;
import com.intellij.openapi.roots.ui.configuration.libraryEditor.LibraryEditor;
import com.intellij.openapi.roots.ui.configuration.projectRoot.LibrariesContainer;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.mule.tooling.runtime.sdk.MuleSdk;
import org.mule.tooling.runtime.sdk.ui.MuleSdkSelectionDialog;

import javax.swing.*;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MuleLibraryDescription extends CustomLibraryDescription
{

    private final Set<? extends LibraryKind> myLibraryKinds;

    public MuleLibraryDescription()
    {
        this(Collections.singleton(MuleLibraryKind.MULE_LIBRARY_KIND));
    }

    private MuleLibraryDescription(@NotNull final Set<? extends LibraryKind> libraryKinds)
    {
        myLibraryKinds = libraryKinds;
    }

    @NotNull
    @Override
    public Set<? extends LibraryKind> getSuitableLibraryKinds()
    {
        return myLibraryKinds;
    }


    @Override
    public NewLibraryConfiguration createNewLibrary(@NotNull JComponent parentComponent, VirtualFile contextDirectory)
    {
        final MuleSdk muleSdk = new MuleSdkSelectionDialog(parentComponent).open();
        if (muleSdk == null)
            return null;
        return new MuleLibraryConfiguration(muleSdk);
    }

    @NotNull
    @Override
    public LibrariesContainer.LibraryLevel getDefaultLevel()
    {
        return LibrariesContainer.LibraryLevel.GLOBAL;
    }


    public static class MuleLibraryConfiguration extends NewLibraryConfiguration
    {
        private final MuleSdk muleSdk;

        public MuleLibraryConfiguration(MuleSdk muleSdk)
        {
            super(MuleLibraryKind.MULE_LIBRARY_KIND_ID + "-" + muleSdk.getVersion(), MuleLibraryType.getInstance(), new MuleLibraryProperties(muleSdk));
            this.muleSdk = muleSdk;
        }

        @Override
        public void addRoots(@NotNull LibraryEditor editor)
        {
            final List<File> libs = muleSdk.getLibraryEntries();
            for (File file : libs)
            {
                editor.addRoot(VfsUtil.getUrlForLibraryRoot(file), OrderRootType.CLASSES);
            }
        }

    }
}
