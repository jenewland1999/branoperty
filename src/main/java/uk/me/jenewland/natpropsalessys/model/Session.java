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

  public String getUsername() {
    if (branch != null) {
      return branch.getName();
    } else if (admin != null) {
      return admin.getUsername();
    } else {
      return "invalid user";
    }
  }

  public Branch getBranch() {
    return branch;
  }

  public boolean isAdmin() {
    return branch == null;
  }
}
