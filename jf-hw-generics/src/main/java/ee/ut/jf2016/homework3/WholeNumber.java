package ee.ut.jf2016.homework3;


/**
 * Created by Erdem on 15-Sep-16.
 */
public class WholeNumber implements Complementable<WholeNumber> {


    private Integer number;

    public WholeNumber(Integer number) {
        this.number = number;
    }

    @Override
    public WholeNumber complement() {
       return new WholeNumber(this.number*-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WholeNumber)) return false;

        WholeNumber that = (WholeNumber) o;

        return number.equals(that.number);

    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
