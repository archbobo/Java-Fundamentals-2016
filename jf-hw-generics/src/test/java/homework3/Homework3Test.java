package homework3;

import static org.junit.Assert.assertEquals;

import ee.ut.jf2016.homework3.BinaryWord;
import ee.ut.jf2016.homework3.HomeWork;
import ee.ut.jf2016.homework3.WholeNumber;
import org.junit.Test;

import javax.jws.Oneway;
import java.util.Arrays;
import java.util.List;

public class Homework3Test {

    @Test
    public void testBinary() {
        BinaryWord bw = new BinaryWord(true, false, false,true,false);
        BinaryWord bwEmpty = new BinaryWord();
        assertEquals(true, bw.complement().equals(new BinaryWord(false,true,true,false,true)));
        assertEquals(true, bw.complement().complement().equals(bw));
        assertEquals(true, bwEmpty.complement().equals(bwEmpty));


        WholeNumber wN = new WholeNumber(5);
        WholeNumber wnZ = new WholeNumber(0);
        assertEquals(true, wN.complement().equals(new WholeNumber(-5)));
        assertEquals(true, wN.complement().complement().equals(wN));
        assertEquals(true, wnZ.complement().equals(wnZ));

    }
    @Test
    public void testUnique() {


        List<Integer> a = Arrays.asList(1, 2, 3);
        List<Integer> b = Arrays.asList(2, 5, 7);
        List<Integer> c = Arrays.asList(3, 15, 7);
        List<Double> d = Arrays.asList(3.0, 5.0, 7.1);
        List<Double> de = Arrays.asList(4.0, 4.0, 8.1);
        List<String> s = Arrays.asList("ab", "bc");
        List<Number> uniqueValues = (List<Number>) HomeWork.unique(a,b,c,d,s, de);
        List<Object> expectedOut = Arrays.asList( 8.1,1, "ab", "bc", 5.0, 5, 3.0, 7.1, 15);
        assertEquals(true, expectedOut.containsAll(uniqueValues) && expectedOut.size()==uniqueValues.size()); // to check equality not considering the order, contains and same size
        assertEquals(true, expectedOut.containsAll(uniqueValues) && expectedOut.size()==uniqueValues.size()); // to check equality not considering the order, contains and same size

    }
}