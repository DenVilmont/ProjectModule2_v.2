package settings;

import animals.abstracts.Herbivore;
import animals.abstracts.Predator;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class Utils {
    private static Set<Class<?>> loadChildClasses(String packageName, Class<?> parentType) throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        URL resource = Thread.currentThread()
                .getContextClassLoader()
                .getResource(packageName.replace('.', '/'));
        File directory = new File(resource.getFile());
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return classes;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                Class<?> classFile = Class.forName(String.format("%s.%s", packageName, file.getName().substring(0, file.getName().indexOf("."))));
                if (!(classFile.getSuperclass() == null) && classFile.getSuperclass().equals(parentType) ){
                    classes.add(classFile);
                }
            }else if(file.isDirectory()){
                Set<Class<?>> classesTemp = loadChildClasses(String.format("%s.%s", packageName, file.getName()), parentType);
                if (classesTemp.size() > 0){
                    classes.addAll(classesTemp);
                }
            }
        }
        return classes;
    }

    public static Set<Class<?>> getAnimalClassesFromFiles() {
        Set<Class<?>> animals = new HashSet<>();
        try {
            animals.addAll(loadChildClasses("animals", Herbivore.class));
            animals.addAll(loadChildClasses("animals", Predator.class));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animals;
    }


}
