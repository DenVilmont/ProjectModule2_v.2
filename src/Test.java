import animals.herbivores.Boar;
import animals.predators.Boa;
import island.Cell;
import settings.Config;

public class Test {
    public static void main(String[] args) {
        Config config = Config.getInstance();
        System.out.println(config.getCharacteristic("Boar"));
        Cell cell = new Cell(1,1);
        Boar boar = new Boar(Boar.class, cell);
        System.out.println(boar.getWeight());
        Boa boa = new Boa(Boa.class, cell);
        System.out.println(boa.getWeight());
        System.out.println(boar.getWeight());
    }
}
