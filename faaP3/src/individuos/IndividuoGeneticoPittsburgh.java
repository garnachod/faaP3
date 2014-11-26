package individuos;

import datos.Elemento;
import java.util.ArrayList;

/**
 *
 * @author Diego Castaño y Daniel Garnacho
 */
public class IndividuoGeneticoPittsburgh extends IndividuoGenetico {
    ArrayList<AtributoGenetico> condicion;
    AtributoGenetico conclusion;
    
    @Override
    public void inicializaIndividuo(ArrayList<Integer> numeroElementosDiferentesPorColumna){
        //por cada columna de clasificacion - 1 genera aleatoriamente
        //una serie de condiciones y al final una conclusion
        
    }

    @Override
    public ArrayList<IndividuoGenetico> cruzar(IndividuoGenetico individuo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IndividuoGenetico mutar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
