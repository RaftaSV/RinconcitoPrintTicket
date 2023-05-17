/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.Rincon.Main;

import com.Rincon.Conexion.Conexion;
import com.Rincon.DAO.clsOrder;
import com.Rincon.Entidades.orderDetailModel;
import com.Rincon.Entidades.orderModel;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author rafap
 */
public class MainTicket {
   public static void main(String[] args) {
        Conexion con = new Conexion();
        Connection conectar = con.retornarConexion();
        CreateTicket create = new CreateTicket();
        CreateTickectOrder createTicketOrder = new CreateTickectOrder();
        String idTicket = null;
        int idOrder = 0;
        clsOrder  order = new clsOrder();

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
                 try{
            ArrayList<orderModel> newOrder = order.obtenerOrdenes();
            
            for (orderModel orderid : newOrder ){
                idOrder = orderid.getOrderId();    
            }
         
            
       if(idOrder!=0){
           createTicketOrder.imprimir(idOrder);
           order.updateOrder(idOrder);
                idOrder = 0;
       }
            
        }catch(Exception e) {
            System.out.println(e);
            
     }
            }
            
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            con.cerrarConexion();
        }
      
   }
}

