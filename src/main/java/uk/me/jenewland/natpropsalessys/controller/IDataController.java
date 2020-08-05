package uk.me.jenewland.natpropsalessys.controller;

import uk.me.jenewland.natpropsalessys.model.IModel;

import java.util.List;

public interface IDataController {
  void create(IModel model);
  IModel read(String key);
  List<IModel> readAll();
  void update(IModel model);
  void delete(IModel model);
}
