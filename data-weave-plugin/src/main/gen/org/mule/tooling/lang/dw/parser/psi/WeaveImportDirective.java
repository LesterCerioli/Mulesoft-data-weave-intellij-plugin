// This is a generated file. Not intended for manual editing.
package org.mule.tooling.lang.dw.parser.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface WeaveImportDirective extends WeaveDirective {

  @NotNull
  List<WeaveAnnotation> getAnnotationList();

  @Nullable
  WeaveFqnIdentifier getFqnIdentifier();

  @Nullable
  WeaveIdentifier getIdentifier();

  @NotNull
  List<WeaveImportedElement> getImportedElementList();

}
