/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package individuos;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author dani
 */
public abstract class IndividuoGenetico {
    /**
     * Inicializa el individuo de la manera que el decida
     * Normalmente aleatoria
     * 
     * @param numeroElementosDiferentesPorColumna es necesario para generar el individuo
     */
    
    ArrayList<ReglaGenetica> reglas;
    
    public abstract void inicializaIndividuo(ArrayList<HashMap<String, Integer>> nElemDistintosPorColumna);
    public abstract ArrayList<IndividuoGenetico> cruzar(IndividuoGenetico individuo);
    public abstract IndividuoGenetico mutar();
    public abstract String getClase(ReglaGenetica reglaAComparar);
}
