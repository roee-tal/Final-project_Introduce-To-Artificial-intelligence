import java.util.ArrayList;

public class Node {

    String data;
    ArrayList<String> parents = new ArrayList<String>();
    ArrayList<String> childs = new ArrayList<String>();
    ArrayList<String> outcomes = new ArrayList<String>();
    ArrayList<String> tables = new ArrayList<String>();
    Table cptt;
    Node p, c;

    public Node(String data){
        this.data=data;
        cptt = new Table();
    }

    @Override
    public String toString() {
        return "Node{" +
                "data='" + data + '\'' +
                ", parents=" + parents +
                ", childs=" + childs +
                ", outcomes=" + outcomes +
                '}';
    }
}
