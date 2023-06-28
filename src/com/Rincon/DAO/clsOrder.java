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
 
  public ArrayList<orderDetailModel> obtenerOrdenes(int orderId) {
    ArrayList<orderDetailModel> ordenes = new ArrayList<>();

    try {
        CallableStatement statement = conectar.prepareCall("CALL SP_ORDERDATA(?)");
        statement.setInt(1, orderId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            orderDetailModel order = new orderDetailModel();
            order.setDetailsOrderId(resultSet.getInt("detailsOrderId"));
            order.setOrderId(resultSet.getInt("orderId"));
            order.setOrderTime(resultSet.getTime("orderTime"));
            order.setOrderDate(resultSet.getDate("orderDate"));
            order.setOrderType(resultSet.getInt("orderType"));
            order.setAddress(resultSet.getString("address"));
            order.setNumberPhone(resultSet.getString("numberPhone"));
            order.setCustomer(resultSet.getString("customer"));
            order.setUser(resultSet.getString("userName"));
            if (resultSet.getInt("orderType") == 0) {
                order.setNumberTable(resultSet.getString("tableNumber"));
            }
            order.setOtherDetail(resultSet.getString("otherDetail"));
            order.setPlatterName(resultSet.getString("platterName"));
            order.setPlatterPrice(resultSet.getDouble("platterPrice"));
            ordenes.add(order);
        }
    } catch (Exception e) {
        System.out.println("Error obteniendo los datos: " + e);
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
    
    public void updateOrderDetails(ArrayList<orderDetailModel> orderDetails) throws SQLException {
    System.out.println("Actualizando detalles de la orden");

    // Establece la conexión en modo de solo lectura
    conectar.setReadOnly(false);

    try {
        // deshabilita el modo de autocommit
        conectar.setAutoCommit(false);

        // prepara la consulta de actualización
        String updateQuery = "UPDATE orderDetails SET detailOrderStatus = ? WHERE detailsOrderId = ?";
        try (PreparedStatement statement = conectar.prepareStatement(updateQuery)) {

            // actualiza el estado de cada objeto OrderDetailModel a 1
            for (orderDetailModel orderDetail : orderDetails) {
                // establece los parámetros de la consulta de actualización
                statement.setInt(1, 1);
                statement.setInt(2, orderDetail.getDetailsOrderId());

                // ejecuta la consulta de actualización
                statement.executeUpdate();
            }

            // si todo está bien, confirma la transacción
            conectar.commit();
            System.out.println("Detalles de la orden actualizados");
        }
    } catch (SQLException e) {
        // en caso de excepción, revierte la transacción
        if (conectar != null) {
            try {
                conectar.rollback();
            } catch (SQLException rollbackException) {
                // Manejar la excepción de rollback
                System.out.println("Error al revertir la transacción: " + rollbackException.getMessage());
            }
        }
        System.out.println("Error en la transacción: " + e.getMessage());
    } finally {
        // Restablece el modo de autocommit y cierra la conexión
        try {
            conectar.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error al restablecer el modo de autocommit: " + e.getMessage());
        }
        
        try {
            conectar.setReadOnly(true);
        } catch (SQLException e) {
            System.out.println("Error al restablecer el modo de solo lectura: " + e.getMessage());
        }
        
        try {
            conectar.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
     
    public ArrayList<orderModel> obtenerOrdenesAgregadoProductos() throws SQLException {
     
    ArrayList<orderModel> orders = new ArrayList<>();

    String query = "select * from orders where orderStatus = 2 and orderDate = curdate()  limit 1;";

    try (PreparedStatement statement = conectar.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            orderModel order = new orderModel();
            order.setOrderId(resultSet.getInt("orderId"));
            orders.add(order);
        }
    }

    return orders;
}
 
         public ArrayList<orderDetailModel> obtenerOrdenesMasProductos(int orderId) {
    ArrayList<orderDetailModel> ordenes = new ArrayList<>();

    try {
        CallableStatement statement = conectar.prepareCall("CALL SP_ORDERDATAMORE(?)");
        statement.setInt(1, orderId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            orderDetailModel order = new orderDetailModel();
            order.setDetailsOrderId(resultSet.getInt("detailsOrderId"));
            order.setOrderId(resultSet.getInt("orderId"));
            order.setOrderTime(resultSet.getTime("orderTime"));
            order.setOrderDate(resultSet.getDate("orderDate"));
            order.setOrderType(resultSet.getInt("orderType"));
            order.setAddress(resultSet.getString("address"));
            order.setNumberPhone(resultSet.getString("numberPhone"));
            order.setCustomer(resultSet.getString("customer"));
            order.setUser(resultSet.getString("userName"));
            if (resultSet.getInt("orderType") == 0) {
                order.setNumberTable(resultSet.getString("tableNumber"));
            }
            order.setOtherDetail(resultSet.getString("otherDetail"));
            order.setPlatterName(resultSet.getString("platterName"));
            order.setPlatterPrice(resultSet.getDouble("platterPrice"));
            ordenes.add(order);
        }
    } catch (Exception e) {
        System.out.println("Error obteniendo los datos: " + e);
    }

    return ordenes;
}

}
