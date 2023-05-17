/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Rincon.Entidades;

import lombok.Data;
import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Rafta
 */
@Data
public class orderModel {
    private int orderId;
    private Time orderTime;
    private Date orderDate;
    private int orderType;
    private String address;
    private String customer;
    private String numberPhone;
    private String otherDetail;
    private int tableId;
    private int numberTable;
    private int userId;
    private String user;
    private int orderStatus;
    
}
