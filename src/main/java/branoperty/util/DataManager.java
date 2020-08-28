package branoperty.util;

import branoperty.model.IModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static branoperty.App.logger;

public class DataManager {
    private final Path PATH;

    /**
     * DataManager constructor.
     *
     * @param path the directory path, as a str, where you want data to be stored.
     */
    public DataManager(String path) {
        PATH = Paths.get(path);
    }

    /**
     * Serialize an instance of IModel and store it to disk. The filename is
     * created using the model {@code toString()} method with the {@code .dat}
     * file extension.
     *
     * @param model the model to serialise and store on disk.
     */
    public void create(IModel model) {
        FileHandler.writeObjsToFile(PATH.resolve(model.toString()) + ".dat", model);
        logger.log(Level.INFO, "Created object for " + model.toString());
    }

    /**
     * Deserialize an object using the {@code FileHandler} class then cast it to
     * {@code IModel} before returning it. The key is the name of the file
     * (without extension) to retrieve the object from.
     *
     * @param key the name of the file (without extension).
     * @return the {@code IModel} from the file.
     */
    public IModel read(String key) {
        return (IModel) FileHandler.readObjFromFile(PATH.resolve(key) + ".dat");
    }

    /**
     * Deserialize all object using the {@code FileHandler} from all the files in
     * the {@code PATH} directory, casts them to {@code IModel}s and returns them
     * as a {@code List}.
     *
     * @return the {@code List} of {@code IModel}s from all the files in the
     * {@code PATH} directory.
     */
    public List<IModel> readAll() {
        File dir = Paths.get(PATH.toString()).toFile();
        File[] files = dir.listFiles();

        if (files == null) {
            return null;
        }

        List<IModel> list = new ArrayList<>();

        for (File file : files) {
            list.add((IModel) FileHandler.readObjFromFile(file.getPath()));
        }

        return list;
    }

    /**
     * Updates a model by calling {@code delete()} with the old model, then
     * calling {@code create()} with the new model.
     *
     * @param oldModel the old version of the model to be updated.
     * @param newModel the new version of the model to replace the old one.
     */
    public void update(IModel oldModel, IModel newModel) {
        delete(oldModel);
        create(newModel);
    }

    /**
     * Search for an {@code IModel} in the {@code PATH} directory files and
     * delete the file if the model exists within it.
     *
     * @param model the model to find and delete.
     */
    public void delete(IModel model) {
        File[] files = PATH.toFile().listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            IModel modelFromFile = (IModel) FileHandler.readObjFromFile(
                    PATH.toString() + File.separator + file.getName()
            );

            if (modelFromFile == null) {
                continue;
            }

            if (file.getName().equals(model.toString() + ".dat")) {
                try {
                    Files.deleteIfExists(Paths.get(PATH.toString() + File.separator + file.getName()));
                    logger.log(Level.INFO, String.format("The file %s was deleted successfully.",
                            PATH.toString() + File.separator + file.getName()
                    ));
                } catch (IOException e) {
                    logger.log(Level.SEVERE, String.format(
                            "The file %s was not deleted for one reason or another. " +
                                    "This is a known issue on Microsoft Windows. %n%s",
                            PATH.toString() + File.separator + file.getName(),
                            Arrays.toString(e.getStackTrace())
                    ));
                }
            }
        }
    }
}
