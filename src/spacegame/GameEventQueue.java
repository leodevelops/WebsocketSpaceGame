package spacegame;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Leo on 14/05/2014.
 */
public class GameEventQueue implements Runnable {
    private final Queue<Runnable> events;
    private final ScheduledExecutorService executorService;
    private final Lock lock;
    private final Condition eventsEmpty;

    public GameEventQueue() {
        events = new LinkedList<Runnable>();
        executorService = new ScheduledThreadPoolExecutor(4);
        lock = new ReentrantLock();
        eventsEmpty = lock.newCondition();
    }

    public void execute(Runnable event) {
        lock.lock();
        try {
            events.add(event);
            eventsEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void executeAll(Runnable[] events) {
        lock.lock();
        try {
            for(Runnable r : events) {
                this.events.add(r);
            }
            eventsEmpty.signal();
        } finally {
            lock.unlock();
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
            lock.lock();
            try {
                while(events.isEmpty()) {
                    eventsEmpty.await();
                }
                events.remove().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
