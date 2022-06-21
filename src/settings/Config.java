package settings;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public final class Config {
    private static volatile Config instance;

    public static Config getInstance() {
        Config result = instance;
        if (result != null) {
            return result;
        }
        synchronized(Config.class) {
            if (instance == null) {
                instance = new Config();
                try(InputStream inputStream = new FileInputStream("./src/settings/properties.yml")){
                    Yaml yaml = new Yaml(new Constructor(Config.class));
                    instance = (Config) yaml.load(inputStream);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return instance;
        }
    }

    private Config(){
    }
    public void saveConfig(){
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        try(PrintWriter writer = new PrintWriter("./src/settings/properties.yml")) {
            yaml.dump(this, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //характеристики острова
    public int islandLength = 100;
    public int islandWidth = 20;

    //перенос таблицы - вероятность съедания, в параметры объектов
    public Map<String, Map<String, Integer>> edibility;
    /*private void initializeEdibility(){
        edibility = Map.ofEntries(
                entry("Wolf", new HashMap<>()),
                entry("Boa", new HashMap<>()),
                entry("Fox", new HashMap<>()),
                entry("Bear", new HashMap<>()),
                entry("Eagle", new HashMap<>()),
                entry("Horse", new HashMap<>()),
                entry("Deer", new HashMap<>()),
                entry("Rabbit", new HashMap<>()),
                entry("Mouse", new HashMap<>()),
                entry("Goat", new HashMap<>()),
                entry("Sheep", new HashMap<>()),
                entry("Boar", new HashMap<>()),
                entry("Buffalo", new HashMap<>()),
                entry("Duck", new HashMap<>()),
                entry("Caterpillar", new HashMap<>())
        );

                edibility.get("Wolf").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 10),
                entry("Deer", 15),
                entry("Rabbit", 60),
                entry("Mouse", 80),
                entry("Goat", 60),
                entry("Sheep", 70),
                entry("Boar", 15),
                entry("Buffalo", 10),
                entry("Duck", 40),
                entry("Caterpillar", 0),
                entry("Plant", 0)
        ));
        edibility.get("Boa").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 15),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 20),
                entry("Mouse", 40),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 10),
                entry("Caterpillar", 0),
                entry("Plant", 0)
        ));
        edibility.get("Fox").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 70),
                entry("Mouse", 90),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 60),
                entry("Caterpillar", 40),
                entry("Plant", 0)
                ));
        edibility.get("Bear").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 80),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 40),
                entry("Deer", 80),
                entry("Rabbit", 80),
                entry("Mouse", 90),
                entry("Goat", 70),
                entry("Sheep", 70),
                entry("Boar", 50),
                entry("Buffalo", 20),
                entry("Duck", 10),
                entry("Caterpillar", 0),
                entry("Plant", 0)
                ));
        edibility.get("Eagle").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 10),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 90),
                entry("Mouse", 90),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 80),
                entry("Caterpillar", 0),
                entry("Plant", 0)
                ));
        edibility.get("Horse").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 0),
                entry("Mouse", 0),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 0),
                entry("Caterpillar", 0),
                entry("Plant", 100)
                ));
        edibility.get("Deer").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 0),
                entry("Mouse", 0),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 0),
                entry("Caterpillar", 0),
                entry("Plant", 100)
                ));
        edibility.get("Rabbit").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 0),
                entry("Mouse", 0),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 0),
                entry("Caterpillar", 0),
                entry("Plant", 100)
                ));
        edibility.get("Mouse").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 0),
                entry("Mouse", 0),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 0),
                entry("Caterpillar", 90),
                entry("Plant", 100)
                ));
        edibility.get("Goat").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 0),
                entry("Mouse", 0),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 0),
                entry("Caterpillar", 0),
                entry("Plant", 100)
                ));
        edibility.get("Sheep").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 0),
                entry("Mouse", 0),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 0),
                entry("Caterpillar", 0),
                entry("Plant", 100)
                ));
        edibility.get("Boar").putAll(Map.ofEntries(entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 0),
                entry("Mouse", 50),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 0),
                entry("Caterpillar", 90),
                entry("Plant", 100)
                ));
        edibility.get("Buffalo").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 0),
                entry("Mouse", 0),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 0),
                entry("Caterpillar", 0),
                entry("Plant", 100)
                ));
        edibility.get("Duck").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 0),
                entry("Mouse", 0),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 0),
                entry("Caterpillar", 90),
                entry("Plant", 100)
                ));
        edibility.get("Caterpillar").putAll(Map.ofEntries(
                entry("Wolf", 0),
                entry("Boa", 0),
                entry("Fox", 0),
                entry("Bear", 0),
                entry("Eagle", 0),
                entry("Horse", 0),
                entry("Deer", 0),
                entry("Rabbit", 0),
                entry("Mouse", 0),
                entry("Goat", 0),
                entry("Sheep", 0),
                entry("Boar", 0),
                entry("Buffalo", 0),
                entry("Duck", 0),
                entry("Caterpillar", 0),
                entry("Plant", 100)
                ));

    }*/

    //перенос таблицы характеристик в параметры объектов
    public Map<String, Map<Characteristic, Double>> characteristic;
    /*private void initializeCharacteristic(){
        characteristic = Map.ofEntries(
                entry("Wolf", new HashMap<>()),
                entry("Boa", new HashMap<>()),
                entry("Fox", new HashMap<>()),
                entry("Bear", new HashMap<>()),
                entry("Eagle", new HashMap<>()),
                entry("Horse", new HashMap<>()),
                entry("Deer", new HashMap<>()),
                entry("Rabbit", new HashMap<>()),
                entry("Mouse", new HashMap<>()),
                entry("Goat", new HashMap<>()),
                entry("Sheep", new HashMap<>()),
                entry("Boar", new HashMap<>()),
                entry("Buffalo", new HashMap<>()),
                entry("Duck", new HashMap<>()),
                entry("Caterpillar", new HashMap<>()),
                entry("Plant", new HashMap<>())
        );
        characteristic.get("Wolf").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 50d),
                entry(Characteristic.POPULATION, 30d),
                entry(Characteristic.SPEED, 3d),
                entry(Characteristic.SATURATION, 8d)
        ));
        characteristic.get("Boa").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 15d),
                entry(Characteristic.POPULATION, 30d),
                entry(Characteristic.SPEED, 1d),
                entry(Characteristic.SATURATION, 3d)
        ));
        characteristic.get("Fox").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 8d),
                entry(Characteristic.POPULATION, 30d),
                entry(Characteristic.SPEED, 2d),
                entry(Characteristic.SATURATION, 2d)
        ));
        characteristic.get("Bear").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 500d),
                entry(Characteristic.POPULATION, 5d),
                entry(Characteristic.SPEED, 2d),
                entry(Characteristic.SATURATION, 80d)
        ));
        characteristic.get("Eagle").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 6d),
                entry(Characteristic.POPULATION, 20d),
                entry(Characteristic.SPEED, 3d),
                entry(Characteristic.SATURATION, 1d)
        ));
        characteristic.get("Horse").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 400d),
                entry(Characteristic.POPULATION, 20d),
                entry(Characteristic.SPEED, 4d),
                entry(Characteristic.SATURATION, 60d)
        ));
        characteristic.get("Deer").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 300d),
                entry(Characteristic.POPULATION, 20d),
                entry(Characteristic.SPEED, 4d),
                entry(Characteristic.SATURATION, 50d)
        ));
        characteristic.get("Rabbit").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 2d),
                entry(Characteristic.POPULATION, 150d),
                entry(Characteristic.SPEED, 2d),
                entry(Characteristic.SATURATION, 0.45d)
        ));
        characteristic.get("Mouse").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 0.05d),
                entry(Characteristic.POPULATION, 500d),
                entry(Characteristic.SPEED, 1d),
                entry(Characteristic.SATURATION, 0.01d)
        ));
        characteristic.get("Goat").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 60d),
                entry(Characteristic.POPULATION, 140d),
                entry(Characteristic.SPEED, 3d),
                entry(Characteristic.SATURATION, 10d)
        ));
        characteristic.get("Sheep").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 70d),
                entry(Characteristic.POPULATION, 140d),
                entry(Characteristic.SPEED, 3d),
                entry(Characteristic.SATURATION, 15d)
        ));
        characteristic.get("Boar").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 400d),
                entry(Characteristic.POPULATION, 50d),
                entry(Characteristic.SPEED, 2d),
                entry(Characteristic.SATURATION, 50d)
        ));
        characteristic.get("Buffalo").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 700d),
                entry(Characteristic.POPULATION, 10d),
                entry(Characteristic.SPEED, 3d),
                entry(Characteristic.SATURATION, 100d)
        ));
        characteristic.get("Duck").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 1d),
                entry(Characteristic.POPULATION, 200d),
                entry(Characteristic.SPEED, 4d),
                entry(Characteristic.SATURATION, 0.15d)
        ));
        characteristic.get("Caterpillar").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 0.01d),
                entry(Characteristic.POPULATION, 50d),
                entry(Characteristic.SPEED, 0d),
                entry(Characteristic.SATURATION, 0d)
        ));
        characteristic.get("Plant").putAll(Map.ofEntries(
                entry(Characteristic.WEIGHT, 1d),
                entry(Characteristic.POPULATION, 200d),
                entry(Characteristic.VEGETATION, 100d)
        ));

    }*/


    public Map<Characteristic, Double> getCharacteristic(String animalName){
        return characteristic.get(animalName);
    }
    public Map<String, Integer> getEdibility(String animalName){
        return edibility.get(animalName);
    }
    public int getIslandLength(){return islandLength;}
    public int getIslandWidth(){return islandWidth;}
}
