package spacegame;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Leo on 14/05/2014.
 */
public class GameEventQueue implements Runnable {
    private final Queue<Runnable> events;
    private ScheduledExecutorService executorService;

    public GameEventQueue() {
        events = new LinkedList<Runnable>();
        executorService = new ScheduledThreadPoolExecutor(4);
    }

    public void execute(Runnable event) {
        synchronized (events) {
            events.add(event);
            events.notify();
        }
    }

    public void executeAll(Runnable[] events) {
        synchronized (this.events) {
            for(Runnable r : events) {
                this.events.add(r);
            }
            this.events.notify();
        }
    }

    public ScheduledFuture<?> schedule(final Runnable event, long delay, TimeUnit timeUnit) {
        Runnable injector = new Runnable() {
            @Override
            public void run() {
                GameEventQueue.this.execute(event);
            }
        };
        return executorService.schedule(injector, delay, timeUnit);
    }

    public ScheduledFuture<?> scheduleRepeat(final Runnable event, long delay, TimeUnit timeUnit) {
        Runnable injector = new Runnable() {
            @Override
            public void run() {
                GameEventQueue.this.execute(event);
            }
        };
        return executorService.scheduleWithFixedDelay(injector, delay, delay, timeUnit);
    }

    @Override
    public void run() {
        while(true) {
            synchronized(events) {
                if(events.isEmpty()) {
                    try {
                        events.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    events.remove().run();
                }
            }
        }
    }
}
