package ru.andsp.oou.type;

public class Trigger extends SourceOracleObject {


    public Trigger(String name) {
        super(name);
        this.typeObject = TypeObject.TRIGGER;
    }

}
