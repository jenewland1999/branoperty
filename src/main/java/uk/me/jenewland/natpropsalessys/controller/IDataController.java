package uk.me.jenewland.natpropsalessys.controller;

import uk.me.jenewland.natpropsalessys.model.IModel;

import java.util.Collection;

public interface IDataController {
  void create(IModel model);
  IModel read(String key);
  Collection<IModel> readAll();
  void update(IModel oldModel, IModel newModel);
  void delete(IModel model);
}
