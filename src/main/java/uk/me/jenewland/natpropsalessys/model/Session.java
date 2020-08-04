package uk.me.jenewland.natpropsalessys.model;

import uk.me.jenewland.natpropsalessys.model.user.UserAdmin;

public class Session {
  private UserAdmin userAdmin;
  private Branch branch;

  public UserAdmin getUserAdmin() {
    return userAdmin;
  }

  public void setUserAdmin(UserAdmin userAdmin) {
    this.userAdmin = userAdmin;
  }

  public Branch getBranch() {
    return branch;
  }

  public void setBranch(Branch branch) {
    this.branch = branch;
  }

  public boolean isAdmin() {
    return branch != null;
  }
}
