package island;

import animals.abstracts.Animal;
import animals.abstracts.Eatable;
import animals.Plant;
import settings.Characteristic;
import settings.Config;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Cell implements Runnable {
    public final int x;
    public final int y;
    public int isDesert = 0;

    public volatile CopyOnWriteArrayList<Animal> animals = new CopyOnWriteArrayList<>();
    public volatile CopyOnWriteArrayList<Animal> animalChilds = new CopyOnWriteArrayList<>();
    public volatile CopyOnWriteArrayList<Plant> plants = new CopyOnWriteArrayList<>();


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public void run(){
        growPlants();

        animals.forEach(e -> {
            e.hadSex = false;
            e.hadMove = false;
            e.hadFood = false;
        });
        animals.forEach(Animal::move);
        animals.forEach(Animal::eat);
        animals.forEach(Animal::reproduction);
        animals.forEach(e -> e.consume(0-Config.getInstance().characteristic
                        .get(e.getClass().getSimpleName())
                        .get(Characteristic.SATURATION) * 0.1));

        animals.removeAll(animals.stream()
                .filter(e -> !e.isAlive)
                .filter(e -> e.getSaturation() < 0).toList());
        plants.removeAll(plants.stream()
                .filter(e -> !e.isAlive).toList());
        animals.addAll(animalChilds);
        animalChilds.clear();


    }

    private void growPlants(){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int maxPopulation = Config.getInstance()
                .getCharacteristic(Plant.class.getSimpleName())
                .get(Characteristic.POPULATION)
                .intValue();

        for (int k = 0; k < random.nextInt(maxPopulation); k++) {
            if (plants.size() < maxPopulation) {
                plants.add(new Plant(this));
            }
        }
    }


    public HashSet<Eatable> getAnimalsYPlants(){
        HashSet<Eatable> set = new HashSet<>();
        set.addAll(animals);
        set.addAll(plants);
        return set;
    }

}
