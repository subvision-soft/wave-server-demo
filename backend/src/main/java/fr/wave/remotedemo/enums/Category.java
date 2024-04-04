package fr.wave.remotedemo.enums;

public enum Category {
    MINIME(0),CADET(1),JUNIOR(2),SENIOR(3),MASTER(4);

    private final int id;

    Category(int i) {
        this.id = i;
    }

    public int getId() {
        return id;
    }

    public static Category fromId(int id){
        for(Category c : Category.values()){
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }
}
