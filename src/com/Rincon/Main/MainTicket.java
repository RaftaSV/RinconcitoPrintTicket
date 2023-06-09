/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.Rincon.Main;
import com.Rincon.Conexion.Conexion;
import com.Rincon.DAO.clsOrder;
import com.Rincon.Entidades.orderModel;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MainTicket {
    public static void main(String[] args) {
        Conexion con = new Conexion();
        Connection conectar = con.retornarConexion();
        CreateTicket create = new CreateTicket();
        CreateTicketOrder createTicketOrder = new CreateTicketOrder();
        CreateTicketOrderAddMoreProducts moreProducts = new CreateTicketOrderAddMoreProducts();
        String idTicket = null;
        int idOrder = 0;
        clsOrder order = new clsOrder();
        startReact react = new startReact();
        startNode node = new startNode();
         Thread nodeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Iniciar servidor Node.js con yarn dev
                try {
                 // node.iniciarServidorNodeJS();
                } catch (Exception e) {
                    System.out.println(e);
                }
                
            }
        });

        Thread reactThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Iniciar aplicación React.js con yarn start
                try {
                 // react.iniciarAplicacionReactJS();  
                } catch (Exception e) {
                    System.out.println(e);
                }
               
            }
        });

        nodeThread.start();
        reactThread.start();
        try {
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
                     try {
                        ArrayList<orderModel> newOrder = order.obtenerOrdenesAgregadoProductos();

                        for (orderModel orderid : newOrder) {
                            idOrder = orderid.getOrderId();
                        }

                        if (idOrder != 0) {
                            moreProducts.imprimir(idOrder);
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


}
