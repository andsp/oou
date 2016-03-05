package ru.andsp.oou.helper;


import ru.andsp.oou.type.TypeObject;

public class ExtensionHelper {

    public static synchronized String getExtension(TypeObject type) {
        switch (type) {
            case FUNCTION:
                return "fnc";
            case PACKAGE:
                return "pck";
            case PROCEDURE:
                return "prc";
            case TRIGGER:
                return "trg";
            case TYPE:
                return "typ";
            case SEQUENCE:
                return "seq";
            case SYNONYM:
                return "syn";
            case TABLE:
                return "tbl";
            case VIEW:
                return "vw";
            default:
                return "sql";
        }
    }
}
