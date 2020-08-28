package branoperty.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static branoperty.App.logger;

public class FileHandler {
    /**
     * Write one or more objects to disk using Java's Serialization API.
     * Objects passed in must implement {@code Serializable} otherwise it
     * will fail.
     *
     * @param filename the name of the file to serialize object to.
     * @param object   the object(s) to serialize and store on disk.
     */
    public static void writeObjsToFile(String filename, Object... object) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));

            for (Object obj : object) {
                oos.writeObject(obj);
            }

            oos.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unexpected IO Error");
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Read all the objects from a file on disk using Java's Serialization API
     * and Java IO methods.
     *
     * @param filename the name of the file to deserialize objects from.
     * @return a {@code List} of the objects from the file.
     */
    public static List<Object> readObjsFromFile(String filename) {
        List<Object> objects = new ArrayList<>();

        try {
            final ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(filename));
            Object obj = null;

            while ((obj = OIS.readObject()) != null) {
                objects.add(obj);
            }

            OIS.close();
        } catch (EOFException e) {
            logger.log(Level.INFO, String.format("EOF Reached. Retrieved %s objects from file", objects.size()));
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "The class being read is unavailable.");
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Unable to find the specified file.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unexpected IO Error");
            logger.log(Level.SEVERE, e.getMessage());
        }

        return objects;
    }

    /**
     * Read all the objects from a file on disk using Java's Serialization API
     * and Java IO methods and return the first item in the list of returned
     * objects.
     *
     * @param filename the name of the file to deserialize objects from.
     * @return the first {@code Object} from the specified file.
     */
    public static Object readObjFromFile(String filename) {
        List<Object> objects = readObjsFromFile(filename);

        return objects.size() > 0 ? objects.get(0) : null;
    }
}
