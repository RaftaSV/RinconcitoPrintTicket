/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Rincon.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

/**
 *
 * @author rafap
 */
public class PrintTicket {
    public void Print  () {
       int statePrint = 0;
    while (true) {
      // Pero usamos un truco y hacemos un ciclo infinito
      
      try {
        
        // En él, hacemos que el hilo duerma
        Thread.sleep(1000);
        // Y después realizamos las operaciones
        System.out.println("Me imprimo cada segundo");
        
  
          try {
                  String path = "C:\\ticket\\impresion.txt";
		    //Guardamos es archivo en la ruta abterior
		      FileInputStream inputStream = null;

		    try {
		    	//llamamos el archivo de la ruta 
		        inputStream = new FileInputStream(path);
                       
                        
		    } catch (FileNotFoundException ex) {
		     // statePrint=0;
		        System.out.println("Error al leer");
		    }
		   
		    

		     DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
		   
		    Doc document = new SimpleDoc(inputStream, docFormat,null);
                    
		      PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
     
		    if (defaultPrintService != null) {
		          DocPrintJob printJob = defaultPrintService.createPrintJob();
		        try {
                            if(statePrint==0){
                             statePrint=1;
                             System.out.println("Imprimiendo");
                             printJob.print(document, null);
                             printClass printer = new printClass();
		             byte[] cutP = new byte[] { 0x1d, 'V', 1 };
		             printer.printBytes(defaultPrintService.getName(), cutP);
                            
                              inputStream.close();
                              
                          
                            }
		          //  JOptionPane.showMessageDialog(null, "imprimiendo");
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
              
		    } else {
		    	 //JOptionPane.showMessageDialog(null, "No existen impresoras instaladas");
		    	System.out.println("No existen impresoras instaladas");
		    }
                    
                    

		
          } catch (Exception e) {
              System.out.println(e);
          }
         
                   
      try {
           Path file = Paths.get("C:\\ticket\\impresion.txt");
            Files.delete(file);
            System.out.println("deleted");
        } catch (Exception e) {
            System.out.println( "error" + e);
            
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    }
    
}
