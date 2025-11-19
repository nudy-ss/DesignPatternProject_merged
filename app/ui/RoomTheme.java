package ui;

public enum RoomTheme {
  PROJECTOR("프로젝터"),
  AUTO_RECORDING("녹화강의실");

  private final String displayName;

  RoomTheme(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
