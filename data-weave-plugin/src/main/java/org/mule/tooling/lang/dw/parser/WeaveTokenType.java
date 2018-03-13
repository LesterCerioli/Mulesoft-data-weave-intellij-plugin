package org.mule.tooling.lang.dw.parser;


import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.mule.tooling.lang.dw.WeaveLanguage;

public class WeaveTokenType extends IElementType {
  public WeaveTokenType(@NotNull @NonNls String debugName) {
    super(debugName, WeaveLanguage.getInstance());
  }
}
