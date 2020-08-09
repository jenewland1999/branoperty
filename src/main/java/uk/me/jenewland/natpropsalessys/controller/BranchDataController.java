package uk.me.jenewland.natpropsalessys.controller;

import uk.me.jenewland.natpropsalessys.model.Branch;
import uk.me.jenewland.natpropsalessys.model.IModel;
import uk.me.jenewland.natpropsalessys.utils.FileHandler;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import static uk.me.jenewland.natpropsalessys.NatPropSalesSys.LOGGER;

public class BranchDataController implements IDataController {
  private final Path PATH;

  public BranchDataController(Path path) {
    this.PATH = path;
  }

  @Override
  public void create(IModel model) {
    if (!(model instanceof Branch)) {
      return;
    }

    Branch branch = (Branch) model;

    FileHandler.writeObjsToFile(String.valueOf(PATH.resolve(branch.getName())), model);
  }

  @Override
  public IModel read(String key) {
    return (IModel) FileHandler.readObjFromFile(String.valueOf(PATH.resolve(key)));
  }

  @Override
  public Collection<IModel> readAll() {
    File dir = Paths.get(PATH.toString()).toFile();
    File[] files = dir.listFiles();

    if (files == null) {
      return null;
    }

    Collection<IModel> list = new ArrayList<>();

    for (File file : files) {
      list.add((IModel) FileHandler.readObjFromFile(file.getName()));
    }

    return list;
  }

  @Override
  public void update(IModel oldModel, IModel newModel) {
    delete(oldModel);
    create(newModel);
  }

  @Override
  public void delete(IModel model) {
    File dir = Paths.get(PATH.toString()).toFile();
    File[] files = dir.listFiles();

    if (files == null) {
      return;
    }

    for (File file : files) {
      IModel modelFromFile = (IModel) FileHandler.readObjFromFile(PATH.toString() + File.separator + file.getName());

      if (modelFromFile == null) {
        return;
      }

      if (file.getName().equals(model.toString())) {
        if (file.delete()) {
          System.out.println("Deleted");
        } else {
          System.out.println("Not deleted.");
        }
      }
    }
  }
}
