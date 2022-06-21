import island.Island;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Island island = Island.getInstance();

    public static void main(String[] args) {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Round(), 1,5, TimeUnit.SECONDS);



    }


}
