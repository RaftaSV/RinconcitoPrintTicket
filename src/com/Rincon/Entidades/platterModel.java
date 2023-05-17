/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Rincon.Entidades;
import lombok.Data;

/**
 *
 * @author rafap
 */

@Data
public class platterModel {
 
    private int platterId;
    private String platterName;
    private double price;
    private double cost;
    private String platterDetail;
    private String platterImage;
    private int categoryId;
    private int platterStatus;
}

