package com.Rincon.Main;

import com.Rincon.DAO.*;
import com.Rincon.Entidades.invoiceModel;
import com.Rincon.Entidades.invoiceDetailModel;
import com.Rincon.Entidades.orderDetailModel;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.*;
import javax.print.*;

public class CreateTickectOrder {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public void imprimir(int id) {
        try {
            StringBuilder builder = new StringBuilder();
            byte[] cutP = new byte[]{0x1d, 'V', 1};

            clsOrder order = new clsOrder();
            ArrayList<orderDetailModel> newOrder = order.obtenerOrdenes(id);
            int orderId = 0;
            Time orderTime = null;
            Date orderDate = null;
            int orderType = 0;
            String address = null;
            String numberPhone = null;
            String customer = null;
            String userName = null;
            String otherDetail = null;
            int tableNumber = 0;
            double total = 0;

            for (orderDetailModel i : newOrder) {
                orderId = i.getOrderId();
                orderTime = i.getOrderTime();
                orderDate = i.getOrderDate();
                orderType = i.getOrderType();
                address = i.getAddress();
                numberPhone = i.getNumberPhone();
                customer = i.getCustomer();
                userName = i.getUser();
                tableNumber = i.getNumberTable();
                otherDetail = i.getOtherDetail();
                total += i.getPlatterPrice();
            }

            buildHeader(builder, orderDate, orderTime, orderId, orderType, tableNumber, userName, customer);
            buildBody(builder, newOrder);
            buildFooter(builder, total, orderType, numberPhone, address, otherDetail);

            String filePath = "C:\\ticket\\Order.txt";
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

    private void buildHeader(
            StringBuilder builder,
            Date orderDate, Time orderTime,
            int orderId, int orderType,
            int tableNumber,
            String userName,
            String customer) {
        builder.append("             El Rincocito Mexicano        \n");
        builder.append("                Orden numero: ").append(orderId).append("\n");
        builder.append("                Fecha: ").append(orderDate.toString()).append("\n");
        builder.append("                 Hora: ").append(orderTime.toLocalTime()).append("\n");
        builder.append("               Mesero: ").append(userName).append("\n");
        if (orderType == 0) {
        builder.append("             Servicio en mesa: ").append(tableNumber).append("\n");
            if (customer.length() > 0) {
                builder.append("              Cliente: ").append(customer).append("\n");
            }
        } else if (orderType == 1) {
            builder.append("              servicio para llevar       ").append("\n");
            if (customer.length() > 0) {
                builder.append("              Cliente: ").append(customer).append("\n");
            }
        } else {
            builder.append("             Servicio a domicilio        \n");
            if (customer.length() > 0) {
                builder.append("              Cliente: ").append(customer).append("\n");
            }

        }
        builder.append("-----------------------------------------------\n");
        builder.append("                                               \n");
        builder.append("DESCRIPCION                            PRECIO  \n");
    }

    private void buildBody(StringBuilder builder, ArrayList<orderDetailModel> detalle) {

        for (orderDetailModel i : detalle) {
            String producName = " " + i.getPlatterName().replace("ñ", "n");
            String unitPrice = " " + String.valueOf(df.format(i.getPlatterPrice()));

            if (producName.length() < 37) {
                while (producName.length() <= 37) {
                    producName = producName + " ";
                }
            } else {
                producName = i.getPlatterName().substring(0, 36) + " ";
            }
            if (unitPrice.length() < 6) {
                while (unitPrice.length() <= 6) {
                    unitPrice = " $" + unitPrice + "  ";
                }
            }

            builder.append(producName).append(unitPrice).append("\n");
        }
    }

    private void buildFooter(StringBuilder builder, double total, int orderType, String numberPhone, String address, String otherDetail) {
        builder.append("\n");

        builder.append("                                Total: $").append(String.valueOf(df.format(total))).append("\n");
        builder.append("\n");
        if (orderType == 2) {
            builder.append("\n");
            builder.append("Numero de telefono: ").append(numberPhone).append("\n");
           builder.append("\n");
            builder.append("                  Direccion             ").append("\n");
            if (address.length() > 46) {
                int numLineasDireccion = (int) Math.ceil(address.length() / 46.0);
                for (int i = 0; i < numLineasDireccion; i++) {
                    int endIndex = Math.min((i + 1) * 46, address.length());
                    String lineaDireccion = address.substring(i * 46, endIndex);
                    builder.append(lineaDireccion).append("\n");
                }
            }
             builder.append("\n");
            builder.append("                Otros Detalles          ").append("\n");
            if (otherDetail.length() > 46) {
                int numLineasDetalles = (int) Math.ceil(otherDetail.length() / 46.0);
                for (int i = 0; i < numLineasDetalles; i++) {
                    int startIndex = i * 46;
                    int endIndex = Math.min(startIndex + 46, otherDetail.length());
                    String lineaDetalles = otherDetail.substring(startIndex, endIndex);
                    builder.append(lineaDetalles).append("\n");
                }
            }

        } else {
            if (otherDetail.length() > 0) {
               builder.append("\n");
                builder.append("                Otros Detalles          ").append("\n");
                if (otherDetail.length() > 46) {
                    int numLineasDetalles = (int) Math.ceil(otherDetail.length() / 46.0);
                    for (int i = 0; i < numLineasDetalles; i++) {
                        int startIndex = i * 46;
                        int endIndex = Math.min(startIndex + 46, otherDetail.length());
                        String lineaDetalles = otherDetail.substring(startIndex, endIndex);
                        builder.append(lineaDetalles).append("\n");
                    }
                }
            }
        }
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
