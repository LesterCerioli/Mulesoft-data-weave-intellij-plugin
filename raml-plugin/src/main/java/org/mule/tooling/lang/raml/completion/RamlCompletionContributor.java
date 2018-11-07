package org.mule.tooling.lang.raml.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiFile;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.mule.tooling.lang.raml.util.RamlUtils;

import org.raml.v2.internal.impl.RamlSuggester;
import org.raml.yagi.framework.suggester.Suggestion;
import org.raml.yagi.framework.suggester.Suggestions;

import java.util.List;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class RamlCompletionContributor extends CompletionContributor
{
    public RamlCompletionContributor()
    {
        extend(
                CompletionType.BASIC,
                psiElement(),
                new RamlCompletionParametersCompletionProvider());
    }

    private static class RamlCompletionParametersCompletionProvider extends CompletionProvider<CompletionParameters>
    {

        @Override
        protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet)
        {
            final int offset = completionParameters.getOffset() - 1;
            final PsiFile originalFile = completionParameters.getOriginalFile();
            if (RamlUtils.isRamlFile(originalFile))
            {
                final String text = originalFile.getText();
                System.out.println("text = " + text);
                System.out.println("offset = " + offset);
                final Suggestions suggestions = new RamlSuggester().suggestions(text, offset);
                final List<Suggestion> suggestionList = suggestions.getSuggestions();
                for (Suggestion suggestion : suggestionList)
                {
                    final LookupElementBuilder map = LookupElementBuilder.create(suggestion.getValue())
                                                                         .withPresentableText(suggestion.getLabel())
                                                                         .withLookupString(suggestion.getLabel())
                                                                         .withLookupString(suggestion.getValue());
                    completionResultSet.addElement(map);
                }
            }
        }
    }
}
