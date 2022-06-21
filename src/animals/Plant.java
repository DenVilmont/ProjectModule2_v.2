package animals;

import animals.abstracts.Eatable;
import island.Cell;
import settings.Characteristic;
import settings.Config;

import java.util.Map;

public class Plant implements Eatable {
    private Cell cell;
    Map<Characteristic, Double> characteristic;
    private Double weight;
    private Double population;
    private Double vegetation;
    public Boolean isAlive = true;


    public Plant(Class<?> type, Cell cell){
        this(cell);
    }
    public Plant(Cell cell){
        this.characteristic = Config.getInstance()
                .getCharacteristic(this.getClass().getSimpleName());
        this.weight = characteristic.get(Characteristic.WEIGHT);
        this.population = characteristic.get(Characteristic.POPULATION);
        this.vegetation = characteristic.get(Characteristic.VEGETATION);
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getPopulation() {
        return population;
    }

    public Double getVegetation() {
        return vegetation;
    }


}
