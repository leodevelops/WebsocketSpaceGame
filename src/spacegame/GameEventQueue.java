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
    private final List<Runnable> events;
    private final List<Runnable> newEvents;
    private ScheduledExecutorService executorService;

    public GameEventQueue() {
        events = new LinkedList<Runnable>();
        newEvents = new LinkedList<Runnable>();
        executorService = new ScheduledThreadPoolExecutor(4);
    }

    public void execute(Runnable event) {
        synchronized (events) {
            newEvents.add(event);
            events.notify();
        }
    }

    public void executeAll(Runnable[] events) {
        synchronized (this.events) {
            for(Runnable r : events) {
                this.newEvents.add(r);
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
            synchronized (events) {
                if(!newEvents.isEmpty()) {
                    events.addAll(newEvents);
                    newEvents.clear();
                }
                if(events.isEmpty()) {
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
