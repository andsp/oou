package ru.andsp.oou.type;


public class Procedure extends SourceOracleObject{

    public Procedure(String name){
        super(name);
        this.typeObject = TypeObject.PROCEDURE;
    }
}
