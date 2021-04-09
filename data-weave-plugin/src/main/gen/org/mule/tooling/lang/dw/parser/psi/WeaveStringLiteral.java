// This is a generated file. Not intended for manual editing.
package org.mule.tooling.lang.dw.parser.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface WeaveStringLiteral extends WeaveLiteralExpression {

  @Nullable
  PsiElement getBacktikedQuotedString();

  @Nullable
  PsiElement getDoubleQuotedString();

  @Nullable
  PsiElement getSingleQuotedString();

  @NotNull String getValue();

}
