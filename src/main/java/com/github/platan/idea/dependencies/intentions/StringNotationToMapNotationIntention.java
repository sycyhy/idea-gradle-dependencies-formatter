package com.github.platan.idea.dependencies.intentions;

import static com.github.platan.idea.dependencies.gradle.Coordinate.isStringNotationCoordinate;
import static org.jetbrains.plugins.groovy.lang.psi.util.ErrorUtil.containsError;
import static org.jetbrains.plugins.groovy.lang.psi.util.GrStringUtil.DOUBLE_QUOTES;
import static org.jetbrains.plugins.groovy.lang.psi.util.GrStringUtil.TRIPLE_DOUBLE_QUOTES;
import static org.jetbrains.plugins.groovy.lang.psi.util.GrStringUtil.escapeAndUnescapeSymbols;
import static org.jetbrains.plugins.groovy.lang.psi.util.GrStringUtil.getStartQuote;
import static org.jetbrains.plugins.groovy.lang.psi.util.GrStringUtil.isStringLiteral;
import static org.jetbrains.plugins.groovy.lang.psi.util.GrStringUtil.removeQuotes;

import com.github.platan.idea.dependencies.gradle.Coordinate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.intentions.base.Intention;
import org.jetbrains.plugins.groovy.intentions.base.PsiElementPredicate;
import org.jetbrains.plugins.groovy.lang.psi.GroovyPsiElementFactory;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.arguments.GrArgumentList;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.literals.GrLiteral;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.literals.GrString;

public class StringNotationToMapNotationIntention extends Intention {

    @Override
    protected void processIntention(@NotNull PsiElement element, Project project, Editor editor) {
        String quote = getStartQuote(element.getText());
        String stringNotation = removeQuotes(element.getText());
        String mapNotation = Coordinate.parse(stringNotation).toMapNotation(quote);
        GrArgumentList argumentList = GroovyPsiElementFactory.getInstance(project).createArgumentListFromText(mapNotation);
        if (isInterpolableString(quote)) {
            replaceGStringMapValuesToString(argumentList, project);
        }
        element.replace(argumentList);
    }

    private boolean isInterpolableString(String quote) {
        return quote.equals(DOUBLE_QUOTES) || quote.equals(TRIPLE_DOUBLE_QUOTES);
    }

    private void replaceGStringMapValuesToString(GrArgumentList map, Project project) {
        for (PsiElement psiElement : map.getChildren()) {
            PsiElement lastChild = psiElement.getLastChild();
            if (lastChild instanceof GrLiteral && !(lastChild instanceof GrString)) {
                String stringWithoutQuotes = removeQuotes(lastChild.getText());
                String unescaped = escapeAndUnescapeSymbols(stringWithoutQuotes, "", "\"$", new StringBuilder());
                String string = String.format("'%s'", unescaped);
                lastChild.replace(GroovyPsiElementFactory.getInstance(project).createExpressionFromText(string));
            }
        }
    }

    @NotNull
    @Override
    protected PsiElementPredicate getElementPredicate() {
        return new PsiElementPredicate() {
            @Override
            public boolean satisfiedBy(PsiElement element) {
                return element.getParent() instanceof GrArgumentList
                        && element instanceof GrLiteral
                        && !containsError(element)
                        && isStringLiteral((GrLiteral) element)
                        && isStringNotationCoordinate(removeQuotes(element.getText()));
            }
        };
    }

    @NotNull
    @Override
    public String getText() {
        return "Convert to map notation";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "Convert string notation to map notation";
    }
}
