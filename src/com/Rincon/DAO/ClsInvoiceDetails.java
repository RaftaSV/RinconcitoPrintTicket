package com.Rincon.DAO;


import com.Rincon.Entidades.invoiceDetailModel;
import com.Rincon.Conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author Rafael
 */
public class ClsInvoiceDetails {

    Conexion con = new Conexion();
    Connection conectar = con.retornarConexion();
       
    public ArrayList<invoiceDetailModel> listaDetallesFacturas(int id) {
        ArrayList<invoiceDetailModel> detalles = new ArrayList<>();

        try {
            CallableStatement cs = conectar.prepareCall("call SP_DETALLES(?)");
            cs.setInt("pId", id);
            ResultSet resultado = cs.executeQuery();
            while (resultado.next()) {
                invoiceDetailModel detallesfactura = new invoiceDetailModel();
                detallesfactura.setPlatterName(resultado.getString("PlatterName"));
                detallesfactura.setUnitPrice(Float.parseFloat(resultado.getString("UnitPrice")));
                detalles.add(detallesfactura);
            }
          
        } catch (Exception e) {
            System.out.println(e);
        }

        return detalles;
    }
     

}
