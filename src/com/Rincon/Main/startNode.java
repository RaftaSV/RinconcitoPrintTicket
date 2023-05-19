/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Rincon.Main;

import java.io.File;
import java.io.IOException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

/**
 *
 * @author Rafta
 */
public class startNode {
 public static void iniciarServidorNodeJS() throws IOException {
    String directorioServidorNodeJS = "D:\\Descargas\\PruebaDev\\NodeYRender";
    String comandoServidorNodeJS = "yarn dev";

    // Navegar al directorio del servidor Node.js
    File dirServidorNodeJS = new File(directorioServidorNodeJS);
    CommandLine cambioDirectorio = new CommandLine("cmd");
    cambioDirectorio.addArgument("/c");
    cambioDirectorio.addArgument("cd");
    cambioDirectorio.addArgument(dirServidorNodeJS.getAbsolutePath());

    // Ejecutar el comando yarn dev para iniciar el servidor Node.js
    CommandLine iniciarServidor = new CommandLine("cmd");
    iniciarServidor.addArgument("/c");
    iniciarServidor.addArgument(comandoServidorNodeJS);

    // Ejecutar los comandos utilizando commons-exec
    DefaultExecutor executor = new DefaultExecutor();
    executor.setWorkingDirectory(dirServidorNodeJS);
    executor.execute(cambioDirectorio);
    executor.execute(iniciarServidor);

    System.out.println("Servidor Node.js iniciado exitosamente.");
}
   
}
