/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.Rincon.Main;
import com.Rincon.Conexion.Conexion;
import com.Rincon.DAO.clsOrder;
import com.Rincon.Entidades.orderModel;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

public class MainTicket {
    public static void main(String[] args) {
        Conexion con = new Conexion();
        Connection conectar = con.retornarConexion();
        CreateTicket create = new CreateTicket();
        CreateTickectOrder createTicketOrder = new CreateTickectOrder();
        String idTicket = null;
        int idOrder = 0;
        clsOrder order = new clsOrder();

        try {
             // Iniciar aplicación React.js con yarn start
            iniciarAplicacionReactJS();
            // Iniciar servidor Node.js con yarn dev
            iniciarServidorNodeJS();

         
            // Resto del código...
            try (CallableStatement call = conectar.prepareCall("call SP_S_NUMTICKET()")) {
                while (true) {
                    Thread.sleep(500);
                    try {
                        ResultSet resultado = call.executeQuery();
                        if (resultado.next()) {
                            idTicket = resultado.getString("invoiceId");
                            try (CallableStatement calla = conectar.prepareCall("call SP_U_NUMTICKET(?)")) {
                                calla.setInt("pId", Integer.valueOf(idTicket));
                                calla.executeQuery();
                            }
                        }
                        if (idTicket != null) {
                            create.imprimir(Integer.valueOf(idTicket));
                            idTicket = null;
                        }
                        resultado.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    try {
                        ArrayList<orderModel> newOrder = order.obtenerOrdenes();

                        for (orderModel orderid : newOrder) {
                            idOrder = orderid.getOrderId();
                        }

                        if (idOrder != 0) {
                            createTicketOrder.imprimir(idOrder);
                            order.updateOrder(idOrder);
                            idOrder = 0;
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            con.cerrarConexion();
        }
    }

    private static void iniciarServidorNodeJS() throws IOException {
    String directorioServidorNodeJS = "D:\\Descargas\\PruebaDev\\NodeYRender";
    String comandoServidorNodeJS = "yarn dev";

    // Navegar al directorio del servidor Node.js
    File dirServidorNodeJS = new File(directorioServidorNodeJS);
    CommandLine cambioDirectorio = new CommandLine("cmd");
    cambioDirectorio.addArgument("/c");
    cambioDirectorio.addArgument("cd");
    cambioDirectorio.addArgument(dirServidorNodeJS.getAbsolutePath());

    // Ejecutar el comando yarn dev para iniciar el servidor Node.js
    CommandLine iniciarServidor = new CommandLine("cmd");
    iniciarServidor.addArgument("/c");
    iniciarServidor.addArgument(comandoServidorNodeJS);

    // Ejecutar los comandos utilizando commons-exec
    DefaultExecutor executor = new DefaultExecutor();
    executor.setWorkingDirectory(dirServidorNodeJS);
    executor.execute(cambioDirectorio);
    executor.execute(iniciarServidor);

    System.out.println("Servidor Node.js iniciado exitosamente.");
}


   private static void iniciarAplicacionReactJS() throws IOException {
    String directorioAplicacionReactJS = "D:\\Descargas\\PruebaDev\\RinconcitoFrontEnd";
    String comandoAplicacionReactJS = "yarn start";

    // Navegar al directorio de la aplicación React.js
    File dirAplicacionReactJS = new File(directorioAplicacionReactJS);
    CommandLine cambioDirectorio = new CommandLine("cmd");
    cambioDirectorio.addArgument("/c");
    cambioDirectorio.addArgument("cd");
    cambioDirectorio.addArgument(dirAplicacionReactJS.getAbsolutePath());

    // Ejecutar el comando yarn start para iniciar la aplicación React.js
    CommandLine iniciarAplicacion = new CommandLine("cmd");
    iniciarAplicacion.addArgument("/c");
    iniciarAplicacion.addArgument(comandoAplicacionReactJS);

    // Ejecutar los comandos utilizando commons-exec
    DefaultExecutor executor = new DefaultExecutor();
    executor.setWorkingDirectory(dirAplicacionReactJS);
    executor.execute(cambioDirectorio);
    executor.execute(iniciarAplicacion);

    System.out.println("Aplicación React.js iniciada exitosamente.");
}

}
