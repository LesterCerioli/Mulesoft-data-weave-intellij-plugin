// This is a generated file. Not intended for manual editing.
package org.mule.tooling.lang.dw.parser.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface WeaveBinaryExpression extends WeaveExpression, WeaveNamedElement {

  @NotNull
  List<WeaveExpression> getExpressionList();

  @NotNull
  WeaveIdentifier getIdentifier();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

  @Nullable
  WeaveExpression getLeft();

  @Nullable
  WeaveExpression getRight();

}
