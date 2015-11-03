package ru.andsp.oou.type;

public class TableColumn extends OracleObject {

    private DataTypes types;

    /**
     * Для строк длинна строки
     * Для чисел общая длинна числа
     */
    private int length;

    /**
     * Используется только для чисел
     * Кол-во цифр после запятой
     */
    private int decimal;


    private int numberLength;


    private boolean nullable = true;

    private String defValue;

    public void setNumberLength(Integer numberLength) {
        this.numberLength = numberLength;
    }

    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }

    public void setTypes(DataTypes types) {
        this.types = types;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public TableColumn(String name) {
        super(name);
    }


    private String getLengthSource() {
        switch (types) {
            case NVARCHAR2:
            case CHAR:
            case RAW:
            case VARCHAR2:
                return String.format("(%s)", this.length);
            case NUMBER:
                return numberLength > 0 && decimal > 0 ? String.format("(%d,%d)", numberLength, decimal) :
                        numberLength > 0 ? String.format("(%d)", this.numberLength) : null;
            default:
                return null;
        }
    }

    @Override
    public String getSource() {
        StringBuilder sb = new StringBuilder(String.format("%s ", this.name));
        sb.append(this.types.toString().toLowerCase());
        if (getLengthSource() != null)
            sb.append(this.getLengthSource());
        if (defValue != null) {
            sb.append(String.format(" default %s", this.defValue));
        }
        if (!this.nullable) {
            sb.append(" not null");
        }
        return sb.toString();
    }
}
