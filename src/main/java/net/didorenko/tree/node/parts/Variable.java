package net.didorenko.tree.node.parts;


/**
 * package: net.didorenko.exception
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 18.12.2015
 */

public class Variable {

    private String variableName;
    private String variableType;

    public Variable(String variableName, String variableType) {
        this.variableName = variableName;
        this.variableType = variableType;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    @Override
    public String toString() {
        return "variableName= '" + variableName + '\'' +
                ", variableType= '" + variableType + '\'';
    }
}
