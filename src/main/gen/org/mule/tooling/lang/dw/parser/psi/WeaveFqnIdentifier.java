// This is a generated file. Not intended for manual editing.
package org.mule.tooling.lang.dw.parser.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface WeaveFqnIdentifier extends WeaveNamedElement {

  @Nullable
  WeaveCustomLoader getCustomLoader();

  @NotNull
  WeaveIdentifier getIdentifier();

  @NotNull
  WeaveIdentifierPackage getIdentifierPackage();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

}
