package org.mule.tooling.lang.dw.injector;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.InjectedLanguagePlaces;
import com.intellij.psi.LanguageInjector;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import org.jetbrains.annotations.NotNull;
import org.mule.tooling.lang.dw.WeaveLanguage;

import javax.xml.namespace.QName;
import java.util.Arrays;
import java.util.List;

public class MuleLanguageInjector implements LanguageInjector {


    public static final String EXPRESSION_PREFIX = "#[";

    public static final String MULE_LOCAL_NAME = "mule";
    public static final String EXPRESSION_SUFFIX = "]";
    public static final String MODULE_LOCAL_NAME = "module";

    private static List<Pair<String, String>> languages = Arrays.asList(Pair.create("xpath", "XPath"), Pair.create("groovy", "Groovy"));

    //Scripting elements
    private QName scriptingScript = new QName("http://www.mulesoft.org/schema/mule/scripting", "script");

    //DataWeave Message Processor
    private QName dwSetPayload = new QName("http://www.mulesoft.org/schema/mule/ee/core", "set-payload");
    private QName dwSetProperty = new QName("http://www.mulesoft.org/schema/mule/ee/core", "set-variable");
    private QName dwSetAttributes = new QName("http://www.mulesoft.org/schema/mule/ee/core", "set-attributes");


    @Override
    public void getLanguagesToInject(@NotNull PsiLanguageInjectionHost host,
                                     @NotNull InjectedLanguagePlaces injectedLanguagePlaces) {
        if (isMuleFile(host.getContainingFile())) {
            if (host instanceof XmlAttributeValue) {
                // Try to inject a language, somewhat abusing the lazy evaluation of predicates :(
                injectExpressionLanguage(host, injectedLanguagePlaces);
            } else if (host instanceof XmlText) {
                final XmlTag tag = ((XmlText) host).getParentTag();
                if (tag != null) {
                    final QName tagName = getQName(tag);
                    if (tagName.equals(scriptingScript)) {
                        final String engine = tag.getAttributeValue("engine");
                        if (engine != null) {
                            injectLanguage(host, injectedLanguagePlaces, StringUtil.capitalize(engine));
                        }
                    } else if (tagName.equals(dwSetPayload) || tagName.equals(dwSetProperty) || tagName.equals(dwSetAttributes)) {
                        injectLanguage(host, injectedLanguagePlaces, WeaveLanguage.WEAVE_LANGUAGE_ID);
                    } else {
                        injectExpressionLanguage(host, injectedLanguagePlaces);
                    }
                }
            }
        } else if (host instanceof PsiLiteralExpression) {
            injectExpressionLanguage(host, injectedLanguagePlaces);
        }
    }

    public static QName getQName(XmlTag xmlTag) {
        return new QName(xmlTag.getNamespace(), xmlTag.getLocalName());
    }

    public static boolean isMuleFile(PsiFile psiFile) {
        if (!(psiFile instanceof XmlFile)) {
            return false;
        }
        if (psiFile.getFileType() != StdFileTypes.XML) {
            return false;
        }
        final XmlFile psiFile1 = (XmlFile) psiFile;
        final XmlTag rootTag = psiFile1.getRootTag();
        return rootTag != null && isMuleTag(rootTag);
    }

    public static boolean isMuleTag(XmlTag rootTag) {
        return rootTag.getLocalName().equalsIgnoreCase(MULE_LOCAL_NAME) || rootTag.getLocalName().equalsIgnoreCase(MODULE_LOCAL_NAME);
    }

    private void injectLanguage(@NotNull PsiLanguageInjectionHost host, @NotNull InjectedLanguagePlaces injectedLanguagePlaces, String scriptingName) {
        final Language requiredLanguage = Language.findLanguageByID(scriptingName);
        if (requiredLanguage != null) {
            final TextRange range = TextRange.from(0, host.getTextRange().getLength());
            injectedLanguagePlaces.addPlace(requiredLanguage, range, null, null);
        }
    }


    private void injectExpressionLanguage(@NotNull PsiLanguageInjectionHost host,
                                          @NotNull InjectedLanguagePlaces injectedLanguagePlaces) {
        // Find the required Language
        final Language requiredLanguage = WeaveLanguage.getInstance();
        if (host instanceof XmlText) {
            injectLanguageIn(((XmlText) host).getValue(), requiredLanguage, injectedLanguagePlaces, 0);
        } else if (host instanceof XmlAttributeValue) {
            injectLanguageIn(StringUtil.unquoteString(host.getText()), requiredLanguage, injectedLanguagePlaces, 1);
        } else if (host instanceof PsiLiteralExpression) {
            if (host.getText().startsWith("\"")) {
                injectLanguageIn(StringUtil.unquoteString(host.getText()), requiredLanguage, injectedLanguagePlaces, 1);
            }
        }
    }

    private void injectLanguageIn(String text, Language requiredLanguage, @NotNull InjectedLanguagePlaces injectedLanguagePlaces, int startOffset) {
        if (text != null) {
            final String trimmedText = text.trim();
            if (trimmedText.startsWith(EXPRESSION_PREFIX) && trimmedText.endsWith(EXPRESSION_SUFFIX) && !trimmedText.startsWith("#[mel:")) {
                int startIndex = text.indexOf(EXPRESSION_PREFIX) + EXPRESSION_PREFIX.length();
                int endIndex = text.lastIndexOf(EXPRESSION_SUFFIX);
                final TextRange expressionTextRange = TextRange.from(startOffset + startIndex, endIndex - startIndex);
                injectedLanguagePlaces.addPlace(requiredLanguage, expressionTextRange, null, null);
            }
        }
    }


}
