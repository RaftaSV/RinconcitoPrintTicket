/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Rincon.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author rafap
 */
public class Conexion {
    	private Connection con;
	
	public Conexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/restaurants?serverTimezone=UTC", "root", "root");
			System.out.println("CONECTADO A LA BD");

		} catch (ClassNotFoundException | SQLException e) {
                    JOptionPane.showMessageDialog( null , "ERROR DE CONEXION A LA BD "+ e);
			
		}
	}

	public Connection retornarConexion() {
		return con;
	}
          public  Connection cerrarConexion () {
        try {
            System.out.println("Cerrando conexion");
        } catch (Exception e) {
              System.out.println("Error cerrando  conexion");
                JOptionPane.showConfirmDialog(null, e); 
        }
        con = null;
    return con;
    }
}
