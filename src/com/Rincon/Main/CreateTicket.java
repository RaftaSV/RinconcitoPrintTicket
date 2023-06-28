package com.Rincon.Main;

import com.Rincon.DAO.*;
import com.Rincon.Entidades.invoiceModel;
import com.Rincon.Entidades.invoiceDetailModel;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.*;
import javax.print.*;

public class CreateTicket {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public void imprimir(int id) {
        try {
            StringBuilder builder = new StringBuilder();
            byte[] cutP = new byte[]{0x1d, 'V', 1};

            ClsInvoice f = new ClsInvoice();
            ArrayList<invoiceModel> factura = f.Factura(id);

            int idFactura = 0;
            Date fecha = null;
            Time hora = null;
            String cajero = "";
            double total = 0;
            float efectivo = 0;
            float cambio = 0;

            for (invoiceModel i : factura) {
                idFactura = i.getInvoiceId();
                fecha = i.getInvoiceDate();
                hora = i.getInvoiceTime();
                cajero = i.getUser();
                total = i.getInvoiceTotal();
                efectivo = i.getCash();
                cambio = i.getInvoiceChange();
            }

            buildHeader(builder, fecha, hora, idFactura, cajero);
            buildBody(builder, id);
            buildFooter(builder, total, efectivo, cambio);

            String filePath = "C:\\ticket\\impresion.txt";
            try {
                Files.write(Paths.get(filePath), builder.toString().getBytes());
                System.out.println("Archivo creado con exito.");
            } catch (IOException e) {
                System.out.println("Error al crear el archivo: " + e.getMessage());
            }

            imprimirArchivo(filePath);
            cutPaper(cutP);
        } catch (Exception e) {
            System.out.println("Error al imprimir: " + e.getMessage());
        }
    }

    private void buildHeader(StringBuilder builder, Date fecha, Time hora, int idFactura, String cajero) {
        builder.append("            El Rinconcito Mexicano     \n");
        builder.append("              Barrio el Centro,  \n");
        builder.append("        Nueva Concepcion, Chalatenango\n");
        builder.append("               Fecha: ").append(fecha.toString()).append("\n");
        builder.append("                Hora: ").append(hora.toLocalTime()).append("\n");
        builder.append("             Factura: ").append(idFactura).append("\n");
        builder.append("              Cajero: ").append(cajero).append("\n");
        builder.append("            WhatsApp: 7595-3055\n");
        builder.append("            Telefono: 2306-4203\n");
        builder.append("------------------------------------------\n");
        builder.append("                                          \n");
         builder.append("DESCRIPCION                      PRECIO  \n");
    }

    private void buildBody(StringBuilder builder, int id) {
        ClsInvoiceDetails det = new ClsInvoiceDetails();
        ArrayList<invoiceDetailModel> detalle = det.listaDetallesFacturas(id);

        for (invoiceDetailModel i : detalle) {
            String nombreProducto = " " + i.getPlatterName().replace("ñ", "n");
            String precioUnidad = " " + String.valueOf(df.format(i.getUnitPrice()));

            if (nombreProducto.length() < 32) {
                while (nombreProducto.length() <= 32) {
                    nombreProducto = nombreProducto + " ";
                }
            } else {
                nombreProducto = i.getPlatterName().substring(0, 31) + " ";
            }
            if (precioUnidad.length() < 6) {
                while (precioUnidad.length() <= 6) {
                    precioUnidad = "$" + precioUnidad + "  ";
                }
            }

            builder.append(nombreProducto).append(precioUnidad).append("\n");
        }
    }

    private void buildFooter(StringBuilder builder, double total, float efectivo, float cambio) {
        builder.append("\n");
        builder.append("                         Total:  $").append(String.valueOf(df.format(total))).append("\n");
        builder.append("                      Efectivo:  $").append(String.valueOf(df.format(efectivo))).append("\n");
        builder.append("                        Cambio:  $").append(String.valueOf(df.format(cambio)).replace("-", "")).append("\n");
          builder.append("------------------------------------------\n");
        builder.append("                               \n");
        builder.append("           GRACIAS POR PREFERIRNOS     \n");
        builder.append("          ESPERAMOS TU PRONTA VISITA   \n\n");
        builder.append("                                \n");
        builder.append("                                \n");
        builder.append("                                .\n");
    }

    private void imprimirArchivo(String filePath) {
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
