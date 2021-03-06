// This is a generated file. Not intended for manual editing.
package org.mule.tooling.lang.dw.parser.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface WeaveImportedElement extends WeaveNamedElement {

  @NotNull
  WeaveIdentifier getIdentifier();

  @Nullable
  WeaveImportedElementAlias getImportedElementAlias();

  String getName();

  PsiElement setName(@NotNull String newName);

  PsiElement getNameIdentifier();

}
