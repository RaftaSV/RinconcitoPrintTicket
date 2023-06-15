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
public class startReact {
    
    public static void iniciarAplicacionReactJS() throws IOException {
    String directorioAplicacionReactJS = "D:/Descargas/PruebaDev/RinconcitoFrontEnd";
    String comandoAplicacionReactJS = "yarn start";

    // Navegar al directorio de la aplicación React.js
    File dirAplicacionReactJS = new File(directorioAplicacionReactJS);
    CommandLine cambioDirectorio = new CommandLine("cmd");
    cambioDirectorio.addArgument("/c");
    cambioDirectorio.addArgument("cd");
    cambioDirectorio.addArgument(dirAplicacionReactJS.getAbsolutePath());

    // Ejecutar el comando yarn start para iniciar la aplicación React.js
    CommandLine iniciarAplicacion = new CommandLine("cmd");
    iniciarAplicacion.addArgument("/c");
    iniciarAplicacion.addArgument(comandoAplicacionReactJS);

    // Ejecutar los comandos utilizando commons-exec
    DefaultExecutor executor = new DefaultExecutor();
    executor.setWorkingDirectory(dirAplicacionReactJS);
    executor.execute(cambioDirectorio);
    executor.execute(iniciarAplicacion);

    System.out.println("Aplicación React.js iniciada exitosamente.");
}
    
}
