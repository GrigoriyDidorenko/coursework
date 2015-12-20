package net.didorenko.general;

import net.didorenko.tree.node.parts.Variable;

import java.util.Arrays;

public class Rule {

    private Term parent;
    private Term[] children;

    public Rule(Term parent, Term[] children) {
        this.parent = parent;
        this.children = children;
    }

    @Override
    public String toString() {
        return "parent=" + parent +
                ", children=" + Arrays.toString(children);
    }

    public Term getParent() {
        return parent;
    }

    public Term[] getChildren() {
        return children;
    }


    public static class Term {

        private boolean isTerminal;
        private String string;

        private String value; //if term - TYPE - typeName, or if NUMBER - numberValue, or if MET_NAME - methodName,
                                        // or if FUNC_NAME - mathFunctionName, else - null
        private Variable variable; //if term - ID - variable, else - null

        public Term(boolean isTerminal, String string) {
            this.isTerminal = isTerminal;
            this.string = string;
        }

        public boolean isTerminal() {
            return isTerminal;
        }

        public boolean isTerminalOrReserved() {
            return isTerminal ||
                    string.equals(Grammar.getInstance().STRING) ||
                    string.equals(Grammar.getInstance().NUMBER) ||
                    string.equals(Grammar.getInstance().TYPE) ||
                    string.equals(Grammar.getInstance().MET_NAME) ||
                    string.equals(Grammar.getInstance().FUNC_NAME) ||
                    string.equals(Grammar.getInstance().BOOL_SIGN) ||
                    string.equals(Grammar.getInstance().HP_SIGN) ||
                    string.equals(Grammar.getInstance().LP_SIGN) ||
                    string.equals(Grammar.getInstance().ID) ||
                    string.equals(Grammar.getInstance().ONLY_ID);
        }

        public void setTerminal(boolean terminal) {
            this.isTerminal = terminal;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Variable getVariable() {
            return variable;
        }

        public void setVariable(Variable variable) {
            this.variable = variable;
        }

        public String smartGetString() {
            if (variable != null) return variable.getVariableName();
            if (value != null) return value;
            return string;
        }

        public void stole(Term victim) {
            isTerminal = victim.isTerminal;
            string = victim.string;
            value = victim.value;
            variable = victim.variable;
        }

        @Override
        public String toString() {
            return string;
        }
    }

}
