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
public abstract class IndividuoGenetico {
    /**
     * Inicializa el individuo de la manera que el decida
     * Normalmente aleatoria
     * 
     * @param numeroElementosDiferentesPorColumna es necesario para generar el individuo
     */
    public abstract void inicializaIndividuo(ArrayList<Integer> numeroElementosDiferentesPorColumna);
    
    //añadir más funcionalidad como cruzar o mutar
}
