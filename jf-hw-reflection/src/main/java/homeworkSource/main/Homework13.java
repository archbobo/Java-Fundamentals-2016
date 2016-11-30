package main;

import org.apache.commons.lang3.ClassUtils;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by erdem on 23.11.16.
 */
public class Homework13 {
    private <T> ArrayList<Field> collectAllFieldsRecursively(Class<T> classObject){
        ArrayList<Field> allmethods = new ArrayList<>(Arrays.asList(classObject.getDeclaredFields()));
        Class<?> superclass = classObject.getSuperclass();
        while(superclass != null) {
            Stream.of(superclass.getDeclaredFields()).filter(field -> !Modifier.isPrivate(field.getModifiers())).forEach(allmethods::add);
            superclass = superclass.getSuperclass();
        }
        return allmethods;
    }

    private boolean areTheySameMethods(Method m1, Method m2){
        boolean namesEqual  = m1.getName().equals(m2.getName());
        boolean parametersEqual = Arrays.equals(m1.getParameterTypes(), m2.getParameterTypes());
        boolean exceptionsEqual = Arrays.equals(m1.getExceptionTypes(),(m2.getExceptionTypes()));
        boolean retTypesequal = m1.getReturnType().equals(m2.getReturnType());
        boolean bothPublic = Modifier.isPublic(m1.getModifiers()) && Modifier.isPublic(m2.getModifiers());
        boolean ret = namesEqual && parametersEqual && exceptionsEqual && retTypesequal  && bothPublic;
        return ret;
    }
    private boolean fieldAndMethodMatches(Method accessor, Field f){
        if (!accessor.getName().matches("^(s|g)et.+")) return false;
        String possibleFieldNameWithCapitalStart = accessor.getName().substring(3);
        char c[] = possibleFieldNameWithCapitalStart.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        String possibleFieldName = new String(c);
        return
                f.getName().equals(possibleFieldName) &&
               (
                    accessor.getName().startsWith("set") &&
                    accessor.getParameterTypes().length == 1 &&
                    accessor.getParameterTypes()[0].equals(f.getType()) &&
                    accessor.getReturnType().equals(void.class)
                    ||
                    accessor.getName().startsWith("get") &&
                    accessor.getParameterTypes().length == 0 &&
                    accessor.getReturnType().equals(f.getType())
               );

    }


    public <T> T proxify(Class<T> iface, final Object obj) {
        ClassLoader classLoader = iface.getClassLoader();
        Class[] interfaces = new Class[] { iface };
        InvocationHandler invocationHandler = (proxy, m, args) -> {
            Method[] allmethods = obj.getClass().getMethods();
            Method objectMethod =  Stream.of(allmethods).filter(belongedMethod -> areTheySameMethods(m, belongedMethod)).findFirst().orElse(null);
            if (objectMethod != null && !objectMethod.isAnnotationPresent(Deprecated.class)) return objectMethod.invoke(obj, args);
            List<Field> allFields = collectAllFieldsRecursively(obj.getClass());
                     Field theField = allFields.stream().filter(f -> fieldAndMethodMatches(m,f)).findFirst().orElse(null);
            if(theField != null && m.getName().startsWith("get")) return theField.get(obj);
            if(theField != null && m.getName().startsWith("set") && args.length ==1 && ClassUtils.isAssignable(args[0].getClass(),theField.getType())){
                theField.set(obj, args[0]);
                return Void.TYPE;
            }
            throw new NoSuchMethodError();
        };
        return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }

}
