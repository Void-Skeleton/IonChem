package org.skeleton.ionchem.states;

import java.util.ArrayList;
import java.util.List;

public class ChemContainer {
    private List<ChemComponent> components;

    public ChemContainer() {
        components = new ArrayList<>();
    }

    public List<ChemComponent> getComponents() {
        return components;
    }

    public ChemComponent getComponent(String name) {
        for (ChemComponent c : this.components) {
            if (c.getName().equals(name)) return c;
        }
        return null;
    }

    public double getAmount(String name) {
        ChemComponent c = this.getComponent(name);
        if (c == null) return 0;
        else return c.getAmount();
    }

    public boolean containsComponent(String name) {
        return (this.getComponent(name)) == null;
    }

    /**
     * @param name trivial
     * @param byAmount trivial
     * @return how much modification is performed
     */
    public double modifyComponent(String name, double byAmount) {
        ChemComponent modifiedComponent = this.getComponent(name);
        if (modifiedComponent == null) {
            modifiedComponent = new ChemComponent(name, 0);
            this.components.add(modifiedComponent);
        }
        double amount = modifiedComponent.getAmount();
        if (amount + byAmount >= 0) {
            modifiedComponent.setAmount(amount + byAmount);
            return byAmount;
        }
        else {
            modifiedComponent.setAmount(0);
            return -amount;
        }
    }

    public void clean() {
        components.removeIf(component -> Math.abs(component.getAmount()) < 0.001);
    }
}
