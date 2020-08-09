package uk.me.jenewland.natpropsalessys.model;

import uk.me.jenewland.natpropsalessys.model.user.UserAdmin;

public class Session {
  private UserAdmin admin;
  private Branch branch;

  public Session(UserAdmin admin) {
    this.admin = admin;
  }

  public Session(Branch branch) {
    this.branch = branch;
  }

  public UserAdmin getUserAdmin() {
    return admin;
  }

  public Branch getBranch() {
    return branch;
  }

  public boolean isAdmin() {
    return branch == null;
  }
}
