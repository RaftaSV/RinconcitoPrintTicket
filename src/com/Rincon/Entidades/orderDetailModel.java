/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Rincon.Entidades;

import lombok.Data;

/**
 *
 * @author Rafta
 */
@Data
public class orderDetailModel extends orderModel{
    private int detailsOrderId;
    private int orderId;
    private String platterName;
    private double platterPrice;
    private int detailOrderStatus;
}
