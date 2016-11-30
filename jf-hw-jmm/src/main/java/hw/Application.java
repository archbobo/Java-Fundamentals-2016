package hw;

import java.lang.ref.WeakReference;

public class Application {

    public static void main(String[] args) throws Exception {
        PostCollectionTaskRunner runner = new PostCollectionTaskRunnerImpl();
        try {
            Object soonToBeGarbage = new Object();
            runner.register(soonToBeGarbage, () -> System.out.println("task1 executed"));
            runner.register(soonToBeGarbage, () -> System.out.println("task1 executed"));
            soonToBeGarbage = null;

            causeGarbageCollection();
        } finally {
         runner.shutdown();
        }
    }

    private static void causeGarbageCollection() {
        Object obj = new Object();
        WeakReference<Object> wRef = new WeakReference<>(obj);
        obj = null;
        while (wRef.get() != null){
           System.gc();
        }
    }
}
