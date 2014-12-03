/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package individuos;

import datos.Elemento;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author dani
 */
public class ReglaGenetica implements Cloneable {
    ArrayList<AtributoGenetico> condiciones;
    ValorGenetico conclusion;
    
    public ReglaGenetica(){
        this.condiciones = new ArrayList<>();
    }
    
    public void inicializaReglaCondicionesAleatorias(ArrayList<HashMap<String, Integer>> nElemPerCondicion, String clase){
        //por cada columna de clasificacion - 1 genera aleatoriamente
        //una serie de condiciones y al final una conclusion
        for(HashMap<String, Integer> hsm : nElemPerCondicion){
            AtributoGenetico condicion = new AtributoGenetico();
            condicion.inicializaSignifAleatorio(hsm);
            this.condiciones.add(condicion);
        }

        this.conclusion = new ValorGenetico(true, clase);
    }
    
    public void inicializaReglaAClasificar(ArrayList<HashMap<String, Integer>> nElemPerCondicion, Elemento[] fila){
        int i = 0;
        for(HashMap<String, Integer> hsm : nElemPerCondicion){
            AtributoGenetico condicion = new AtributoGenetico();
            //condicion.inicializaSignificado(hsm);
            condicion.inicializaSignificadoClasificacion(hsm, fila[i]);
            this.condiciones.add(condicion);
            i++;
        }
    }
    
    public Boolean cumpleRegla(ReglaGenetica reglaToTest){
        
        for(int i = 0; i < this.condiciones.size(); i++){
            if(this.condiciones.get(i).cumpleCondicion(reglaToTest.condiciones.get(i)) == false){
                return false;
            }
        }
        return true;
    }
    public void mutaCondiciones(double probMutacion){
       
        for(AtributoGenetico atrib : this.condiciones){
            atrib.mutaValorGenetico(probMutacion);
        }
        
    }
    
    public String getValueConclusion(){
        return this.conclusion.getSignificado();
    }
    
    @Override
    public Object clone(){
        ReglaGenetica clon = new ReglaGenetica();
        
        for(AtributoGenetico a: this.condiciones){
            clon.condiciones.add((AtributoGenetico)a.clone());
        }
        clon.conclusion = (ValorGenetico)this.conclusion.clone();
        
        return clon;
    }
}
