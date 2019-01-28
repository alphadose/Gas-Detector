package com.example.alphadose.gasdetector;

public class Gas {
    private String formula, name, concentration;

    public Gas() {
    }

    public Gas(String formula, String name, String concentration) {
        this.formula = formula;
        this.name = name;
        this.concentration = concentration;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }
}