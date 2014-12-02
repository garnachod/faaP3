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
    private static final Double probMutacion = 0.1;
    private static final Double probCruce = 0.6;
    
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

    
    
    
    private ArrayList<IndividuoGenetico> cruzarUniforme(IndividuoGenetico individuo) {
        
        ArrayList<IndividuoGenetico> hijos = new ArrayList<>();

        IndividuoGeneticoPittsburgh hijo1 = (IndividuoGeneticoPittsburgh) this.clone();
        IndividuoGeneticoPittsburgh hijo2 = (IndividuoGeneticoPittsburgh) individuo.clone();
        
        
        // Para cada atributo se lanza moneda. Si sale cara se coge atributo de la madre
        for (int i = 0; i < individuo.reglas.size(); i++) {
            for (int j = 0; j < individuo.reglas.get(i).condiciones.size(); j++) {
               if (GestorRand.lanzarMoneda() == 0) {
                   hijo1.reglas.get(i).condiciones.remove(j);
                   hijo1.reglas.get(i).condiciones.add(j, individuo.reglas.get(i).condiciones.get(j));
               }
            }
        }
        
        // Para cada atributo se lanza moneda. Si sale cara se coge atributo de la madre
        for (int i = 0; i < individuo.reglas.size(); i++) {
            for (int j = 0; j < individuo.reglas.get(i).condiciones.size(); j++) {
               if (GestorRand.lanzarMoneda() == 0) {
                   hijo2.reglas.get(i).condiciones.remove(j);
                   hijo2.reglas.get(i).condiciones.add(j, this.reglas.get(i).condiciones.get(j));
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
    public void mutar() {
        
    }
    
    private void mutacionAtributoGenetico(){
        Double rand = GestorRand.getDouble();
        if(rand <= probMutacion){
            int nReglas = this.reglas.size();
            int indexRegla = GestorRand.getInt(nReglas);
            this.reglas.get(indexRegla).mutaUnaCondicion();
        }
    }
    @Override
    public Object clone(){
        IndividuoGeneticoPittsburgh clon = new IndividuoGeneticoPittsburgh();
        for(ReglaGenetica r : this.reglas){
            clon.reglas.add((ReglaGenetica)r.clone());
        }
        return clon;
    }

}
