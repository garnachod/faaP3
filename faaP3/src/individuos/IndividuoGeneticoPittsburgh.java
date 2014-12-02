package individuos;

import datos.Elemento;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Diego Castaño y Daniel Garnacho
 */
public class IndividuoGeneticoPittsburgh extends IndividuoGenetico {

    private static Random random = new Random();
    
    public IndividuoGeneticoPittsburgh(){
        this.reglas = new ArrayList<>();
    }
    
    @Override
    public void inicializaIndividuo(ArrayList<HashMap<String, Integer>> nElemDistintosPColum){
        //por cada clase vamos a generar una regla aleatoria
        HashMap<String, Integer> clasesHSM =  nElemDistintosPColum.get(nElemDistintosPColum.size()-1);
        nElemDistintosPColum.remove(nElemDistintosPColum.size()-1);
        for(String clase: clasesHSM.keySet()){
            ReglaGenetica regla = new ReglaGenetica();
            regla.inicializaReglaCondicionesAleatorias(nElemDistintosPColum, clase);
            this.reglas.add(regla);
        }
        nElemDistintosPColum.add(clasesHSM);
    }
    
    @Override
    public String getClase(ReglaGenetica reglaAComparar){
        ReglaGenetica ultimaRegla = null;
        for(ReglaGenetica regla : this.reglas ){
            ultimaRegla = regla;
            if(regla.cumpleRegla(reglaAComparar)){
                return regla.getValueConclusion();
            }
        }
       return ultimaRegla.getValueConclusion();
    }

    
    // Devuelve 1 ó 0
    private int lanzarMoneda () {
        return random.nextInt(2);
    }
    
    private ArrayList<IndividuoGenetico> cruzarUniforme(IndividuoGenetico individuo) {
        
        ArrayList<IndividuoGenetico> hijos = new ArrayList<>();
        IndividuoGeneticoPittsburgh hijo1 = new IndividuoGeneticoPittsburgh();
        IndividuoGeneticoPittsburgh hijo2 = new IndividuoGeneticoPittsburgh();
        
        // Inicializar reglas como el padre
        hijo1.reglas = this.reglas;
        hijo2.reglas = this.reglas;
        
        // Para cada atributo se lanza moneda. Si sale cara se coge atributo de la madre
        for (int i = 0; i < hijo1.reglas.size(); i++) {
            for (int j = 0; j < hijo1.reglas.get(i).condiciones.size(); j++) {
               if (lanzarMoneda() == 0) {
                   hijo1.reglas.get(i).condiciones.remove(j);
                   hijo1.reglas.get(i).condiciones.add(individuo.reglas.get(i).condiciones.get(j));
               }
            }
        }
        
        // Para cada atributo se lanza moneda. Si sale cara se coge atributo de la madre
        for (int i = 0; i < hijo2.reglas.size(); i++) {
            for (int j = 0; j < hijo2.reglas.get(i).condiciones.size(); j++) {
               if (lanzarMoneda() == 0) {
                   hijo2.reglas.get(i).condiciones.remove(j);
                   hijo2.reglas.get(i).condiciones.add(individuo.reglas.get(i).condiciones.get(j));
               }
            }
        }
        
        hijos.add(hijo1);
        hijos.add(hijo2);
        return hijos;
    }
    
    @Override
    public ArrayList<IndividuoGenetico> cruzar(IndividuoGenetico individuo) {
        return cruzarUniforme(individuo);
    }
        
    @Override
    public IndividuoGenetico mutar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
