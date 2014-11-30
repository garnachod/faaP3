package individuos;

import datos.Elemento;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Diego Castaño y Daniel Garnacho
 */
public class IndividuoGeneticoPittsburgh extends IndividuoGenetico {
    ArrayList<ReglaGenetica> reglas;
    
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

    @Override
    public ArrayList<IndividuoGenetico> cruzar(IndividuoGenetico individuo) {
        //vamos a cruzar a nivel de atributos geneticos
        
    }

    @Override
    public IndividuoGenetico mutar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
