// This is a generated file. Not intended for manual editing.
package org.mule.tooling.lang.dw.parser.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.mule.tooling.lang.dw.parser.psi.WeaveTypes.*;
import org.mule.tooling.lang.dw.parser.psi.*;

public class WeaveUnionTypeImpl extends WeaveTypeImpl implements WeaveUnionType {

  public WeaveUnionTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull WeaveVisitor visitor) {
    visitor.visitUnionType(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof WeaveVisitor) accept((WeaveVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<WeaveType> getTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, WeaveType.class);
  }

}
