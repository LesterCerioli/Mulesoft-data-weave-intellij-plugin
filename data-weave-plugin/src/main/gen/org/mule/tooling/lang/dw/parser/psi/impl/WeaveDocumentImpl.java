// This is a generated file. Not intended for manual editing.
package org.mule.tooling.lang.dw.parser.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.mule.tooling.lang.dw.parser.psi.WeaveTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.mule.tooling.lang.dw.parser.psi.*;
import com.intellij.navigation.ItemPresentation;

public class WeaveDocumentImpl extends ASTWrapperPsiElement implements WeaveDocument {

  public WeaveDocumentImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull WeaveVisitor visitor) {
    visitor.visitDocument(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof WeaveVisitor) accept((WeaveVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public WeaveBody getBody() {
    return findChildByClass(WeaveBody.class);
  }

  @Override
  @Nullable
  public WeaveHeader getHeader() {
    return findChildByClass(WeaveHeader.class);
  }

  public ItemPresentation getPresentation() {
    return WeavePsiImplUtils.getPresentation(this);
  }

  @Nullable
  public String getQualifiedName() {
    return WeavePsiImplUtils.getQualifiedName(this);
  }

  @Nullable
  public String getName() {
    return WeavePsiImplUtils.getName(this);
  }

  public WeaveDocument setName(String name) {
    return WeavePsiImplUtils.setName(this, name);
  }

}
