import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Factor {


    public String[][] f;
    public ArrayList<String> variables;
    public HashMap<Integer,String> hash_headers;

    public Factor(String[][] s, ArrayList<String> given_outcome, Bayesian b,ArrayList<String> given_value) {
        hash_headers=new HashMap<Integer,String>();
        this.variables=new ArrayList<String>();
        ArrayList<String> headers = find_headers(s);
        for (int k = 0; k < headers.size(); k++) {
            if(given_outcome.contains(headers.get(k))){
                Node node = get_list(b,headers.get(k));
                int place = 0;
                for (int i = 0; i < s[0].length; i++) {
                    if (s[0][i].equals(node.data)) {
                        place = i;
                    }
                }
                int len = s.length;
                int len_col = s[0].length;
//            String[][]copy_t = new String[len-1][len_col];
                int outcome_counter=find_paralel_place(given_outcome,node.data);
                for (int j = 1; j < s.length; j++) {
                    if (!s[j][place].equals(given_value.get(outcome_counter))) {
                        s = remove_row(s, j,node);
                        j--;
                    }
                }
                s = remove_col(s, node);

            }
            }
        this.f=s;
        }

    private int find_paralel_place(ArrayList<String> given_outcome,String s) {
        for (int i = 0; i < given_outcome.size(); i++) {
            if(given_outcome.get(i).equals(s)){
                return i;
            }
        }
        return -1;
    }

    private Node get_list(Bayesian b, String s) {
        for (int i = 0; i < b.nodeList.size(); i++) {
            if(b.nodeList.get(i).data.equals(s)){
                return b.nodeList.get(i);
            }
        }
        return null;
    }

    private ArrayList<String> find_headers(String[][] s) {
        ArrayList<String>heads= new ArrayList<String>();
        for (int i = 0; i < s[0].length-1; i++) {
            heads.add(s[0][i]);
        }
        return heads;
    }

    public static void insert_heads(Factor fac){
        for (int i = 0; i < fac.f[0].length-1; i++) {
            fac.hash_headers.put(i,fac.f[0][i]);
        }
    }

    private String[][] remove_row(String[][] table, int j,Node node) {
        int len = table.length;
        int len_col = table[0].length;
        String[][] copy = new String[len - 1][len_col];
        int row = 0;
        for (int i = 0; i < table.length; i++) {
            for (int k = 0; k < table[0].length; k++) {
                if (i != j) {
                    copy[row][k] = table[i][k];
                }
            }
            if (i != j) {
                row++;
            }
        }
        table = copy;
        return table;
    }

    public static String[][] remove_col(String[][] table, Node node){
        int place=0;
        String[][]copy2= new String[table.length][table[0].length-1];
        for (int i = 0; i < table[0].length; i++) {
            if (table[0][i].equals(node.data)) {
                place = i;
                break;
            }
        }
        int col=0;
        for (int i = 0; i < table.length; i++) {
            for (int k = 0; k < table[0].length; k++) {
                if(k!=place){
                    copy2[i][col]=table[i][k];
                    col++;
                }
            }
//            if (i != place) {
                col=0;
//            }
            }
        table = copy2;
        return table;
    }


    public static void find_rel_var(Factor fac) {
        int j = 0;
        for (int i = 0; i < fac.f[0].length - 1; i++) {
            String vari = fac.f[j][i];
            fac.variables.add(vari);
        }
    }
}




