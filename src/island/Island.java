package island;

import animals.abstracts.Animal;
import animals.abstracts.Eatable;
import animals.Plant;
import settings.Characteristic;
import settings.Config;
import settings.Utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public final class Island {
    private static Island instance;
    public final int IslandLENGTH;
    public final int IslandWIDTH;
    private Cell[][] cells;

    public static Island getInstance() {
        Island result = instance;
        if (result != null) {
            return result;
        }
        synchronized(Island.class) {
            if (instance == null) {
                instance = new Island();
                instance.initializeIsland();
            }
            return instance;
        }
    }

    private void initializeIsland(){
        Set<Class<?>> animals = Utils.getAnimalClassesFromFiles();
        animals.add(Plant.class);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < this.IslandLENGTH; i++) {
            for (int j = 0; j < this.IslandWIDTH; j++) {

                Cell cell = this.getCell(i,j);
                for (Class<?> type: animals) {
                    Double maxPopulation = Config.getInstance()
                            .getCharacteristic(type.getSimpleName())
                            .get(Characteristic.POPULATION);
                    int PopulationInCell = random.nextInt(maxPopulation.intValue()+1) / 2;
                    for (int k = 0; k < PopulationInCell; k++) {
                        try {
                            Eatable creatura = (Eatable) type.getConstructors()[0].newInstance(type, cell);
                            if (creatura instanceof Animal){
                                cell.animals.add((Animal) creatura);
                            }else if (creatura instanceof Plant){
                                cell.plants.add((Plant) creatura);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    private Island() {
        Config config = Config.getInstance();
        IslandLENGTH = config.getIslandLength();
        IslandWIDTH = config.getIslandWidth();
        cells = new Cell[IslandLENGTH][IslandWIDTH];
        for (int i = 0; i < IslandLENGTH; i++) {
            for (int j = 0; j < IslandWIDTH; j++) {
                cells[i][j] = new Cell(i,j);
            }
        }
    }
    public Cell getCell(int x, int y){
        return cells[x][y];
    }

    public List<Eatable> getAllAnimalsYPlantsFromIsland(){
        List<Eatable> list = new ArrayList<>();
        for (int i = 0; i < IslandLENGTH; i++) {
            for (int j = 0; j < IslandWIDTH; j++) {
                Cell cell = getCell(i,j);
                list.addAll(cell.animals);
                list.addAll(cell.plants);
            }
        }
        return list;
    }
    public List<Eatable> getAllAnimalsFromIsland(){
        List<Eatable> list = new ArrayList<>();
        for (int i = 0; i < IslandLENGTH; i++) {
            for (int j = 0; j < IslandWIDTH; j++) {
                Cell cell = getCell(i,j);
                list.addAll(cell.animals);
            }
        }
        return list;
    }

    public void drawStatistic(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(formatter.format(new Date(System.currentTimeMillis())));
        System.out.println("-------------------------------------------------------------------------------------");
        Set<Class<?>> animals = Utils.getAnimalClassesFromFiles();
        animals.add(Plant.class);
        List<Eatable> list = getAllAnimalsYPlantsFromIsland();
        for (Class<?> type: animals) {
            long count = list.stream().filter(x -> x.getClass().equals(type)).count();
            System.out.printf("На острове %d созданий типа %s",count, type.getSimpleName());
            System.out.print(System.lineSeparator());
        }
    }

}
