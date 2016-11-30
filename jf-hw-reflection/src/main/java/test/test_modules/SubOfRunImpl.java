package test_modules;

/**
 * Created by erdem on 24.11.16.
 */
public class SubOfRunImpl implements subOfRun {
    @Override
    public void run2(Integer p) {
        System.out.println("run2 is called with "+ p);
    }

    @Override
    @Deprecated
    public int run3() {
        System.out.println("run3 is called");
        return 0;
    }

    @Override
    public void run() {
        System.out.println("run is called");

    }
}
