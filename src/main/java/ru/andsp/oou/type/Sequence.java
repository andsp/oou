package ru.andsp.oou.type;


import java.math.BigInteger;

public class Sequence extends OracleObject {

    private BigInteger max;

    private BigInteger min;

    private boolean cycle;

    private boolean order;

    private BigInteger cache;

    private BigInteger increment;

    private BigInteger start;


    public BigInteger getMax() {
        return max;
    }

    public void setMax(BigInteger max) {
        this.max = max;
    }

    public BigInteger getMin() {
        return min;
    }

    public void setMin(BigInteger min) {
        this.min = min;
    }

    public boolean isCycle() {
        return cycle;
    }

    public void setCycle(boolean cycle) {
        this.cycle = cycle;
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public BigInteger getCache() {
        return cache;
    }

    public void setCache(BigInteger cache) {
        this.cache = cache;
    }

    public BigInteger getIncrement() {
        return increment;
    }

    public void setIncrement(BigInteger increment) {
        this.increment = increment;
    }

    public BigInteger getStart() {
        return start;
    }

    public void setStart(BigInteger start) {
        this.start = start;
    }

    public Sequence(String name) {
        super(name);
        this.typeObject = TypeObject.SEQUENCE;
        this.cycle = false;
        this.order = false;
    }

    @Override
    public String getSource() {
        StringBuilder sb = new StringBuilder(String.format("CREATE SEQUENCE %s\n", this.name));
        if (this.min != null) {
            sb.append(String.format("minvalue %d\n", this.min));
        }
        if (this.max != null) {
            sb.append(String.format("maxvalue %d\n", this.max));
        }
        if(this.start!=null){
            sb.append(String.format("start with %d\n",this.start));
        }
        if(this.increment!=null){
            sb.append(String.format("increment by %d\n",this.increment));
        }
        if(this.cache!=null){
            sb.append(String.format("cache %d\n",this.cache));
        }
        if(this.cycle){
            sb.append("cycle\n");
        }
        if(this.order){
            sb.append("order\n");
        }
        sb.append(";");
        return sb.toString();
    }
}
