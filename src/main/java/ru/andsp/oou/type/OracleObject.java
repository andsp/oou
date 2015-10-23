package ru.andsp.oou.type;


import ru.andsp.oou.contract.OracleObjectContract;

public abstract class OracleObject implements OracleObjectContract{

    final String name;

    TypeObject typeObject;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public abstract String getSource();

    public TypeObject getTypeObject(){
        return this.typeObject;
    }


    OracleObject(String name){
        this.name = name;
    }
}