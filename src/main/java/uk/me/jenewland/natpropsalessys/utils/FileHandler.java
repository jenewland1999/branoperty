package uk.me.jenewland.natpropsalessys.utils;

import uk.me.jenewland.natpropsalessys.Main;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandler
{
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void writeObjsToFile(String filename, Object... object)
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));

            for (Object obj : object)
            {
                oos.writeObject(obj);
            }

            oos.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unexpected IO Error");
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public static List<Object> readObjsFromFile(String filename)
    {
        List<Object> objects = new ArrayList<>();

        try {
            final ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(filename));
            Object obj = null;

            while ((obj = OIS.readObject()) != null) {
                objects.add(obj);
            }

            OIS.close();
        } catch (EOFException e) {
            LOGGER.log(Level.INFO, String.format("EOF Reached. Retrieved %s objects from file", objects.size()));
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "The class being read is unavailable.");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Unable to find the specified file.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unexpected IO Error");
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        return objects;
    }

    public static Object readObjFromFile(String filename)
    {
        List<Object> objects = readObjsFromFile(filename);

        if (objects.size() > 0) {
            return objects.get(0);
        }

        return null;
    }
}
