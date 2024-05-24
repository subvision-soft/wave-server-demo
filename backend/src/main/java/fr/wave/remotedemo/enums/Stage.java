package fr.wave.remotedemo.enums;

public enum Stage {
    QUALIFICATION("Qualification"),
    EIGHTH_FINAL("Huitième de finale"),
    QUARTER_FINAL("Quart de finale"),
    SEMI_FINAL("Demie finale"),
    FINAL("Finale");

    private final String name;

    Stage(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Stage fromName(String name) {
        for (Stage stage : Stage.values()) {
            if (stage.name.equals(name)) {
                return stage;
            }
        }
        return null;
    }
}
