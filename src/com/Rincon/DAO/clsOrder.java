package com.Rincon.DAO;

import com.Rincon.Entidades.*;
import com.Rincon.Conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
public class clsOrder {
        Conexion con = new Conexion();
        Connection conectar = con.retornarConexion();
    public ArrayList<orderModel> obtenerOrdenes() throws SQLException {
        ArrayList<orderModel> orders = new ArrayList<>();

         String query = "{CALL SP_S_ORDERNUM}";

        try (CallableStatement statement = conectar.prepareCall(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                orderModel order = new orderModel();
                order.setOrderId(resultSet.getInt("orderId"));
                order.setOrderTime(java.sql.Time.valueOf(resultSet.getString("orderTime")));
                order.setOrderDate(java.sql.Date.valueOf(resultSet.getString("orderDate")));
                order.setOrderType(resultSet.getInt("orderType"));
                order.setAddress(resultSet.getString("address"));
                order.setCustomer(resultSet.getString("customer"));
                order.setNumberPhone(resultSet.getString("numberPhone"));
                order.setOtherDetail(resultSet.getString("otherDetail"));
                order.setTableId(resultSet.getInt("tableId"));
                order.setUserId(resultSet.getInt("userId"));
                order.setOrderStatus(resultSet.getInt("orderStatus"));
                orders.add(order);
            }
        }

        return orders;
    }
 
    public ArrayList<orderDetailModel> obtenerOrdenes(int orderId) throws SQLException {
        ArrayList<orderDetailModel> ordenes = new ArrayList<>();


            try {
            CallableStatement statement = conectar.prepareCall("CALL SP_ORDERDATA(?)");
            statement.setInt("pId", orderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                orderDetailModel order = new orderDetailModel();
                order.setOrderId(resultSet.getInt("orderId"));
                order.setOrderTime(resultSet.getTime("orderTime"));
                order.setOrderDate(resultSet.getDate("orderDate"));
                order.setOrderType(resultSet.getInt("orderType"));
                order.setAddress(resultSet.getString("address"));
                order.setNumberPhone(resultSet.getString("numberPhone"));
                order.setCustomer(resultSet.getString("customer"));
                order.setUser(resultSet.getString("userName"));
                 order.setNumberTable(resultSet.getInt("tableNumber"));
                order.setOtherDetail(resultSet.getString("otherDetail"));
                order.setPlatterName(resultSet.getString("platterName"));
                order.setPlatterPrice(resultSet.getDouble("platterPrice"));
                ordenes.add(order);
                           }
        } catch (Exception e){
            System.out.println(e);
        }

        return ordenes;
    }

    public void updateOrder(int id){
        
        try {
            CallableStatement statement = conectar.prepareCall("CALL SP_U_ORDER(?)");
            statement.setInt("pId", id);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Orden actualizada");
        } catch (Exception e ){
            System.out.println(e);
        }
    }

}
