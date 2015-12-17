package net.didorenko;

import java.util.List;

/**
 * package: PACKAGE_NAME
 * project: coursework
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 14.12.2015
 */
public class Node {

    private Rule.Term data;
    private Node parent;
    private List<Node> childs;

    public Node(Rule.Term data) {
        this.data = data;
    }

    public Node(Rule.Term data, Node parent, List<Node> childs) {
        this.data = data;
        this.parent = parent;
        this.childs = childs;
    }

    public Rule.Term getData() {
        return data;
    }

    public void setData(Rule.Term data) {
        this.data = data;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChilds() {
        return childs;
    }

    public void addChild(Node child){
        this.childs.add(child);
    }

    public void setChilds(List<Node> childs) {
        this.childs = childs;
    }

}
