// This is a generated file. Not intended for manual editing.
package org.mule.tooling.lang.dw.parser.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.mule.tooling.lang.dw.parser.psi.WeaveTypes.*;
import org.mule.tooling.lang.dw.parser.psi.WeaveStringLiteralMixin;
import org.mule.tooling.lang.dw.parser.psi.*;

public class WeaveStringLiteralImpl extends WeaveStringLiteralMixin implements WeaveStringLiteral {

  public WeaveStringLiteralImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull WeaveVisitor visitor) {
    visitor.visitStringLiteral(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof WeaveVisitor) accept((WeaveVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getBacktikedQuotedString() {
    return findChildByType(BACKTIKED_QUOTED_STRING);
  }

  @Override
  @Nullable
  public PsiElement getDoubleQuotedString() {
    return findChildByType(DOUBLE_QUOTED_STRING);
  }

  @Override
  @Nullable
  public PsiElement getSingleQuotedString() {
    return findChildByType(SINGLE_QUOTED_STRING);
  }

  @Override
  @NotNull
  public String getValue() {
    return WeavePsiImplUtils.getValue(this);
  }

}
