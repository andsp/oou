package ru.andsp.oou.type;

public class View extends OracleObject{


    private String text;


    private String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public View(String name){
        super(name);
        this.typeObject = TypeObject.VIEW;
    }

    @Override
    public String getSource() {
        return String.format("CREATE OR REPLACE FORCE VIEW %s as\n %s;",this.getName(),getText());
    }
}
