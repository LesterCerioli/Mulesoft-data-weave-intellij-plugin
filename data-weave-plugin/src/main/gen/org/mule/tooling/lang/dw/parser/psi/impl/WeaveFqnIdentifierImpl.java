// This is a generated file. Not intended for manual editing.
package org.mule.tooling.lang.dw.parser.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.mule.tooling.lang.dw.parser.psi.WeaveTypes.*;
import org.mule.tooling.lang.dw.parser.psi.WeaveNamedElementImpl;
import org.mule.tooling.lang.dw.parser.psi.*;
import com.intellij.psi.PsiReference;

public class WeaveFqnIdentifierImpl extends WeaveNamedElementImpl implements WeaveFqnIdentifier {

  public WeaveFqnIdentifierImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull WeaveVisitor visitor) {
    visitor.visitFqnIdentifier(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof WeaveVisitor) accept((WeaveVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public WeaveContainerModuleIdentifier getContainerModuleIdentifier() {
    return findNotNullChildByClass(WeaveContainerModuleIdentifier.class);
  }

  @Override
  @Nullable
  public WeaveCustomLoader getCustomLoader() {
    return findChildByClass(WeaveCustomLoader.class);
  }

  @Override
  @NotNull
  public WeaveIdentifier getIdentifier() {
    return findNotNullChildByClass(WeaveIdentifier.class);
  }

  @Override
  public PsiReference[] getReferences() {
    return WeavePsiImplUtils.getReferences(this);
  }

  @Override
  public String getModuleFQN() {
    return WeavePsiImplUtils.getModuleFQN(this);
  }

  @Override
  public String getPath() {
    return WeavePsiImplUtils.getPath(this);
  }

}
