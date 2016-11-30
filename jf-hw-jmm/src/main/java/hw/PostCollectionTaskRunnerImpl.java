package hw;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 *
 * [GC (System.gc()) [PSYoungGen: 6554K->816K(76288K)] 6554K->824K(251392K), 0.0011065 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 *
 * Minor gc =>
 * Young generation had 6554K before the collection and after it has 816K total allowed space is 76288K
 * Total heap memory  => from 6554K to 824K  total allowed space is 251392K
 * And the duration is 0.00 secs total allowed space is 76288K
 *
 [Full GC (System.gc()) [PSYoungGen: 816K->0K(76288K)] [ParOldGen: 8K->596K(175104K)] 824K->596K(251392K), [Metaspace: 4151K->4151K(1056768K)], 0.0071889 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 *
 * It is a FullFC
 * PSYoungGen was 800K  and cleared to 0K total allowed space is 76288K
 * Old generation increased from 8K to 596K total allowed space is 175104K
 * Total heap space usage decreased from 824K > 596K total allowed space is 251392K
 * it took 0.01 secs

 */
public class PostCollectionTaskRunnerImpl implements PostCollectionTaskRunner {
    ExecutorService executorService;
    AtomicBoolean blockingAllowed;
    AtomicBoolean runningActively;
    ReferenceQueue<Object> queue;
    HashMap<Integer, PhantomReference<Object>> objectToPhantom;
    HashMap<PhantomReference<Object>, List<Runnable>> phantomToTasks;

    private Boolean isShutDownCalled = false;

    public PostCollectionTaskRunnerImpl() {
        executorService = Executors.newFixedThreadPool(50);
        objectToPhantom = new HashMap<>();
        phantomToTasks = new HashMap<>();
        queue = new ReferenceQueue<>();
        blockingAllowed = new AtomicBoolean(true);
        runningActively = new AtomicBoolean(false);
        executorService.execute(this::waitForGarbageCollectionAndRun);
    }

    @Override
    public void register(Object o, Runnable task) {
        if(isShutDownCalled) throw new IllegalAccessError("This Service is shutdown");
        Integer hash = o.hashCode();
        PhantomReference<Object> phantom;
        if (objectToPhantom.containsKey(hash)){
             phantom = objectToPhantom.get(hash);
        }
        else{
            phantom = new PhantomReference<>(o,queue);
            objectToPhantom.put(o.hashCode(), phantom);
        }
        phantomToTasks.putIfAbsent(phantom, new ArrayList<>());
        phantomToTasks.get(phantom).add(task);
    }
    private void waitForGarbageCollectionAndRun(){
        try {
            Reference<?> collected;
            while(true){
                collected = queue.remove();// blocking here
                blockingAllowed.set(true); // blocking
                if (phantomToTasks.containsKey(collected)){
                    runningActively.set(true); // running a task started
                    phantomToTasks.remove(collected).forEach(executorService::execute);
                    objectToPhantom.values().remove(collected);
                    runningActively.set(false);// running the task is over
                }
            }

        } catch (InterruptedException e) {
            //System.out.println("Time Interrupt");
        }
    }

    /**
     * Here, isShutdownCalled is set to true in order to make new registry impossible,
     *
     * In the loop we basically let the taskRunner to be blocked in the queue for maximum 30 milliseconds.
     * Whenever it receives a Phantom Object the time counter is reset,
     *
     * Also while tasks are being run, we have to let it be run by the thread-pool.
     * I had to use this approach, otherwise if all the registered objects are not garbage collected, this would be blocked forever.
     * /
     * @throws Exception
     */
    @Override
    public void shutdown() throws Exception {
        isShutDownCalled = true;
        while (blockingAllowed.get()){ // while the  queue is allowed to be blocked
            blockingAllowed.set(false); // you are no more allowed to be blocked
            Thread.sleep(30); // waiting for queue to be unblocked and set it to true back.
            while(runningActively.get());// are the tasks are being sent to executor?
                                         // if at this point queue has not reset the blockingAllowed to true
        }
        executorService.shutdownNow(); //then we waited enough, kill that bastard!
        phantomToTasks.clear();
        objectToPhantom.clear();
        queue= null;
        blockingAllowed = null;
        executorService = null;
    }
}
