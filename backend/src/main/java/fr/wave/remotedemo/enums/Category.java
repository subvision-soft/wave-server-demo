package fr.wave.remotedemo.enums;

public enum Category {
    MINIME("Minime"),CADET("Cadet"),JUNIOR("Junior"),SENIOR("Sénior"),MASTER("Master");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
