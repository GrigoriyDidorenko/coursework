package net.didorenko.parser;

import net.didorenko.Grammar;
import net.didorenko.Node;
import net.didorenko.Rule;
import net.didorenko.Token;

import java.util.ArrayList;

/**
 * package: net.didorenko.parser
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 14.12.2015
 */
public class SyntaxicalAnalyzer {

    public static void inspect(ArrayList<Token> tokens) throws SyntaxicalException {
        Node node = new Node(new Rule.Term(false, "PROGRAM"));
        findAppropriateRule(node, tokens);
    }

    public static void childCreator(Node parentNode, Token token) throws SyntaxicalException {
        Node node = new Node((new Rule.Term(true, token.getValue())));
        node.setParent(parentNode);
        parentNode.addChild(node);
    }
    

    public static Rule findAppropriateRule(Node currentNode, ArrayList<Token> tokens) throws SyntaxicalException {
        int counter = 0;
        Rule result = new Rule();
        ArrayList<Rule> possibleRules = new ArrayList<>();
        ArrayList<Token> tmp = new ArrayList<>(tokens);
        for (Rule rule : Grammar.rules)
            if (rule.getParents().getValue().equals(currentNode.getData().getValue()))
                possibleRules.add(rule);
        switch (possibleRules.size()) {
            case 0:
                throw new SyntaxicalException("Syntaxical error with construction" + currentNode.getData().getValue());
            case 1:
                return possibleRules.get(0);
            default:
                for (int i = 0; i < possibleRules.size(); i++) {
                    result = possibleRules.get(i);
                    counter = 0;
                    if (tokens.size() < result.getChilds().length)
                        continue;
                    for (int j = 0; j < result.getChilds().length; j++) {
                        if (result.getChilds()[j].isTerminal()) {
                            if (tokens.get(j).getValue().equals(result.getChilds()[j].getValue())) {
                                tmp.remove(tokens.get(j));
                                counter++;
                                childCreator(currentNode, tokens.get(j));
                            } else if (counter != 0)
                                throw new SyntaxicalException("Syntaxical error");
                            else
                                break;
                        } else{
                            Node node = new Node(new Rule.Term(false, result.getChilds()[j].getValue()));
                            findAppropriateRule(node, tmp);
                        }
                    }
                }
        }
        if (counter == 0) throw new SyntaxicalException("Syntaxical error");
        return result;
    }
}