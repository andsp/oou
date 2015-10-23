package ru.andsp.oou.type;

import ru.andsp.oou.contract.SourceObjectContract;

public class SourceOracleObject extends OracleObject implements SourceObjectContract {

    protected String source;

    public SourceOracleObject(String name) {
        super(name);
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public void setSource(String source) {
        this.source = source;
    }
}

