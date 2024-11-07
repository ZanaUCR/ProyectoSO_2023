package org.example;

import javax.swing.*;
import java.awt.*;

public class Casilla {
    int id;
    boolean enUso;
    JLabel labelCasilla;

    public Casilla(int id, boolean enUso, JLabel labelCasilla) {
        this.id = id;
        this.enUso = enUso;
        this.labelCasilla = labelCasilla;
    }

    public void restablecer(){
        labelCasilla.setText("Disponible");
        labelCasilla.setBackground(Color.white);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnUso() {
        return enUso;
    }

    public void setEnUso(boolean enUso) {
        this.enUso = enUso;
    }

    public JLabel getLabelCasilla() {
        return labelCasilla;
    }

    public void setLabelCasilla(JLabel labelCasilla) {
        this.labelCasilla = labelCasilla;
    }
}
