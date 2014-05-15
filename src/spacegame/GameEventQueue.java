package spacegame;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Leo on 14/05/2014.
 */
public class GameEventQueue implements Runnable {
    private List<Runnable> events;
    private ScheduledExecutorService executorService;

    public GameEventQueue() {
        events = new LinkedList<Runnable>();
        executorService = new ScheduledThreadPoolExecutor(4);
    }

    public void addEvent(Runnable event) {
        synchronized (events) {
            events.add(event);
        }
    }

    public void execute(Runnable event) {
        addEvent(event);
    }

    public ScheduledFuture<?> schedule(final Runnable event, long delay, TimeUnit timeUnit) {
        Runnable injector = new Runnable() {
            @Override
            public void run() {
                GameEventQueue.this.addEvent(event);
            }
        };
        return executorService.schedule(injector, delay, timeUnit);
    }

    @Override
    public void run() {
        while(true) {
            synchronized (events) {
                if (events.isEmpty()) {
                    try {
                        events.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Iterator<Runnable> it = events.iterator();
                    while (it.hasNext()) {
                        it.next().run();
                        it.remove();
                    }
                }
            }
        }
    }
}
