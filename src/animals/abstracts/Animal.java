package animals.abstracts;

import animals.Plant;
import island.Cell;
import island.Island;
import settings.Characteristic;
import settings.Config;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class Animal implements Eatable{
    private static Map<String,Integer> edibility;
    private static Map<Characteristic, Double> characteristic;
    {
        edibility = Config.getInstance()
                .getEdibility(this.getClass().getSimpleName());
        characteristic = Config.getInstance()
                .getCharacteristic(this.getClass().getSimpleName());
    }

    private Cell cell;
    private Double weight;
    private Double maxPopulation;
    private Double speed;
    private Double saturation;

    public  Boolean isAlive = true;
    public Boolean hadSex = false;
    public Boolean hadFood = false;
    public Boolean hadMove = false;


    public Animal(Class<?> type, Cell cell){
        this.cell = cell;
        this.weight = characteristic.get(Characteristic.WEIGHT);
        this.maxPopulation = characteristic.get(Characteristic.POPULATION);
        this.speed = characteristic.get(Characteristic.SPEED);
        this.saturation = characteristic.get(Characteristic.SATURATION) / 2; //Животное рождается с сытостью 50%
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Double getSaturation() {
        return saturation;
    }
    public void consume(Double saturation){
        this.saturation += saturation;
        if (this.saturation < 0)
            this.isAlive = false;
        if (this.saturation > characteristic.get(Characteristic.SATURATION))
            this.saturation = characteristic.get(Characteristic.SATURATION);
    }

    public Cell getCell() {
        return cell;
    }

    public Double getWeight() {
        return weight;
    }



    public void eat(){
        if (isAlive && !hadFood && saturation < characteristic.get(Characteristic.SATURATION)) {
            ThreadLocalRandom random = ThreadLocalRandom.current();

            Map<String, Integer> canEatInTeory = edibility.entrySet().stream()
                    .filter(e -> e.getValue() > 0)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            Eatable food = null;
            int luck = random.nextInt(101); //100%
            synchronized (cell) {
                Set<Eatable> canHunt = cell.getAnimalsYPlants().stream()
                        .filter(e -> canEatInTeory.containsKey(e.getClass().getSimpleName()))
                        .collect(Collectors.toSet());

                if (canHunt.size() > 0) {
                    food = (Eatable) canHunt.toArray()[random.nextInt(canHunt.size())];
                    if (luck >= canEatInTeory.get(food.getClass().getSimpleName())) {
                        if (food instanceof Animal){
                            Animal animal = (Animal) food;
                            animal.isAlive = false;
                        }else if(food instanceof Plant){
                            Plant plant = (Plant) food;
                            plant.isAlive = false;
                        }
                    } else {
                        food = null;
                    }
                }
            }
            if (!(food == null)) {
                this.consume(food.getWeight());
            }
        }
        this.hadFood = true;
    }


    public void reproduction(){ //все рожают по одному
        long populationNow = cell.animals.stream()
                .filter(e->e.getClass().equals(this.getClass()))
                .count();
        if (isAlive && !hadSex && (saturation >= characteristic.get(Characteristic.SATURATION))
            && populationNow < maxPopulation) {
            synchronized (this) {
                Animal partner = cell.animals.stream()
                        .filter(e->e.getClass().equals(this.getClass()))
                        .filter(e -> e.isAlive == true)
                        .filter(e -> e.hadSex == false)
                        .filter(e -> e.getSaturation() >= characteristic.get(Characteristic.SATURATION))
                        .findFirst().orElseThrow();
                if (partner != null) {
                    synchronized (partner) {
                        this.hadSex = true;
                        partner.hadSex = true;
                        try {
                            ThreadLocalRandom random = ThreadLocalRandom.current();
                            int rand = random.nextInt(1,10);
                            if (rand > 6){
                                Animal childAnimal = (Animal) this.getClass().getConstructors()[0].newInstance(this.getClass(), cell);
                                cell.animalChilds.add(childAnimal);
                                childAnimal.hadSex = true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /*
    *     1
    *     ↑
    * 4 ←   → 2
    *     ↓
    *     3
    */
    public void move(){
        if (isAlive && !hadMove) {
            Island island = Island.getInstance();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            boolean correct = false;
            int newX = cell.x;
            int newY = cell.y;
            while (!correct) {
                newX = cell.x;
                newY = cell.y;
                int direction = random.nextInt(1, 5);
                int distance = random.nextInt(speed.intValue() + 1);
                switch (direction) {
                    case 1 -> //↑
                            newY = cell.y - distance;
                    case 2 -> //→
                            newX = cell.x + distance;
                    case 3 -> //↓
                            newY = cell.y + distance;
                    case 4 -> //←
                            newX = cell.x - distance;
                }
                if (newX >= 0 && newX < island.IslandLENGTH
                        && newY >= 0 && newY < island.IslandWIDTH) {
                    correct = true;
                }
            }
            cell.animals.remove(this);
            Cell newCell = island.getCell(newX, newY);
            this.cell = newCell;
            newCell.animals.add(this);

            this.hadMove = true;
        }
    }
}
