package ru.andsp.oou.type;

public class TableColumn extends OracleObject {

    private DataTypes types;

    /**
     * Для строк длинна строки
     * Для чисел общая длинна числа
     */
    private Integer length;

    /**
     * Используется только для чисел
     * Кол-во цифр после запятой
     */
    private Integer decimal;


    private boolean nullable = true;

    private String defValue;

    public String getDefValue() {
        return defValue;
    }

    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }

    public DataTypes getTypes() {
        return types;
    }

    public void setTypes(DataTypes types) {
        this.types = types;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDecimal() {
        return decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public TableColumn(String name) {
        super(name);
    }

    private String getLengthSource(){
        if(this.length != null && this.decimal != null){
            return String.format("(%d,%d)",this.length,this.decimal);
        }else if(this.length != null){
            return String.format("(%d)",this.length);
        }else{
            return "";
        }
    }

    @Override
    public String getSource() {
        StringBuilder sb = new StringBuilder(String.format("%s ",this.name));
        sb.append(this.types.toString().toLowerCase());
        if(types.isLength()){
            sb.append(this.getLengthSource());
        }
        if(defValue != null){
            sb.append(String.format("default %s", this.defValue));
        }
        if(!this.nullable){
            sb.append(" not null");
        }
        return sb.toString();
    }
}
