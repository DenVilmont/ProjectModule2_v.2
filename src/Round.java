import island.Island;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Round implements Runnable{

    public void run(){

        Island island = Island.getInstance();

        ExecutorService executorService = Executors.newFixedThreadPool(6);

        for (int i = 0; i < island.IslandLENGTH; i++) {
            for (int j = 0; j < island.IslandWIDTH; j++) {
                executorService.execute(island.getCell(i,j));
            }
        }

        executorService.shutdown();
        island.drawStatistic();
    }
}
