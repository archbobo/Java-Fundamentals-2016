package ee.ut.jf2016.homework3;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Erdem on 14-Sep-16.
 */
public class BinaryWord implements Complementable<BinaryWord> {

   List<Boolean> word;

    public BinaryWord(List<Boolean> word) {
        this.word = word;
    }

    public BinaryWord(Boolean... word) {
        this.word = Arrays.asList(word);
    }



    @Override
    public BinaryWord complement() {
        return new BinaryWord(word.stream().map(element ->!element).collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BinaryWord)) return false;

        BinaryWord that = (BinaryWord) o;

        return word.equals(that.word);

    }

    @Override
    public String toString() {
        return word.toString();
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }
}
