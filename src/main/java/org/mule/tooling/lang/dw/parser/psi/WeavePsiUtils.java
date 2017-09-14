package org.mule.tooling.lang.dw.parser.psi;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xdebugger.XDebuggerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mule.tooling.lang.dw.WeaveFile;

import java.util.*;
import java.util.stream.Collectors;


public class WeavePsiUtils {

  public static List<IElementType> KeyWordsToken = Arrays.asList(WeaveTypes.IF, WeaveTypes.CASE_KEYWORD, WeaveTypes.IS, WeaveTypes.AS, WeaveTypes.UNLESS, WeaveTypes.ELSE, WeaveTypes.USING, WeaveTypes.DEFAULT, WeaveTypes.MATCHES_KEYWORD, WeaveTypes.MATCH_KEYWORD);

  public static List<IElementType> DirectivesToken =
          Arrays.asList(WeaveTypes.INPUT_DIRECTIVE_KEYWORD, WeaveTypes.OUTPUT_DIRECTIVE_KEYWORD, WeaveTypes.NAMESPACE_DIRECTIVE_KEYWORD, WeaveTypes.TYPE_DIRECTIVE_KEYWORD
                  , WeaveTypes.VAR_DIRECTIVE_KEYWORD, WeaveTypes.VERSION_DIRECTIVE_KEYWORD, WeaveTypes.FUNCTION_DIRECTIVE_KEYWORD, WeaveTypes.IMPORT_DIRECTIVE_KEYWORD);

  @NotNull
  public static String stripQuotes(@NotNull String text) {
    if (text.length() > 0) {
      final char firstChar = text.charAt(0);
      final char lastChar = text.charAt(text.length() - 1);
      if (firstChar == '\'' || firstChar == '"') {
        if (text.length() > 1 && firstChar == lastChar && !isEscapedChar(text, text.length() - 1)) {
          return text.substring(1, text.length() - 1);
        }
        return text.substring(1);
      }
    }
    return text;
  }


  @Nullable
  public static PsiElement getFirstWeaveElement(Project project, Document document, int line) {
    final Ref<PsiElement> result = Ref.create();
    XDebuggerUtil.getInstance().iterateLine(project, document, line, element -> {
      if (!(element instanceof PsiWhiteSpace)) {
        result.set(element);
        return false;
      }
      return true;
    });
    return result.get();
  }

  public static boolean isEscapedChar(@NotNull String text, int position) {
    int count = 0;
    for (int i = position - 1; i >= 0 && text.charAt(i) == '\\'; i--) {
      count++;
    }
    return count % 2 != 0;
  }

  @Nullable
  public static WeaveDocument getDocument(@NotNull PsiElement element) {
    PsiElement result = element;
    while (!(result instanceof WeaveDocument) && result != null) {
      result = result.getParent();
    }
    return (WeaveDocument) result;
  }


  public static Optional<WeaveVariable> findVariables(@NotNull PsiElement element, @NotNull final String name) {
    final List<WeaveVariable> variables = findVariables(element);
    final Optional<WeaveVariable> matching = Iterables.tryFind(variables, weaveVariableDefinition -> name.equals(weaveVariableDefinition.getVariableName()));
    return matching;
  }

  public static List<WeaveVariable> findVariables(@NotNull PsiElement element) {
    final List<WeaveVariable> result = new ArrayList<>();
    PsiElement parent = element.getParent();
    while (isNotWeaveFile(parent)) {
      if (parent instanceof WeaveUsingExpression) {
        final List<WeaveVariableDefinition> vars = ((WeaveUsingExpression) parent).getVariableDefinitionList();
        result.addAll(vars);
      }
      if (parent instanceof WeaveDocument) {
        final Collection<WeaveVariableDefinition> vars = PsiTreeUtil.findChildrenOfType(((WeaveDocument) parent).getHeader(), WeaveVariableDefinition.class);
        result.addAll(vars);
      }
      if (parent instanceof WeaveLambdaLiteral) {
        result.addAll(((WeaveLambdaLiteral) parent).getFunctionParameterList());
      }
      if (parent instanceof WeaveFunctionDefinition) {
        result.addAll(((WeaveFunctionDefinition) parent).getFunctionParameterList());
      }
      parent = parent.getParent();
    }

    return result;
  }

  public static List<WeaveNamedElement> findFunctions(@NotNull PsiElement element) {
    final List<WeaveNamedElement> result = new ArrayList<>();
    PsiElement parent = element.getParent();
    while (isNotWeaveFile(parent)) {
      if (parent instanceof WeaveDocument) {
        final Collection<WeaveVariableDefinition> vars = PsiTreeUtil.findChildrenOfType(((WeaveDocument) parent).getHeader(), WeaveVariableDefinition.class);
        final Collection<WeaveFunctionDefinition> functionDirectives = PsiTreeUtil.findChildrenOfType(((WeaveDocument) parent).getHeader(), WeaveFunctionDefinition.class);
        result.addAll(functionDirectives);
        result.addAll(vars.stream().filter(var -> var.getExpression() instanceof WeaveLambdaLiteral).collect(Collectors.toList()));
        break;
      }
      parent = parent.getParent();
    }
    return result;
  }

  @Nullable
  public static Optional<? extends PsiElement> findFunction(PsiElement element, final String functionName) {
    final List<WeaveNamedElement> variables = findFunctions(element);
    final Optional<WeaveNamedElement> matching = Iterables.tryFind(variables, new Predicate<WeaveNamedElement>() {
      @Override
      public boolean apply(WeaveNamedElement weaveVariableDefinition) {
        return functionName.equals(weaveVariableDefinition.getName());
      }
    });
    return matching;
  }

  @Nullable
  public static PsiElement findImplicitVariable(PsiElement myElement) {
    PsiElement parent = myElement.getParent();
    while (isNotWeaveFile(parent)) {
      if (parent instanceof WeaveBinaryExpression) {
        final List<WeaveExpression> expressionList = ((WeaveBinaryExpression) parent).getExpressionList();
        if (expressionList.size() == 2) {
          return expressionList.get(1);
        }
      }
      parent = parent.getParent();
    }
    return null;
  }

  private static boolean isNotWeaveFile(PsiElement parent) {
    return parent != null && !(parent instanceof WeaveFile);
  }
}
