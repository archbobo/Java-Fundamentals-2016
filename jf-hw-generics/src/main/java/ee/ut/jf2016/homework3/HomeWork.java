package ee.ut.jf2016.homework3;



import java.util.*;

/**
 * Created by Erdem on 14-Sep-16.
 */
public class HomeWork {
    static Map<Object, Boolean> countOfThem = new HashMap<>();
    public static List<? extends Object> unique(List<? extends Object>... lists){
        List<Object> things = new ArrayList<>();
        Arrays.asList(lists).forEach(list -> list.forEach(HomeWork::addToCounter));
        countOfThem.forEach((K,V) ->{ if(V) things.add(K);});
        return things;
}

    public static void addToCounter(Object o){
        if(countOfThem.containsKey(o)) countOfThem.put(o, false);
        countOfThem.putIfAbsent(o,true);
    }
}
