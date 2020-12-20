package org.skeleton.ionchem.states;

public class ChemComponent {
    private String name;
    private double amount;

    public ChemComponent(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        else if (obj instanceof ChemComponent) {
            ChemComponent c = (ChemComponent) obj;
            return (c.name.equals(this.name) && (c.amount == this.amount));
        } else return false;
    }

    public int hashCode() {
        return (int) (this.name.hashCode() * amount);
    }
}
