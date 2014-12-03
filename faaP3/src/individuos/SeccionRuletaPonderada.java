/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package individuos;

/**
 *
 * @author dani
 */
public class SeccionRuletaPonderada {
    private IndividuoGenetico individuo;
    private int aciertos;
    
    public SeccionRuletaPonderada(IndividuoGenetico ind){
        this.individuo = ind;
        this.aciertos = 0;
    }
    
    public void testClase(ReglaGenetica reglaAComparar, String clase){
        if(this.individuo.getClase(reglaAComparar).equals(clase)){
            this.aciertos++;
        }
    }
    public int getAciertos(){
        return this.aciertos*4;
    }
    public IndividuoGenetico getIndividuo(){
        return this.individuo;
    }
}
