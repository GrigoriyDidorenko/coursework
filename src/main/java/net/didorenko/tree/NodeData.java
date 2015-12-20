package net.didorenko.tree;

import net.didorenko.general.Grammar;
import net.didorenko.general.Rule;
import net.didorenko.tree.node.parts.Variable;

public class NodeData {

    private Rule.Term term;
    private String additionalData; //if term - TYPE - typeName, or if NUMBER - numberValue, or if MET_NAME - methodName,
                                    // or if FUNC_NAME - mathFunctionName, else - null
    private Variable variable; //if term - ID - variable, else - null

    public NodeData(Rule.Term term) {
        this.term = term;
    }

    public Rule.Term getTerm() {
        return term;
    }

    public void setTerm(Rule.Term term) {
        this.term = term;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public String getNodeString() {
        Grammar grammar = Grammar.getInstance();
        if (term.getString().equals(grammar.ID) ||
                term.getString().equals(grammar.ONLY_ID)) return variable.getVariableName();

        if (term.getString().equals(grammar.TYPE) ||
                term.getString().equals(grammar.NUMBER) ||
                term.getString().equals(grammar.MET_NAME) ||
                term.getString().equals(grammar.FUNC_NAME)) return additionalData;

        return term.getString();
    }


    @Override
    public String toString() {
        return term.getString();
    }
}
