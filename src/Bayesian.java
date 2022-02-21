import java.util.ArrayList;

public class Bayesian {

    public Node head;
    public ArrayList<Node> nodeList;

    public Bayesian(Node head){
        this.head = head;
        nodeList.add(0,head);
    }
    public Bayesian(){
        this.nodeList = new ArrayList<Node>();
    }

    @Override
    public String toString() {
        return "Bayesian{" +
                ", nodeList=" + nodeList.get(0) +
                '}';
    }
}
