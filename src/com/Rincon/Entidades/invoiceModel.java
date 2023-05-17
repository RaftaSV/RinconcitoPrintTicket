/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Rincon.Entidades;
import java.sql.Time;
import java.util.Date;

import lombok.Data;

/**
 *
 * @author Rafael
 */
@Data
public class invoiceModel {
 private int invoiceId;
    private Date invoiceDate;
    private Time invoiceTime;
    private int userId;
    private String user;
    private float invoiceTotal;
    private float cash;
    private float invoiceChange;
    private int invoiceStatus;
}
