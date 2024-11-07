package org.example;


import java.util.ArrayList;

public class Proceso {

    int idProceso;
    String estatus;
    int tiempoLlegada;
    int prioridadInicial;
    int prioridadActual;
    int tiempoProcesadorRequerido;
    int tiempoProcesadorRestante;
    int memoriaRequerida;
    String ubicacionMemoria;
    int impresorasSolicitadas;
    int impresorasAsignadas;
    int escaneresSolicitados;
    int escaneresAsignados;
    int modemsSolicitados;
    int modemsAsignados;
    int cdsSolicitados;
    int cdsAsignados;

    public ArrayList<Integer> ubicaciones;
    public ArrayList<Recurso> recursosAsignados;


    public Proceso(int idProceso, String estatus, int tiempoLlegada, int prioridadInicial, int prioridadActual,
                   int tiempoProcesadorRequerido, int tiempoProcesadorRestante, int memoriaRequerida, String ubicacionMemoria,
                   int impresorasSolicitadas, int impresorasAsignadas, int escaneresSolicitados, int escaneresAsignados,
                   int modemsSolicitados, int modemsAsignados, int cdsSolicitados, int cdsAsignados) {
        super();
        this.idProceso = idProceso;
        this.estatus = estatus;
        this.tiempoLlegada = tiempoLlegada;
        this.prioridadInicial = prioridadInicial;
        this.prioridadActual = prioridadActual;
        this.tiempoProcesadorRequerido = tiempoProcesadorRequerido;
        this.tiempoProcesadorRestante = tiempoProcesadorRestante;
        this.memoriaRequerida = memoriaRequerida;
        this.ubicacionMemoria = ubicacionMemoria;
        this.impresorasSolicitadas = impresorasSolicitadas;
        this.impresorasAsignadas = impresorasAsignadas;
        this.escaneresSolicitados = escaneresSolicitados;
        this.escaneresAsignados = escaneresAsignados;
        this.modemsSolicitados = modemsSolicitados;
        this.modemsAsignados = modemsAsignados;
        this.cdsSolicitados = cdsSolicitados;
        this.cdsAsignados = cdsAsignados;
        ubicaciones = new ArrayList<>();
        recursosAsignados = new ArrayList<>();
    }



    public int getIdProceso() {
        return idProceso;
    }



    public void setIdProceso(int idProceso) {
        this.idProceso = idProceso;
    }



    public int getModemsSolicitados() {
        return modemsSolicitados;
    }



    public void setModemsSolicitados(int modemsSolicitados) {
        this.modemsSolicitados = modemsSolicitados;
    }



    public int getModemsAsignados() {
        return modemsAsignados;
    }



    public void setModemsAsignados(int modemsAsignados) {
        this.modemsAsignados = modemsAsignados;
    }



    public String getEstatus() {
        return estatus;
    }



    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }



    public int getTiempoLlegada() {
        return tiempoLlegada;
    }



    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }



    public int getPrioridadInicial() {
        return prioridadInicial;
    }



    public void setPrioridadInicial(int prioridadInicial) {
        this.prioridadInicial = prioridadInicial;
    }



    public int getPrioridadActual() {
        return prioridadActual;
    }



    public void setPrioridadActual(int prioridadActual) {
        this.prioridadActual = prioridadActual;
    }



    public int getTiempoProcesadorRequerido() {
        return tiempoProcesadorRequerido;
    }



    public void setTiempoProcesadorRequerido(int tiempoProcesadorRequerido) {
        this.tiempoProcesadorRequerido = tiempoProcesadorRequerido;
    }



    public int getTiempoProcesadorRestante() {
        return tiempoProcesadorRestante;
    }



    public void setTiempoProcesadorRestante(int tiempoProcesadorRestante) {
        this.tiempoProcesadorRestante = tiempoProcesadorRestante;
    }



    public int getMemoriaRequerida() {
        return memoriaRequerida;
    }



    public void setMemoriaRequerida(int memoriaRequerida) {
        this.memoriaRequerida = memoriaRequerida;
    }



    public String getUbicacionMemoria() {
        return ubicacionMemoria;
    }



    public void setUbicacionMemoria(String ubicacionMemoria) {
        this.ubicacionMemoria = ubicacionMemoria;
    }



    public int getImpresorasSolicitadas() {
        return impresorasSolicitadas;
    }



    public void setImpresorasSolicitadas(int impresorasSolicitadas) {
        this.impresorasSolicitadas = impresorasSolicitadas;
    }



    public int getImpresorasAsignadas() {
        return impresorasAsignadas;
    }



    public void setImpresorasAsignadas(int impresorasAsignadas) {
        this.impresorasAsignadas = impresorasAsignadas;
    }



    public int getEscaneresSolicitados() {
        return escaneresSolicitados;
    }



    public void setEscaneresSolicitados(int escaneresSolicitados) {
        this.escaneresSolicitados = escaneresSolicitados;
    }



    public int getEscaneresAsignados() {
        return escaneresAsignados;
    }



    public void setEscaneresAsignados(int escaneresAsignados) {
        this.escaneresAsignados = escaneresAsignados;
    }



    public int getCdsSolicitados() {
        return cdsSolicitados;
    }



    public void setCdsSolicitados(int cdsSolicitados) {
        this.cdsSolicitados = cdsSolicitados;
    }



    public int getCdsAsignados() {
        return cdsAsignados;
    }



    public void setCdsAsignados(int cdsAsignados) {
        this.cdsAsignados = cdsAsignados;
    }



}
