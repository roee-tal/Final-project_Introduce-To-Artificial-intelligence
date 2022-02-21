import java.util.Comparator;

public class Comparing implements Comparator<Factor> {

    @Override
    public int compare(Factor f1, Factor f2) {
        String s1 = "";
        String s2 = "";
        int c1=0;
        int c2=0;
        for (int i = 0; i < f1.variables.size(); i++) {
            s1 = s1 + f1.variables.get(i);
        }
        for (int i = 0; i < s1.length(); i++) {
            c1=c1+(int)s1.charAt(i);
        }

        for (int i = 0; i < f2.variables.size(); i++) {
            s2 = s2 + f2.variables.get(i);
        }
        for (int i = 0; i < s2.length(); i++) {
            c2=c2+(int)s2.charAt(i);
        }
        if(c1>c2) return 1;
        else if(c1<c2) return -1;
        else return 0;
    }
}
