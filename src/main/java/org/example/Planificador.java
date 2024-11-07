package org.example;

import java.awt.*;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.*;

public class Planificador {

    ArrayList<Proceso> procesosT = new ArrayList<>();
    LinkedList<Proceso> procesos = new LinkedList<>();
    LinkedList<Proceso> procesosTiempoReal = new LinkedList<Proceso>();
    LinkedList<Proceso> procesosUsuario = new LinkedList<Proceso>();
  //  ModeloTabla m = new ModeloTabla(procesosT);
    Queue<Proceso> prioridad1 = new LinkedList<>();
    Queue<Proceso> prioridad2 = new LinkedList<>();
    Queue<Proceso> prioridad3 = new LinkedList<>();
    Recurso impresora1;
    Vista v;
    Recurso impresora2;
    Recurso escaner;
    Recurso cd1;
    Recurso cd2;
    Recurso modem;

    int memoria = 960;
    ArrayList<Recurso> recursosSistema;
    public Planificador() throws InterruptedException {
        leerArchivo();
         v = new Vista("SO", procesosT);
         recursosSistema = new ArrayList<>();
        recursosSistema.add(new Recurso(1, "Impresora", false, 0));
        recursosSistema.add(new Recurso(2, "Impresora", false, 1));
        recursosSistema.add(new Recurso(1, "Escaner", false, 2));
        recursosSistema.add(new Recurso(1, "CD", false, 3));
        recursosSistema.add(new Recurso(2, "CD", false, 4));
        recursosSistema.add(new Recurso(1, "Modem", false, 5));
        while (!procesosUsuario.isEmpty() || !procesos.isEmpty() ||
                !prioridad1.isEmpty() || !prioridad2.isEmpty() || !prioridad3.isEmpty()) {
            if (!procesos.isEmpty()) {
                Proceso process = procesos.poll();
                Asignar(process);
                ejecutar();
            }
            for (int i = 1; i <= procesosUsuario.size() && !procesosUsuario.isEmpty(); i++) {
                Proceso proceso = procesosUsuario.poll();
                if (Asignar(proceso)) {
                    ejecutar();
                }
            }
            if ((!(prioridad1.isEmpty()) || !(prioridad2.isEmpty()) || !(prioridad3.isEmpty())) && procesos.isEmpty()) {
                ejecutar();
            }
        }
    }

    public boolean Asignar(Proceso proceso) {
        if (proceso.getPrioridadInicial() == 0) {
            proceso.setEstatus("Listo");
            proceso.setUbicacionMemoria("1 - 2");
            procesosTiempoReal.add(proceso);
            v.casillasMemoria[0].getLabelCasilla().setText(String.valueOf(proceso.getIdProceso()));
            v.casillasMemoria[0].getLabelCasilla().setBackground(Color.blue);
            v.casillasMemoria[0].setEnUso(true);
            v.casillasMemoria[1].getLabelCasilla().setText(String.valueOf(proceso.getIdProceso()));
            v.casillasMemoria[1].getLabelCasilla().setBackground(Color.blue);
            v.casillasMemoria[1].setEnUso(true);

        }
        if (proceso.getPrioridadInicial() == 1 || proceso.getPrioridadInicial() == 2 || proceso.getPrioridadInicial() == 3) {
            boolean bandera = true;
            boolean bloquesContinuos = false;
            int necesarios = 1;
            Color color = colorAleatorio();
            while (bandera) {
                if (proceso.getMemoriaRequerida() <= 32 * necesarios) {
                    if (memoria - 32 * necesarios >= 0 && verificarRecursos(proceso)) {
                        for (int i = 2; i < v.casillasMemoria.length; i++) {
                            if (!v.casillasMemoria[i].isEnUso()) {
                                bloquesContinuos = verificarContinuos(i, necesarios, proceso);
                                if (bloquesContinuos) {
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < proceso.ubicaciones.size(); i++) {
                            int indice = proceso.ubicaciones.get(i);
                            v.casillasMemoria[indice].getLabelCasilla().setBackground(color);
                            v.casillasMemoria[indice].getLabelCasilla().setText(String.valueOf(proceso.getIdProceso()));
                            v.casillasMemoria[indice].setEnUso(true);
                        }
                        if (!proceso.ubicaciones.isEmpty()) {
                            asignarRecursos(proceso);
                            memoria -= 32 * necesarios;
                            proceso.setEstatus("Listo");
                            if (proceso.getPrioridadInicial() == 1) {
                                prioridad1.offer(proceso);
                                // notificar();
                                return true;
                            }
                            if (proceso.getPrioridadInicial() == 2) {
                                prioridad2.offer(proceso);
                                // notificar();
                                return true;
                            }
                            if (proceso.getPrioridadInicial() == 3) {
                                prioridad3.offer(proceso);
                                // notificar();
                                return true;
                            }
                        } else {
                            System.out.println("FALTA MEMORIA");
                            proceso.setEstatus("Bloqueado");
                            procesosUsuario.offer(proceso);
                            return false;
                        }

                    } else {
                        if (!(memoria - 32 * necesarios >= 0) && !bloquesContinuos) {
                            System.out.println("FALTA MEMORIA");
                        } else {
                            System.out.println("FALTAN RECURSOS");
                        }
                        proceso.setEstatus("Bloqueado");
                        procesosUsuario.offer(proceso);
                        // notificar();
                        return false;
                    }
                    bandera = false;

                } else {
                    necesarios++;
                }
            }
        }
        return true;
    }

    public boolean verificarContinuos(int inicio, int cantidad, Proceso proceso) {
        ArrayList<Integer> ubicaciones = new ArrayList<>();
        for (int i = inicio; i < inicio + cantidad && i < 32; i++) {
            if (!v.casillasMemoria[i].isEnUso()) {
                ubicaciones.add(i);
            }
        }
        if (ubicaciones.size() == cantidad) {
            String ubis = " ";
            for (int ubicacion : ubicaciones) {
                ubis += ubicacion + " ";
                proceso.ubicaciones.add(ubicacion);
            }
            proceso.setUbicacionMemoria(ubis);
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarRecursos(Proceso actual) {
        int impresorasDisponibles = contarRecursosDisponibles("Impresora");
        int cdsDisponibles = contarRecursosDisponibles("CD");
        int modemsDisponibles = contarRecursosDisponibles("Modem");
        int escanersDisponibles = contarRecursosDisponibles("Escaner");

        return actual.getImpresorasSolicitadas() <= impresorasDisponibles &&
                actual.getCdsSolicitados() <= cdsDisponibles &&
                actual.getModemsSolicitados() <= modemsDisponibles &&
                actual.getEscaneresSolicitados() <= escanersDisponibles;
    }

    private int contarRecursosDisponibles(String tipo) {
        return (int) recursosSistema.stream()
                .filter(recurso -> recurso.getNombre().equals(tipo) && !recurso.isEnUso())
                .count();
    }

    public void asignarRecursos(Proceso proceso) {
        asignarRecursosTipo(proceso, "Impresora", proceso.getImpresorasSolicitadas(), proceso::setImpresorasAsignadas);
        asignarRecursosTipo(proceso, "Escaner", proceso.getEscaneresSolicitados(), proceso::setEscaneresAsignados);
        asignarRecursosTipo(proceso, "CD", proceso.getCdsSolicitados(), proceso::setCdsAsignados);
        asignarRecursosTipo(proceso, "Modem", proceso.getModemsSolicitados(), proceso::setModemsAsignados);
    }

    private void asignarRecursosTipo(Proceso proceso, String tipo, int cantidadSolicitada, Consumer<Integer> asignadosSetter) {
        int asignados = 0;

        while (asignados != cantidadSolicitada) {
            for (Recurso recurso : recursosSistema) {
                if (recurso.getNombre().equals(tipo) && !recurso.isEnUso()) {
                    asignados++;
                    proceso.recursosAsignados.add(recurso);
                    asignadosSetter.accept(asignados);
                    recurso.setEnUso(true);
                    break;
                }
            }
        }
    }



    public void leerArchivo() {
        String archivo = "procesos2.txt";


        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                if (valores.length == 8) {
                    int tiempoLlegada = Integer.parseInt(valores[0]);
                    int prioridad = Integer.parseInt(valores[1]);
                    int tiempoProcesador = Integer.parseInt(valores[2]);
                    int mbytes = Integer.parseInt(valores[3]);
                    int cantImpresora = Integer.parseInt(valores[4]);
                    int cantEscan = Integer.parseInt(valores[5]);
                    int cantModems = Integer.parseInt(valores[6]);
                    int cantCD = Integer.parseInt(valores[7]);
                    Proceso proceso = new Proceso(tiempoLlegada, "Creado", tiempoLlegada, prioridad, prioridad, tiempoProcesador, tiempoProcesador, mbytes, "Sin asignar", cantImpresora, 0, cantEscan, 0, cantModems, 0, cantCD, 0);
                    procesos.add(proceso);
                    procesosT.add(proceso);


                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Color colorAleatorio() {
        Random random = new Random();
        int maxComponent = 200;  // Ajusta este valor según lo claro que desees que sea el color
        return new Color(
                maxComponent - random.nextInt(56),  // Componente rojo más alto
                maxComponent - random.nextInt(24),  // Componente verde más alto
                maxComponent - random.nextInt(78)   // Componente azul más alto
        );
    }

    public void liberarRecursos(Proceso proceso) {
        if (!proceso.recursosAsignados.isEmpty()) {
            for (Recurso recurso : proceso.recursosAsignados) {
                recurso.setEnUso(false);
            }
            proceso.recursosAsignados.clear();

        }
        if (proceso.getPrioridadActual() == 0) {
            v.casillasMemoria[0].restablecer();
            v.casillasMemoria[1].restablecer();
            v.casillasMemoria[0].setEnUso(false);
            v.casillasMemoria[1].setEnUso(false);
        }
        if (proceso.getPrioridadActual() == 1 || proceso.getPrioridadActual() == 2 || proceso.getPrioridadActual() == 3) {
            ArrayList<Integer> sectores = new ArrayList<>(proceso.ubicaciones);
            for (int sector : sectores) {

                v.casillasMemoria[sector].restablecer();
                v.casillasMemoria[sector].setEnUso(false);

            }
            memoria += 32 * proceso.ubicaciones.size();
        }

    }

    public void ejecutar() {
        if (!procesosTiempoReal.isEmpty()) {
            for (Proceso p : procesosTiempoReal) {
                p.setEstatus("Ejecucion");
                while (p.getTiempoProcesadorRestante() != 0) {

                    System.out.println("\nProceso Tiempo real: " + p.getIdProceso() + "\nSegundos restantes: " + p.getTiempoProcesadorRestante());

                    p.setTiempoProcesadorRestante((p.getTiempoProcesadorRestante() - 1));
                    //notificar();
                    try {
                       v.actualizar();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            Proceso proceso = procesosTiempoReal.poll();
            proceso.setEstatus("Finalizado");
            liberarRecursos(proceso);

            return;
        }
        if (!prioridad1.isEmpty()) {
            Proceso proceso = prioridad1.peek();
            proceso.setEstatus("Ejecucion");

            try {
                v.actualizar();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            proceso.setTiempoProcesadorRestante((proceso.getTiempoProcesadorRestante() - 1));
            if (!(proceso.getTiempoProcesadorRestante() == 0)) {
                proceso.setEstatus("Listo");
                proceso.setPrioridadActual(2);
                prioridad2.offer(proceso);
                prioridad1.remove(proceso);
                return;
            } else {
                proceso.setEstatus("Finalizado");
                liberarRecursos(proceso);
                prioridad1.remove(proceso);

                // notificar();
            }

        }
        if (!prioridad2.isEmpty()) {
            Proceso proceso = prioridad2.peek();
            proceso.setEstatus("Ejecucion");

            try {
                v.actualizar();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            proceso.setTiempoProcesadorRestante((proceso.getTiempoProcesadorRestante() - 1));
            if (!(proceso.getTiempoProcesadorRestante() == 0)) {
                proceso.setEstatus("Listo");
                proceso.setPrioridadActual(3);
                prioridad3.offer(proceso);
                prioridad2.remove(proceso);
                return;
            } else {
                proceso.setEstatus("Finalizado");
                liberarRecursos(proceso);
                prioridad2.remove(proceso);

                // notificar();
            }
        }
        if (!prioridad3.isEmpty()) {
            Proceso proceso = prioridad3.poll();
            proceso.setEstatus("Ejecucion");

            try {
                v.actualizar();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            proceso.setTiempoProcesadorRestante((proceso.getTiempoProcesadorRestante() - 1));

            if (!(proceso.getTiempoProcesadorRestante() == 0)) {
                proceso.setEstatus("Listo");
                prioridad3.offer(proceso);
            } else {
                proceso.setEstatus("Finalizado");
                liberarRecursos(proceso);
                prioridad3.remove(proceso);
            }
        }
    }
}

