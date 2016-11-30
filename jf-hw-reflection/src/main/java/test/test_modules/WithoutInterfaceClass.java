package test_modules;


/**
 * Created by erdem on 23.11.16.
 */
public class WithoutInterfaceClass extends MySuperClass {

    int a;
    int erdem;
    String s;

    public void run2(Integer p) {
        System.out.println("Called with" +p);
    }

    public synchronized int run3() {
        return 0;
    }

}
