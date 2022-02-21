import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class Ex1 {

    private static int add_actions=0;
    private static int add_dup=0;

    private static void add_actions(){
        add_actions++;
    }
    private static void add_dup(){
        add_dup++;
    }

    private static void set_addup(){
        add_actions=0;
        add_dup=0;
    }

    public static boolean ind(Node a, String b, ArrayList<String> arr, Bayesian bayes) { // arr for given
        Queue<Node> q = new LinkedList<Node>();
        Queue<Integer> ezer = new LinkedList<Integer>();
        boolean[] visit_p = new boolean[bayes.nodeList.size()];
        boolean[] visit_c = new boolean[bayes.nodeList.size()];
        q.add(a);
        ezer.add(2);
        while (!q.isEmpty()) {
            Node temp_n = q.poll();
            int temp_i = ezer.poll();
            if (temp_n.data.equals(b)) return false;
            if (temp_i == 2) {
                add_children(temp_n, bayes, visit_c, q, ezer);
                add_parents(temp_n, bayes, visit_p, q, ezer);

            }
            if (temp_i == 0 && (!arr.contains(temp_n.data))) {
                for (int i = 0; i < temp_n.childs.size(); i++) {
                    String str = temp_n.childs.get(i);
                    for (int j = 0; j < bayes.nodeList.size(); j++) {
                        if (bayes.nodeList.get(j).data.equals(str)) {
                            if (visit_c[j] == false) {
                                q.add(bayes.nodeList.get(j));
                                ezer.add(0); // 0 from parents
                                visit_c[j] = true;
                            }
                        }
                    }
                }
            } else if (temp_i == 1 && (!arr.contains(temp_n.data))) {
                for (int i = 0; i < temp_n.childs.size(); i++) {
                    String str = temp_n.childs.get(i);
                    for (int j = 0; j < bayes.nodeList.size(); j++) {
                        if (bayes.nodeList.get(j).data.equals(str)) {
                            if (visit_c[j] == false) {
                                q.add(bayes.nodeList.get(j));
                                ezer.add(0); // 0 from parents
                                visit_c[j] = true;
                            }
                        }
                    }
                }
                for (int i = 0; i < temp_n.parents.size(); i++) {
                    String str = temp_n.parents.get(i);
                    for (int j = 0; j < bayes.nodeList.size(); j++) {
                        if (bayes.nodeList.get(j).data.equals(str)) {
                            if (visit_p[j] == false) {
                                q.add(bayes.nodeList.get(j));
                                ezer.add(1); // 1 from childs
                                visit_p[j] = true;
                            }
                        }
                    }
                }
            } else if (temp_i == 0 && arr.contains(temp_n.data)) {
                for (int i = 0; i < temp_n.parents.size(); i++) {
                    String str = temp_n.parents.get(i);
                    for (int j = 0; j < bayes.nodeList.size(); j++) {
                        if (bayes.nodeList.get(j).data.equals(str)) {
                            if (visit_p[j] == false) {
                                q.add(bayes.nodeList.get(j));
                                ezer.add(1); // 1 from childs
                                visit_p[j] = true;
                            }
                        }
                    }
                }
            } else if (temp_i == 1 && arr.contains(temp_n.data)) {
                continue;
            }

//                for (int i = 0; i < temp.childs.size(); i++) {
//                    String str = temp.childs.get(i);
//                    for (int j = 0; j < bays.nodeList.size(); j++) {
//                        if (bays.nodeList.get(j).data.equals(str)) q.add(bays.nodeList.get(j));
//                    }
//                }
        }
        //return true;

        //}
        return true;
    }

    public static void add_children(Node temp_n, Bayesian bayes, boolean[] visit_c, Queue q, Queue ezer) {
        for (int i = 0; i < temp_n.childs.size(); i++) {
            String str = temp_n.childs.get(i);
            for (int j = 0; j < bayes.nodeList.size(); j++) {
                if (bayes.nodeList.get(j).data.equals(str)) {
                    if (visit_c[j] == false) {
                        q.add(bayes.nodeList.get(j));
                        ezer.add(0); // 0 from parents
                        visit_c[j] = true;
                    }
                }
            }
        }
    }

    public static void add_parents(Node temp_n, Bayesian bayes, boolean[] visit_p, Queue q, Queue ezer) {
        for (int i = 0; i < temp_n.parents.size(); i++) {
            String str = temp_n.parents.get(i);
            for (int j = 0; j < bayes.nodeList.size(); j++) {
                if (bayes.nodeList.get(j).data.equals(str)) {
                    if (visit_p[j] == false) {
                        q.add(bayes.nodeList.get(j));
                        ezer.add(1); // 1 from childs
                        visit_p[j] = true;
                    }
                }
            }
        }
    }

    public static Node toNode(String a, Bayesian bayes) {
        Node flag = new Node(a);
        for (int i = 0; i < bayes.nodeList.size(); i++) {
            if (bayes.nodeList.get(i).data.equals(a)) flag = bayes.nodeList.get(i);
        }
        return flag;
    }


    /*
    PART2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public static boolean isAncestor(Node node, Bayesian bayes, ArrayList<String> given, String query) {
        Node qu = toNode(query, bayes);
        boolean[] visit = new boolean[bayes.nodeList.size()];
        Queue<Node> q = new LinkedList<Node>();
        q.add(qu);
        for (int i = 0; i < given.size(); i++) {
            Node n = toNode(given.get(i), bayes);
            q.add(n);
        }
        while (!q.isEmpty()) {
            Node temp = q.poll();
            if (temp.data.equals(node.data)) {
                return true;
            }
            for (int i = 0; i < temp.parents.size(); i++) {
                String str = temp.parents.get(i);
                for (int j = 0; j < bayes.nodeList.size(); j++) {
                    if (bayes.nodeList.get(j).data.equals(str)) {
                        if (visit[j] == false) {
                            q.add(bayes.nodeList.get(j));
                            visit[j] = true;
                        }
                    }
                }
            }
        }
        return false;

    }

    public static double join(Bayesian b, FactorList factor_list, ArrayList<String> hidden, ArrayList<String> query) {
        Collections.sort(factor_list.factors, new Comparing());
        for (int i = 0; i < hidden.size(); i++) {
            String f = hidden.get(i);
            int cou = 0;
            int j = 0;
//            for (int j = 0; j < factor_list.factors.size(); j++)
            while (j < factor_list.factors.size()) {
                if (factor_list.factors.get(j).variables.contains(f)) {
                    Factor f_first = factor_list.factors.get(j);
                    cou++;
                    boolean find = false;
                    for (int k = j + 1; k < factor_list.factors.size() && !find; k++) {
                        if (factor_list.factors.get(k).variables.contains(f)) {
                            Factor f_second = factor_list.factors.get(k);
                            cou++;
                            join(b, f, f, hidden, f_first, f_second, factor_list);
                            factor_list.factors.remove(k);
                            factor_list.factors.remove(j);
                            find = true;
                            Collections.sort(factor_list.factors, new Comparing());
                        }
                    }
                    if (cou == 1) {
                        eliminate(f, f_first.f, b, factor_list);
                        factor_list.factors.remove(j);
                        Collections.sort(factor_list.factors, new Comparing());
                    }
                }
                if (cou == 2) {
                    j = 0;
                    cou = 0;
                } else j++;
            }
        }
        if(factor_list.factors.size()>1){
            for (int i = 0; i < factor_list.factors.size(); i++) {
                if(factor_list.factors.get(i).f.length<3){
                    factor_list.factors.remove(i);
                }
            }
            for (int i = 0; i < factor_list.factors.size(); i++) {
                boolean find=false;
                Factor f_first = factor_list.factors.get(i);
                for (int j = i+1; j < factor_list.factors.size() &&find==false; j++) {
                    Factor f_second = factor_list.factors.get(j);
                    join(b, "s", "s", hidden, f_first, f_second, factor_list);
                    factor_list.factors.remove(i);
                    factor_list.factors.remove(i);
                    find=true;
                }
            }
        }
        Factor f_first = factor_list.factors.get(0);
        normalize(b, f_first, factor_list);

        Factor last=factor_list.factors.get(0);
        for (int i = 1; i < last.f.length; i++) {
            if(query.get(1).equals(last.f[i][0])){
                String s_res=last.f[i][last.f[0].length-1];
                double d_res=Double.parseDouble(s_res);
                return d_res;

            }
        }
        return -1;
    }

    private static void normalize(Bayesian b, Factor f_first, FactorList factor_list) {
        double sum=0;
        String f_num = f_first.f[1][f_first.f[0].length-1];
        double num1 = Double.parseDouble(f_num);
        sum = sum + num1;
        for (int i = 2; i < f_first.f.length; i++) {
            String s_num=f_first.f[i][f_first.f[0].length-1];
            double num11 = Double.parseDouble(s_num);
            sum = sum + num11;
            add_actions();
        }
        for (int i = 1; i < f_first.f.length; i++) {
            String num=f_first.f[i][f_first.f[0].length-1];
            double num3 = Double.parseDouble(num);
            double d_total=num3/sum;
            String s_total = String.valueOf(d_total);
            f_first.f[i][f_first.f[0].length-1]=s_total;
        }
    }

    public static void join(Bayesian b, String first, String second, ArrayList<String> given, Factor cpt_first, Factor cpt_second, FactorList factor_list) {
//        Node s_first = NodeLists(b, first);
////        String[][] cpt_first = s_first.cptt.cpt;
//        Node s_second = NodeLists(b, second);
//        String[][] cpt_second = s_second.cptt.cpt;
        ArrayList<String> check_con = find_new_headers(cpt_first.f, cpt_second.f);
        int rows = 0;
        if (cpt_first.f[0].length > cpt_second.f[0].length) {
            if (is_in(cpt_first.f, cpt_second.f)) {
                rows = cpt_first.f.length - 1;
            } else {
                rows = find_new_row_num(cpt_first.f, cpt_second.f, b);
            }
        } else if (cpt_first.f[0].length < cpt_second.f[0].length) {
            if (is_in(cpt_second.f, cpt_first.f)) {
                rows = cpt_second.f.length - 1;
            } else {
                rows = find_new_row_num(cpt_second.f, cpt_first.f, b);
            }
        } else {
            rows = find_new_row_num(cpt_first.f, cpt_second.f, b);
        }
        String[][] new_factor = new String[rows + 1][check_con.size() + 1];
        gen_new_headers(new_factor, check_con);
        ArrayList<String> cur_out = ret_outcomes(new_factor[0][0], b);
        int slice = rows / cur_out.size();
        for (int i = 0; i < new_factor[0].length - 1; i++) {
            ArrayList<String> outcome_node = ret_outcomes(new_factor[0][i], b);
            int count = 0;
            int out_count = 0;
            for (int j = 1; j < new_factor.length; j++) {
                if (count < slice) {
                    new_factor[j][i] = outcome_node.get(out_count);
                    count++;
                }
                if (count >= slice && out_count < outcome_node.size() - 1) {
                    out_count++;
                    count = 0;
                } else if (count >= slice && out_count == outcome_node.size() - 1) {
                    out_count = 0;
                    count = 0;
                }
            }
            if (i != new_factor[0].length - 2) {
                ArrayList<String> cut_slice = ret_outcomes(new_factor[0][i + 1], b);
                slice = slice / cut_slice.size();
            }
        }
        HashMap<Integer, String> new_f_heads = new HashMap<Integer, String>();
        for (int i = 0; i < new_factor[0].length - 1; i++) {
            new_f_heads.put(i, new_factor[0][i]);
        }
        String a = "A";
//        ArrayList<Integer> not_found_place1= check_var_is_in(new_factor,cpt_first);
//        ArrayList<Integer> not_found_place2= check_var_is_in(new_factor,cpt_second);
        duplicate_factors(new_factor, cpt_first, cpt_second, a, new_f_heads, factor_list, b);
    }

    private static ArrayList<Integer> check_var_is_in(String[][] new_factor, String[][] cpt_first) {
        ArrayList<String> new_factor_var = new ArrayList<String>();
        for (int i = 0; i < new_factor[0].length - 1; i++) {
            new_factor_var.add(new_factor[0][i]);
        }
        ArrayList<String> cpt_found_var = new ArrayList<String>();
        for (int i = 0; i < cpt_first[0].length - 1; i++) {
            cpt_found_var.add(cpt_first[0][i]);
        }
        ArrayList<Integer> not_found_var = new ArrayList<Integer>();
        for (int i = 0; i < new_factor_var.size(); i++) {
            if (!cpt_found_var.contains(new_factor_var.get(i))) {
                not_found_var.add(i);
            }
        }
        return not_found_var;
    }

    private static boolean is_in(String[][] cpt_first, String[][] cpt_second) {
        ArrayList<String> first = new ArrayList<String>();
        for (int i = 0; i < cpt_first[0].length - 1; i++) {
            first.add(cpt_first[0][i]);
        }
        for (int i = 0; i < cpt_second[0].length - 1; i++) {
            if (!first.contains(cpt_second[0][i])) {
                return false;
            }
        }
        return true;
    }

    private static void duplicate_factors(String[][] new_factor, Factor cpt_first, Factor cpt_second, String a, HashMap<Integer, String> new_f_head, FactorList factor_list, Bayesian b) {
        ArrayList<String> count_rel_var1 = new ArrayList<String>();
        ArrayList<String> count_rel_var2 = new ArrayList<String>();
        int count1 = 0;
        int count2 = 0;
        for (int i = 1; i < new_factor.length; i++) {
            boolean finish1 = false;
            boolean finish2 = false;
            double sum = 1;
            String[] row_of_n = get_row(new_factor, i);
            for (int j = 1; j < cpt_first.f.length && !finish1 == true; j++) {
                String[] row_of_first = get_row(cpt_first.f, j);
                for (int k = 0; k < new_factor[0].length - 1; k++) {
                    if (is_inside(new_f_head, cpt_first.hash_headers, k) != -1) {
                        int place_var1 = is_inside(new_f_head, cpt_first.hash_headers, k);
                        if (cpt_first.f[j][place_var1].equals(row_of_n[k])) {
                            count1++;
                        }
                    }

                    if (count1 == cpt_first.f[0].length - 1) {
                        String s_num1 = cpt_first.f[j][cpt_first.f[0].length - 1];
                        double num1 = Double.parseDouble(s_num1);
                        sum = sum * num1;
                        count1 = 0;
                        finish1 = true;
                    }
                }
                count1 = 0;
            }
            for (int l = 1; l < cpt_second.f.length && !finish2 == true; l++) {
                String[] row_of_first = get_row(cpt_second.f, l);
                for (int m = 0; m < new_factor[0].length - 1 && !finish2 == true; m++) {
                    if (is_inside(new_f_head, cpt_second.hash_headers, m) != -1) {
                        int place_var2 = is_inside(new_f_head, cpt_second.hash_headers, m);
                        if (cpt_second.f[l][place_var2].equals(row_of_n[m])) {
                            count2++;
                        }
                    }

                    if (count2 == cpt_second.f[0].length - 1) {
                        String s_num1 = cpt_second.f[l][cpt_second.f[0].length - 1];
                        double num1 = Double.parseDouble(s_num1);
                        sum = sum * num1;
                        add_dup();
                        count2 = 0;
                        finish2 = true;
                        String total = String.valueOf(sum);
                        new_factor[i][new_factor[0].length - 1] = total;
                    }
                }
                count2 = 0;
            }
        }
        ArrayList<String> s1 = new ArrayList<String>();
        ArrayList<String> s2 = new ArrayList<String>();
        Factor new_f = new Factor(new_factor, s1, b, s2);
        factor_list.factors.add(new_f);
        Factor.find_rel_var(new_f);
        Factor.insert_heads(new_f);
    }



    private static int is_inside(HashMap<Integer, String> new_f_head, HashMap<Integer, String> hash_headers, int k) {
        for (int i = 0; i < new_f_head.size(); i++) {
            if (new_f_head.get(k).equals(hash_headers.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private static String[] get_row(String[][] new_factor, int i) {
        String[] get_row = new String[new_factor[0].length - 1];
        for (int j = 0; j < get_row.length; j++) {
            get_row[j] = new_factor[i][j];
        }
        return get_row;
    }




    private static ArrayList<String> add_cur_var(String[][] new_factor, int i, ArrayList<Integer> c_factor) {
        ArrayList<String> rel_var = new ArrayList<String>();
        for (int j = 0; j < new_factor[0].length - 1; j++) {
            if (!c_factor.contains(j)) {
                rel_var.add(new_factor[i][j]);
            }
        }
        return rel_var;
    }


    private static void eliminate(String s, String[][] table, Bayesian b, FactorList factor_list) {
        Node var_node = NodeLists(b, s);
        int rows = table.length / var_node.outcomes.size();
        table = Factor.remove_col(table, var_node);
        String[][] eliminate_copy = new String[rows + 1][table[0].length];
        for (int i = 0; i < table[0].length; i++) {
            eliminate_copy[0][i] = table[0][i];
        }
        ArrayList<String> first_outcome = Table.cur_outcome(eliminate_copy[0][0], b);
        int slice = rows / first_outcome.size();
        for (int i = 0; i < eliminate_copy[0].length - 1; i++) {
            ArrayList<String> outcome_node = Table.cur_outcome(eliminate_copy[0][i], b);
            int count = 0;
            int out_count = 0;
            for (int j = 1; j < eliminate_copy.length; j++) {
                if (count < slice) {
                    eliminate_copy[j][i] = outcome_node.get(out_count);
                    count++;
                }
                if (count >= slice && out_count < outcome_node.size() - 1) {
                    out_count++;
                    count = 0;
                } else if (count >= slice && out_count == outcome_node.size() - 1) {
                    out_count = 0;
                    count = 0;
                }
            }
            if (i != eliminate_copy[0].length - 2) {
                ArrayList<String> cut_slice = Table.cur_outcome(eliminate_copy[0][i + 1], b);
                slice = slice / cut_slice.size();
            }
        }
        insertP(eliminate_copy, table, var_node, b, factor_list);
    }

    private static int skip_col(String[][] table, Node var_node) {
        for (int i = 0; i < table[0].length; i++) {
            if (table[0][i].equals(var_node.data)) {
                return i;
            }
        }
        return -1;
    }

    private static void insertP(String[][] eliminate_copy, String[][] table, Node var_node, Bayesian b, FactorList factor_list) {
        ArrayList<String> count_outcomes = new ArrayList<String>();
        int count = 0;
//        int stop = eliminate_copy.length - 1;
//        int stop_counts = stop / var_node.outcomes.size();
        int stop_count = eliminate_copy[0].length - 1;
        for (int i = 1; i < eliminate_copy.length; i++) {
            int finish=0;
            double sum = 0;
//            int m = 1;
            count_outcomes = addd(eliminate_copy, i);
//            m++;
            for (int j = 1; j < table.length ; j++) {
                for (int k = 0; k < table[0].length - 1 ; k++) {
//                    if(k!=var_place){
                    if (table[j][k].equals(count_outcomes.get(k))) {
                        count++;
                    }
                    if (count == stop_count) {
                        String s_num1 = table[j][table[0].length - 1];
                        double num1 = Double.parseDouble(s_num1);
                        sum = sum + num1;
                        count = 0;
                        finish++;
                    }
//                    if(finish==stop_count+1){
//                        add_actions();
//                    }
                }
                count = 0;
            }

            String put = String.valueOf(sum);
            eliminate_copy[i][eliminate_copy[0].length - 1] = put;
            count_outcomes.clear();
        }
        int dif= table.length-eliminate_copy.length;
        for (int j = 0; j < dif; j++) {
            add_actions();
        }
        ArrayList<String> s1 = new ArrayList<String>();
        ArrayList<String> s2 = new ArrayList<String>();
//            Node node=new Node();
        Factor new_f = new Factor(eliminate_copy, s1, b, s2);
        factor_list.factors.add(new_f);
        Factor.find_rel_var(new_f);
        Factor.insert_heads(new_f);
    }

    private static ArrayList<String> addd(String[][] s, int i) {
        ArrayList<String> out = new ArrayList<String>();
        for (int j = 0; j < s[0].length - 1; j++) {
            out.add(s[i][j]);
        }
        return out;
    }


    private static int find_node_place(String[][] cpt_second, String a) {
        for (int i = 0; i < cpt_second[0].length; i++) {
            if (cpt_second[0][i].equals(a)) {
                return i;
            }
        }
        return -1;
    }


    private static ArrayList<String> ret_outcomes(String s, Bayesian b) {
        for (int j = 0; j <= b.nodeList.size(); j++) {
            if (b.nodeList.get(j).data.equals(s)) {
                return b.nodeList.get(j).outcomes;
            }
        }
        return null;
    }


    private static void gen_new_headers(String[][] new_factor, ArrayList<String> check_con) {
        int count = 0;
        for (int i = 0; i < new_factor[0].length; i++) {
            for (int j = 0; j < new_factor[0].length; j++) {
                if (i == 0 && j != new_factor[0].length - 1) {
                    new_factor[i][j] = check_con.get(count);
                    count++;
//                } else if (i == 0 && j != new_factor.length) {
//                    new_factor[i][j] = check_con.get(j - 1);
                } else if (i == 0 && j == new_factor[0].length - 1) {
                    new_factor[i][j] = "P";
                }
            }
        }
    }


    private static int find_new_row_num(String[][] cpt_first, String[][] cpt_second, Bayesian b) {
        int len = cpt_first.length - 1;
        int sum = 0;
        int count = 0;
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < cpt_first[0].length - 1; i++) {
            res.add(cpt_first[0][i]);
        }
        for (int i = 0; i < cpt_second[0].length - 1; i++) {
            if (res.contains(cpt_second[0][i])) {
                continue;
            } else {
                res.add(cpt_second[0][i]);
                count++;
                Node node = toNode(cpt_second[0][i], b);
                sum = node.outcomes.size();
                len = len * sum;
            }
        }
        if (count == 0) {
            return cpt_first.length - 1;
        } else {
//            return (cpt_first.length-1) * count * sum;
            return len;
        }
    }

    private static ArrayList<String> find_new_headers(String[][] cpt_first, String[][] cpt_second) {
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < cpt_first[0].length - 1; i++) {
            res.add(cpt_first[0][i]);
        }
        for (int i = 0; i < cpt_second[0].length - 1; i++) {
            if (res.contains(cpt_second[0][i])) {
                continue;
            } else {
                res.add(cpt_second[0][i]);
            }
        }
        return res;
    }

    private static Node NodeLists(Bayesian b, String first) {
        for (int i = 0; i < b.nodeList.size(); i++) {
            if (b.nodeList.get(i).data.equals(first)) {
                return b.nodeList.get(i);
            }
        }
        return null;
    }

    private static Bayesian build_net(String path) throws FileNotFoundException {
        File text = new File(path);
        Scanner scnr = new Scanner(text);
        Bayesian b = new Bayesian();
        String title = "";
        while (scnr.hasNextLine()) {
            String line = scnr.nextLine();
            if (line.contains("NAME")) {
                String sub = (line.substring(7));
                String[] parts = sub.split("<");
                String part1 = parts[0];
                Node n = new Node(part1);
                b.nodeList.add(0, n);
            } else if (line.contains("OUTCOME")) {
                String sub = (line.substring(10));
                String[] parts = sub.split("<");
                String part1 = parts[0];
                //System.out.println(part1);
                b.nodeList.get(0).outcomes.add(part1);

            } else if (line.contains("FOR")) {
                String sub = (line.substring(6));
                String[] parts = sub.split("<");
                String part1 = parts[0];
                title = part1;
            } else if (line.contains("GIVEN")) {
                String sub = (line.substring(8));
                String[] parts = sub.split("<");
                String part1 = parts[0];
                //int ind = 0;
                for (int i = 0; i < b.nodeList.size(); i++) {
                    if (b.nodeList.get(i).data.equals(title)) {
                        //int ind = i;
                        b.nodeList.get(i).parents.add(part1);
                        break;
                    }
                }
                for (int i = 0; i < b.nodeList.size(); i++) {
                    if (b.nodeList.get(i).data.equals(part1)) {
                        //int ind = i;
                        b.nodeList.get(i).childs.add(title);
                        break;
                    }
                }

            } else if (line.contains("TABLE")) {
                String sub = (line.substring(8));
                String[] parts = sub.split("<");
                String part1 = parts[0];
                String[] val = part1.split(" ");
                Node n = b.nodeList.get(0);
                for (int i = 0; i < b.nodeList.size(); i++) {
                    if (b.nodeList.get(i).data.equals(title)) {
                        n = b.nodeList.get(i);
                        break;
                    }
                }
                for (int i = 0; i < val.length; i++) {
                    n.tables.add(val[i]);
                }
                n.cptt.add(n, b);

            }
//            System.out.println(b.nodeList.get(2).cptt);
        }
        return b;
    }

    public static void main(String[] args) throws FileNotFoundException {
        try{
            FileWriter writer = new FileWriter("output.txt");
            try{
                File input = new File("input.txt");
                Scanner read = new Scanner(input);
                Bayesian b = null;
                while (read.hasNextLine()){
                    set_addup();
                    String data = read.nextLine();
                    String ans="";
                    if(data.contains("xml")){
                        b=build_net(data);
                    }
                    else if(data.contains("P")){
                        data = data.replace("P","");
                        data = data.replace("(","");
                        data = data.replace(")","");
                        String[] s=data.split(" ");
                        String[] q_e=s[0].split("\\|");
                        String[] q_v=q_e[0].split("=");
                        ArrayList<String> query = new ArrayList<String>();
                        query.add(q_v[0]);
                        query.add(q_v[1]);
                        ArrayList<String> vars = new ArrayList<String>();
                        ArrayList<String> values = new ArrayList<String>();
                        ArrayList<String> given = new ArrayList<String>();
                        if(q_e.length==2){
                            String[] evidences=q_e[1].split(",");
                            for (int i = 0; i < evidences.length; i++) {
                                evidences[i].split("=");
                                vars.add(evidences[i].split("=")[0]);
                                values.add(evidences[i].split("=")[1]);
                            }
                        }
                        if(s.length>1){
                            String[] hiddens = s[1].split("-");
                            for (int i = 0; i < hiddens.length; i++) {
                                given.add(hiddens[i]);
                            }
                        }
                        FactorList factor_list = new FactorList();
                        for (int i = 0; i < b.nodeList.size(); i++) {
                            Factor f = new Factor(b.nodeList.get(i).cptt.cpt, vars, b, values);
                            factor_list.factors.add(f);
                        }
                        for (int i = 0; i < factor_list.factors.size(); i++) {
                            Factor.find_rel_var(factor_list.factors.get(i));
                            Factor.insert_heads(factor_list.factors.get(i));
                        }
                        for (int i = 0; i < given.size(); i++) {
                            Node node = toNode(given.get(i), b);
                            if (!isAncestor(node, b, vars, query.get(0))) {
                                find_fact(factor_list.factors, given.get(i),given);
                            }
                        }
                        for (int i = 0; i < given.size(); i++) {
                            Node node = toNode(given.get(i), b);
                            if(ind(node,query.get(0),vars,b)){
                                for (int j = 0; j < factor_list.factors.size(); j++) {
                                    find_fact(factor_list.factors, given.get(i),given);
                                }
                            }
                        }
                        ans=String.format("%.5f",join(b, factor_list, given,query));
                        ans = ans+","+add_actions+","+add_dup;
                        writer.write(ans);
                        if(read.hasNextLine()){
                            writer.write("\n");
                        }
                    }
                    else{
                        String[] bayes = data.split("\\|");
                        String[] inds =  bayes[0].split("-");
                        String s1= inds[0];
                        String s2= inds[1];
                        Node node = toNode(s2, b);
                        if(bayes.length>1){
                            String[] evidences=bayes[1].split(",");
                            ArrayList<String> given = new ArrayList<String>();
                            for (int i = 0; i < evidences.length; i++) {
                                evidences[i].split("=");
                                given.add(evidences[i].split("=")[0]);
                            }
//                            Node node = toNode(given.get(i), b);
                            if(ind(node,s1,given,b)){
                                ans="yes";
                            }
                            else{ ans="no";}
                            writer.write(ans);
                            if(read.hasNextLine()){
                                writer.write("\n");
                            }
                        }
                        else {
                            ArrayList<String> given = new ArrayList<String>();
                            if(ind(node,s1,given,b)){
                                ans="yes";
                            }
                            else{ ans="no";}
                            writer.write(ans);
                            if(read.hasNextLine()){
                                writer.write("\n");
                            }
                        }
                    }
                }
                read.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void find_fact(ArrayList<Factor> factors, String s,ArrayList<String>give) {
        int i=0;
        while (i< factors.size()){
            if (factors.get(i).variables.contains(s)) {
                factors.remove(i);
//                give.remove(i);
                i=0;
            }
            else i++;
        }
    }
}




