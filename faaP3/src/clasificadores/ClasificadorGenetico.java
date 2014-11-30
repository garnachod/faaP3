package clasificadores;

import datos.Datos;
import datos.Elemento;
import datos.ElementoNominal;
import individuos.IndividuoGenetico;
import individuos.IndividuoGeneticoPittsburgh;
import individuos.ReglaGenetica;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author diego.castaño
 */
public class ClasificadorGenetico extends Clasificador {
    private int tam_poblacion = 100;
    private int nEpocas = 10;
    private IndividuoGenetico individuoClasificador;
    private ArrayList<IndividuoGenetico> poblacion = new ArrayList<>();
    private ArrayList<HashMap<String, Integer>> nElemDistintosPorColumna;
    
    @Override
    public void entrenamiento(Datos datosTrain) {
        //para poder generar individuos geneticos tenemos que buscar cuantos 
        //elementos son diferentes de cada columna de clasificacion
        Elemento[][] datos = datosTrain.getDatos();
        this.nElemDistintosPorColumna = new ArrayList<>();
        
        //se inicializan los hasmaps
        //se usan hashmaps por eficiencia
        for(Elemento e : datos[0]){
            HashMap<String, Integer> hashm = new HashMap<>();
            nElemDistintosPorColumna.add(hashm);
        }
        
        //se buscan los elementos distintos
        for(Elemento[] fila: datos){
            for(int i = 0; i<fila.length; i++){
                HashMap<String, Integer> hashmColumna = nElemDistintosPorColumna.get(i);
                if(hashmColumna.containsKey(fila[i].getValorNominal())){
                    //ya lo tenemos en el hashmap
                }else{
                    //no lo tenemos en el hashmap, por lo que lo insertamos
                    hashmColumna.put(fila[i].getValorNominal(), 1);
                }
            }
        }
        
        
        //ya tenemos los elementos distintos de cada columna de clasificacion
        //generamos la poblacion
        for(int i = 0; i < this.tam_poblacion ; i++){
            IndividuoGeneticoPittsburgh individuo = new IndividuoGeneticoPittsburgh();
            individuo.inicializaIndividuo(nElemDistintosPorColumna);
            this.poblacion.add(individuo);
        }
        
        //entrenamiento puro
        for(int i = 0; i < nEpocas; i++){
            //cruce
            //a pares
            ArrayList<IndividuoGenetico> nuevaPoblacion = new ArrayList<>();
            for(int j = 0; j < this.tam_poblacion; j = j +2){
                IndividuoGenetico ind1 = this.poblacion.get(j);
                IndividuoGenetico ind2 = this.poblacion.get(j+1);
                ArrayList<IndividuoGenetico> hijos =  ind1.cruzar(ind2);
                for(IndividuoGenetico ind : hijos){
                    nuevaPoblacion.add(ind);
                }
            }
            this.poblacion = nuevaPoblacion;
            //mutacion
        }
        
        
        //obtencion del mejor clasificador
        this.individuoClasificador = this.poblacion.get(0);
    }

    @Override
    public ArrayList<Elemento> clasifica(Datos datosTest) {
        ArrayList<Elemento> clasificacion = new ArrayList<>();
        Elemento[][] datos = datosTest.getDatos();
        
        for(Elemento[] fila: datos){
            ReglaGenetica individuoTest = this.generaReglaFila(fila);
            String claseStr = this.individuoClasificador.getClase(individuoTest);
            
            ElementoNominal clase = new ElementoNominal(claseStr);
            clasificacion.add(clase);
        }
        
        return clasificacion;
    }
    
    private ReglaGenetica generaReglaFila(Elemento[] fila){
        ReglaGenetica regla = new ReglaGenetica();
        regla.inicializaReglaAClasificar(nElemDistintosPorColumna, fila);
        return regla;
    }
}
