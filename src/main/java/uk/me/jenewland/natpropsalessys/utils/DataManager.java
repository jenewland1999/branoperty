package uk.me.jenewland.natpropsalessys.utils;

import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.IModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;

import static uk.me.jenewland.natpropsalessys.NatPropSalesSys.LOGGER;

public class DataManager {
  private final Path PATH;

  public DataManager(String path) {
    PATH = Paths.get(path);
  }

  public void create(IModel model) {
    FileHandler.writeObjsToFile(PATH.resolve(model.toString()) + ".dat", model);
    LOGGER.log(Level.INFO, "Created object for " + model.toString());
  }

  public IModel read(String key) {
    return (IModel) FileHandler.readObjFromFile(PATH.resolve(key) + ".dat");
  }

  public Set<IModel> readAll() {
    File dir = Paths.get(PATH.toString()).toFile();
    File[] files = dir.listFiles();

    if (files == null) {
      return null;
    }

    Set<IModel> list = new HashSet<>();

    for (File file : files) {
      list.add((IModel) FileHandler.readObjFromFile(file.getPath()));
    }

    return list;
  }

  public void update(IModel oldModel, IModel newModel) {
    delete(oldModel);
    create(newModel);
  }

  public void delete(IModel model) {
    File dir = Paths.get(PATH.toString()).toFile();
    File[] files = dir.listFiles();

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
          LOGGER.log(Level.INFO, String.format("The file %s was deleted successfully.",
                  PATH.toString() + File.separator + file.getName()
          ));
        } catch (IOException e) {
          LOGGER.log(Level.SEVERE, String.format(
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
