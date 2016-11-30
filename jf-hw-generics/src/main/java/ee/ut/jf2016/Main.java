package ee.ut.jf2016;


import ee.ut.jf2016.homework3.HomeWork;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = Arrays.asList(2, 5, 7);
        List<Integer> c = Arrays.asList(3, 15, 7);
        List<Double> d = Arrays.asList(3.0, 5.0, 7.1);
        List<String> s = Arrays.asList("ab", "bc");
        List<Number> compiles = (List<Number>) HomeWork.unique(a,b,c,d,s);
        System.out.println(compiles.toString());
    }
}
