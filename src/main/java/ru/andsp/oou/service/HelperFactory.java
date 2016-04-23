package ru.andsp.oou.service;


import ru.andsp.oou.contract.ObjectHelper;
import ru.andsp.oou.helper.*;
import ru.andsp.oou.type.TypeObject;


class HelperFactory {


    public static synchronized ObjectHelper getHelper(TypeObject type) {
        return createHelper(type);
    }

    private static ObjectHelper createHelper(TypeObject type) {
        switch (type) {
            case FUNCTION:
            case PACKAGE:
            case PROCEDURE:
            case TYPE:
            case TRIGGER:
                return new SourceHelper();
            case SEQUENCE:
                return new SequenceHelper();
            case TABLE:
                return new TableHelper();
            case VIEW:
                return new ViewHelper();
            case SYNONYM:
                return new SynonymHelper();
            default:
                return null;
        }
    }

}