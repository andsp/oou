package ru.andsp.oou.type;

public class Synonym extends OracleObject {

    private String owner;

    private String nameObject;

    private boolean publicSynonym;

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setNameObject(String nameObject) {
        this.nameObject = nameObject;
    }

    public void setPublicSynonym(boolean publicSynonym) {
        this.publicSynonym = publicSynonym;
    }

    public Synonym(String name) {
        super(name);
        this.typeObject = TypeObject.SYNONYM;
    }

    @Override
    public String getSource() {
        String typeSynonym = " ";
        if (this.publicSynonym) {
            typeSynonym = "public";
        }
        return String.format("create or replace %s synonym %s  for %s.%s;", typeSynonym, this.name, this.owner, this.nameObject);
    }
}
