/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package individuos;

import java.util.ArrayList;

/**
 *
 * @author dani
 */
public class RuletaPonderada {
    ArrayList<SeccionRuletaPonderada> secciones;
    private int sumaTotal;
    
    public RuletaPonderada(){
        this.secciones = new ArrayList<>();
        this.sumaTotal = 0;
    }
    
    public void addElemento(SeccionRuletaPonderada sec){
        this.sumaTotal += sec.getAciertos();
        this.secciones.add(sec);
    }
    
    public IndividuoGenetico getIndividuo(){
        int rand = GestorRand.getInt(sumaTotal);
        int i, suma;
        
        if(rand == 0){
            return this.secciones.get(0).getIndividuo();
        }
        
        for(i = 0, suma = 0; suma < rand; i++){
            suma += this.secciones.get(i).getAciertos();
        }
        i--;
        return this.secciones.get(i).getIndividuo();
    }
    
    public IndividuoGenetico getMejor(){
        int i = 0;
        int mejor = 0;
        SeccionRuletaPonderada mejorS = null;
                
        for(SeccionRuletaPonderada s : this.secciones){
            if(mejor < s.getAciertos()){
                mejorS = s;
                mejor = s.getAciertos();
            }
        }
        
        return mejorS.getIndividuo();
    }
    public SeccionRuletaPonderada getMejorSeccion(){
        int i = 0;
        int mejor = 0;
        SeccionRuletaPonderada mejorS = null;
                
        for(SeccionRuletaPonderada s : this.secciones){
            if(mejor < s.getAciertos()){
                mejorS = s;
                mejor = s.getAciertos();
            }
        }
        
        return mejorS;
    }
    
    public int getSuma(){
        return this.sumaTotal;
    }
}
