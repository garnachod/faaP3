/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package individuos;

import java.util.Random;

/**
 *
 * @author dani
 */
public class GestorRand {
    private static Random random = new Random();
    
    // Devuelve 1 ó 0
    public static int lanzarMoneda () {
        return random.nextInt(2);
    }
    
    public static int getInt(int n){
        return random.nextInt(n);
    }
    
    public static Double getDouble(){
        return random.nextDouble();
    }
}
