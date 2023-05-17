/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Rincon.Entidades;

import lombok.Data;

/**
 *
 * @author Rafael
 */
@Data
public class invoiceDetailModel extends platterModel{
    private int detailInvoiceId;
    private float unitPrice;
    private int invoiceDetailsStatus;
    private int invoiceId;
    private int platterId;
}
