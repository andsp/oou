package ru.andsp.oou.service;


import ru.andsp.oou.type.*;
import ru.andsp.oou.type.Package;

public class OracleObjectFactory {

    public static OracleObject createObject(String name, TypeObject type) {
        switch (type) {
            case FUNCTION:
                return new Function(name);
            case PACKAGE:
                return new Package(name);
            case PROCEDURE:
                return new Procedure(name);
            case SEQUENCE:
                return new Sequence(name);
            case SYNONYM:
                return new Synonym(name);
            case TABLE:
                return new Table(name);
            case TRIGGER:
                return new Trigger(name);
            case TYPE:
                return new Type(name);
            case VIEW:
                return new View(name);
            default:
                return null;
        }
    }
}
