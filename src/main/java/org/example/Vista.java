package org.example;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class Vista extends JFrame {
    private JTable tabla;
    private ModeloTabla modeloTabla;

    public volatile Casilla[] casillasMemoria;





    public Vista(String TituloVentana, ArrayList<Proceso> procesos){

        setTitle(TituloVentana);
        Border bordeCompuesto = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),  // Borde exterior de color rojo
                null  // Espacio interior alrededor del texto
        );


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(20, 20, 1800, 800);
        setLayout(new BorderLayout());
        modeloTabla = new ModeloTabla(procesos);
        tabla = new JTable(modeloTabla);
        setResizable(false);
        getContentPane().setLayout(new GridLayout(2,0));


        JPanel conMemoria= new JPanel();
        conMemoria.setLayout(new GridLayout(4, 8));

        casillasMemoria= new Casilla[32];

        for(Integer i=0;i<32;i++){
            JLabel casilla= new JLabel("Disponible "+(i));
            casilla.setName(i.toString());
            casilla.setBorder(BorderFactory.createLineBorder(Color.black));
            casilla.setOpaque(true);
            casilla.setBackground(Color.white);
            casilla.setBorder(bordeCompuesto);
            casillasMemoria[i] = new Casilla(i, false, casilla);
            conMemoria.add(casillasMemoria[i].getLabelCasilla());

        }

        getContentPane().add(conMemoria, BorderLayout.CENTER);



        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Obtener el ancho preferido para cada columna y establecerlo
        for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
            TableColumn column = tabla.getColumnModel().getColumn(i);
            int width = obtenerAnchoPreferido(tabla, i);
            column.setPreferredWidth(width);
            if(column.getIdentifier().equals("ID")) {
                column.setPreferredWidth(width+20);
            }
        }

        // Crear un renderizador centrado para las celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Aplicar el renderizador centrado a todas las columnas
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Agregar el JTable a un JScrollPane para permitir el desplazamiento
        JScrollPane jScrollPane = new JScrollPane(tabla);


        // Configurar barras de desplazamiento horizontal y vertical
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        getContentPane().add(jScrollPane, BorderLayout.CENTER);
        setVisible(true);

    }
public void actualizar(){
        this.revalidate();
        this.repaint();
    modeloTabla.updateTable();
}





    // Método para obtener el ancho preferido de una columna basado en el contenido de la cabecera
    private static int obtenerAnchoPreferido(JTable table, int columnIndex) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
        Component headerComp = headerRenderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, 0);
        int width = headerComp.getPreferredSize().width;

        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer cellRenderer = table.getCellRenderer(row, columnIndex);
            Component cellComp = cellRenderer.getTableCellRendererComponent(table, table.getValueAt(row, columnIndex), false, false, row, columnIndex);
            width = Math.max(width, cellComp.getPreferredSize().width);
        }

        return width + 5;  // Ajuste adicional
    }


}
