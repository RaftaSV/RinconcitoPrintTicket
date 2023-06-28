package com.Rincon.Main;

import com.Rincon.DAO.*;
import com.Rincon.Entidades.orderDetailModel;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.*;
import javax.print.*;

public class CreateTicketOrderAddMoreProducts {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    ArrayList<orderDetailModel> newOrder = null;

    public void imprimir(int id) {
        try {
            StringBuilder builder = new StringBuilder();

            clsOrder order = new clsOrder();
            newOrder = order.obtenerOrdenesMasProductos(id);
            int orderId = 0;
            double total = 0;
            for (orderDetailModel i : newOrder) {
                orderId = i.getOrderId();
             
                total += i.getPlatterPrice();
            }

            buildHeader(builder, orderId);
            buildBody(builder, newOrder);
            buildFooter(builder, total);

            String filePath = "C:\\ticket\\Order.txt";
            try {
                Files.write(Paths.get(filePath), builder.toString().getBytes());
                System.out.println("Archivo creado con exito.");
            } catch (IOException e) {
                System.out.println("Error al crear el archivo: " + e.getMessage());
            }

            imprimirArchivo(filePath, id);

        } catch (Exception e) {
            System.out.println("Error al imprimir: " + e.getMessage());
        }
    }

    private void buildHeader(
            StringBuilder builder,
            int orderId
            ) {
        builder.append("            Productos agregados a la     \n");
        builder.append("               Orden numero: ").append(orderId).append("\n");
        builder.append("------------------------------------------\n");
        builder.append("                                 \n");
        builder.append("DESCRIPCION                        PRECIO  \n");
    }

    private void buildBody(StringBuilder builder, ArrayList<orderDetailModel> detalle) {

        for (orderDetailModel i : detalle) {
            String producName = " " + i.getPlatterName().replace("ñ", "n");
            String unitPrice = " " + String.valueOf(df.format(i.getPlatterPrice()));

            if (producName.length() < 32) {
                while (producName.length() <= 32) {
                    producName = producName + " ";
                }
            } else {
                producName = i.getPlatterName().substring(0, 31) + " ";
            }
            if (unitPrice.length() < 6) {
                while (unitPrice.length() <= 6) {
                    unitPrice = " $" + unitPrice + "  ";
                }
            }

            builder.append(producName).append(unitPrice).append("\n");
        }
    }

    private void buildFooter(
            StringBuilder builder,
            double total) {
        builder.append("\n");
        builder.append("Total en platos agregados: $").append(String.valueOf(df.format(total))).append("\n");
        builder.append("\n");
        builder.append("\n");
        builder.append("\n");
        builder.append("\n");
        builder.append("\n");
        builder.append("\n");
    }

    private void imprimirArchivo(String filePath, int id) {
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException ex) {
            System.out.println("Error al guardar");
        }

        if (inputStream == null) {
            return;
        }

        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc document = new SimpleDoc(inputStream, docFormat, null);
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            try {
                printJob.print(document, null);
                byte[] cutP = new byte[]{0x1d, 'V', 1};
                cutPaper(cutP);
                clsOrder order = new clsOrder();

                try {
                    order.updateOrderDetails(newOrder);
                } catch (SQLException e) {
                    System.out.println("Error al actualizar los detalles de la orden: " + e.getMessage());
                }

            } catch (PrintException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("No existen impresoras instaladas");
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cutPaper(byte[] cutP) {
        printClass printer = new printClass();
        printer.printBytes(PrintServiceLookup.lookupDefaultPrintService().getName(), cutP);
    }
}
