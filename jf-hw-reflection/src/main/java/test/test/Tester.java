package test;
import main.Homework13;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import test_modules.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by erdem on 24.11.16.
 */
public class Tester {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    Homework13 h = null;
    @Rule
    public ExpectedException thrown= ExpectedException.none();
    @Before
    public void construct(){
        System.setOut(new PrintStream(outContent));
        h = new Homework13();
    }
    @After
    public void cleanUp(){
        System.setOut(null);
    }
    @Test
    public void testWithoutImplementing(){
        WithoutInterfaceClass obj = new WithoutInterfaceClass();
        thrown.expect(NoSuchMethodError.class);
        h.proxify(Runnable.class, obj).run();
    }
    @Test
    public void testSuperInterfaceWithImplementingSubInterface(){
        SubOfRunImpl obj = new SubOfRunImpl();
        h.proxify(subOfRun.class, obj).run();
        assertEquals("run is called", outContent.toString().trim());
    }
    @Test
    public void testInterfaceWithImplementingTheInterface(){
        SubOfRunImpl obj = new SubOfRunImpl();
        h.proxify(subOfRun.class, obj).run2(4);
        assertEquals("run2 is called with 4", outContent.toString().trim());
    }

    @Test
    public void testSetterWithoutImplemetingTheInterface(){
        SetterGetterNotmpl obj = new SetterGetterNotmpl(); // This object does not implement the interface
        h.proxify(SetterGetter.class, obj).setFieldX(4);
        assertEquals(4, obj.fieldX);
    }
    @Test
    public void testGetterWithoutImplemetingTheInterface(){
        SetterGetterNotmpl obj = new SetterGetterNotmpl(12); // This object does not implement the interface
        assertEquals(12, h.proxify(SetterGetter.class, obj).getFieldX()) ;
    }

    @Test
    public void testIfItChecksTheFieldTypeWhileSetting(){
        thrown.expect(NoSuchMethodError.class);
        SetterGetterNotImpWithFakeField obj = new SetterGetterNotImpWithFakeField(); // This object has fieldX but it is a String
        h.proxify(SetterGetter.class, obj).setFieldX(12);
    }
    @Test
    public void testIfItChecksTheFieldTypeWhileGetting(){
        thrown.expect(NoSuchMethodError.class);
        SetterGetterNotImpWithFakeField obj = new SetterGetterNotImpWithFakeField(); // This object has fieldX but it is a String
        h.proxify(SetterGetter.class, obj).getFieldX();
    }
    @Test
    public void testDeptecatedThrowsError(){
        thrown.expect(NoSuchMethodError.class);
        SubOfRunImpl obj = new SubOfRunImpl();
        h.proxify(subOfRun.class, obj).run3();// This is Deprecated in obj.class
    }
    @Test
    public void testSuperFieldWorkswithSetter(){
        WithoutInterfaceClass obj = new WithoutInterfaceClass();
        h.proxify(SetterGetter.class, obj).setSuperField(10);
        assertEquals(10, obj.superField);
    }

    @Test
    public void testSuperFieldWorkswithGetter(){
        WithoutInterfaceClass obj = new WithoutInterfaceClass();
        obj.superField = 10;
        int out = h.proxify(SetterGetter.class, obj).getSuperField();
        assertEquals(10, out);
    }
    @Test
    public void testprivateSuperFieldThrowsException(){
        thrown.expect(NoSuchMethodError.class);
        WithoutInterfaceClass obj = new WithoutInterfaceClass();
        h.proxify(SetterGetter.class, obj).getPrivateSuperField();// This is Deprecated in obj.class
    }
}
