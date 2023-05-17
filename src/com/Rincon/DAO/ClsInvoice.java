package com.Rincon.DAO;


import com.Rincon.Entidades.invoiceModel;
import com.Rincon.Conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;




/**
 *
 * @author Rafael
 */
public class ClsInvoice {
	
    Conexion con = new Conexion();
    Connection conectar = con.retornarConexion();
       
    
        public ArrayList<invoiceModel> Factura(int id) {
        ArrayList<invoiceModel> factura = new ArrayList<>();

        try {
            CallableStatement cs = conectar.prepareCall("call SP_DatosFactura(?)");
            cs.setInt("pId", id);
            ResultSet resultado = cs.executeQuery();
            while (resultado.next()) {
                invoiceModel facturas = new invoiceModel();
                facturas.setInvoiceId(Integer.valueOf(resultado.getString("invoiceId")));
                facturas.setInvoiceDate(java.sql.Date.valueOf(resultado.getString("invoiceDate")));
                facturas.setInvoiceTotal(Float.parseFloat(resultado.getString("invoiceTotal")));
                facturas.setUser(resultado.getString("userName"));
                facturas.setCash(Float.parseFloat(resultado.getString("cash")));
                facturas.setInvoiceChange(Float.parseFloat(resultado.getString("InvoiceChange")));
                facturas.setInvoiceTime(java.sql.Time.valueOf(resultado.getString("InvoiceTime")));
                factura.add(facturas);

            }

            

        } catch (Exception e) {
            System.out.println(e);
        }

        return factura;
    }

}
