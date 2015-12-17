package net.didorenko;

/**
 * package: net.didorenko.parser
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 17.12.2015
 */
public class Rule {

    private Term parents;
    private Term[] childs;

    public Rule(){}

    public Rule(Term parents, Term[] childs) {
        this.parents = parents;
        this.childs = childs;
    }

    public Term getParents() {
        return parents;
    }

    public void setParents(Term parents) {
        this.parents = parents;
    }

    public Term[] getChilds() {
        return childs;
    }

    public void setChilds(Term[] childs) {
        this.childs = childs;
    }

    public static class Term {

        private boolean isTerminal;
        private String value;

        public Term(boolean isTerminal, String value) {
            this.isTerminal = isTerminal;
            this.value = value;
        }

        public boolean isTerminal() {
            return isTerminal;
        }

        public void setIsTerminal(boolean isTerminal) {
            this.isTerminal = isTerminal;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
