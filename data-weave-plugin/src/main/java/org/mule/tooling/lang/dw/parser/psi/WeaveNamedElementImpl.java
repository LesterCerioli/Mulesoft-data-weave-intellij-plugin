package org.mule.tooling.lang.dw.parser.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public abstract class WeaveNamedElementImpl extends ASTWrapperPsiElement implements WeaveNamedElement {

    public WeaveNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    public String getName() {
        if (getIdentifier() == null) {
            return null;
        } else {
            return getIdentifier().getName();
        }
    }

    public PsiElement setName(@NotNull String newName) {
        ASTNode keyNode = getIdentifier().getNode();
        if (keyNode != null) {
            WeaveIdentifier property = WeaveElementFactory.createIdentifier(getProject(), newName);
            getIdentifier().getParent().getNode().replaceChild(keyNode, property.getNode());
        }
        return this;
    }

    @Override
    public int getTextOffset() {
        PsiElement nameIdentifier = getNameIdentifier();
        if (nameIdentifier != null) {
            return nameIdentifier.getTextOffset();
        } else {
            return super.getTextOffset();
        }
    }

    public PsiElement getNameIdentifier() {
        return getIdentifier();
    }


    public abstract WeaveIdentifier getIdentifier();
}
