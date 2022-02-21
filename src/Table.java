import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;

public class Table {

    String[][] cpt;
    Bayesian bayes;

    public Table(){
        this.cpt=new String[1][1];
    }

    public static int len(String[][]s){
        return s.length;
    }

    public void add (Node node, Bayesian baye) {
        int rows = cols(node, baye);
        int col = colums(node);
        String[][] CPT = new String[rows + 1][col + 1];
        cpt=CPT;
        int m=0;
        for (int j = cpt[0].length-1; j >=0; j--) {
            if (m == 0 && j == col) {
                cpt[m][j] = "P";
            } else if (m == 0 && j == col-1) {
                cpt[m][j] = node.data;
            }
            else if(node.parents.size()!=0){cpt[m][j] = node.parents.get(j);}
//                } else if (i == 0 && j == col) {
//                    cpt[i][j] = "P";
//                }
            }
        ArrayList<String> first_outcome = cur_outcome(cpt[0][0], baye);
        int slice = rows / first_outcome.size();
        int c= cpt[0].length;
        for (int i = 0; i < cpt[0].length-1; i++) {
            ArrayList<String> outcome_node = cur_outcome(cpt[0][i], baye);
//            int slice = rows / outcome_node.size();
                int count = 0;
                int out_count = 0;
                for (int j = 1; j < cpt.length; j++) {
                    if (count < slice) {
                        cpt[j][i] = outcome_node.get(out_count);
                        count++;
                    }
                    if (count >= slice && out_count < outcome_node.size()-1) {
                        out_count++;
                        count = 0;
                    } else if (count >= slice && out_count == outcome_node.size()-1) {
                        out_count = 0;
                        count = 0;
                    }


                }
                if(i!=cpt[0].length-2) {
                    ArrayList<String> cut_slice = cur_outcome(cpt[0][i + 1], baye);
                    slice = slice / cut_slice.size();
                }
                }
        int p_counter= cpt[0].length-1;
        for (int i = 1,int_counter=0; i <= cpt.length-1; i++,int_counter++){
            cpt[i][p_counter]=node.tables.get(int_counter);
        }

        }


    public static ArrayList<String> cur_outcome(String i, Bayesian b) {
        for (int j = 0; j < b.nodeList.size(); j++){
            if (b.nodeList.get(j).data.equals(i)) {
                return b.nodeList.get(j).outcomes;
            }
        }
    return null;
    }

    public static int cols(Node node, Bayesian b) {
        int ans = node.outcomes.size();
        for (int i = 0; i < node.parents.size(); i++) {
            String s = node.parents.get(i);
            for (int j = 0; j < b.nodeList.size(); j++) {
                if (b.nodeList.get(j).data.equals(s)) {
                    ans = ans * b.nodeList.get(j).outcomes.size();
                }
            }
        }
        return ans;
    }





    private int colums(Node node) {
        int res = 1;
        res = res + node.parents.size();
        return res;
    }

    }

