package clasificadores;

import datos.Datos;
import datos.Elemento;
import datos.ElementoNominal;
import individuos.GestorRand;
import individuos.IndividuoGenetico;
import individuos.IndividuoGeneticoPittsburgh;
import individuos.ReglaGenetica;
import individuos.RuletaPonderada;
import individuos.SeccionRuletaPonderada;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author diego.castaño
 */
public class ClasificadorGenetico extends Clasificador {
    private final int tam_poblacion = 100;
    private final int nEpocas = 3000;
    private final int nEpocasSinMejora = 300;
    private int nEpocasSMCount = 0;
    private int lastMejorSum = 0;
    private IndividuoGenetico individuoClasificador = null;
    private ArrayList<IndividuoGenetico> poblacion = new ArrayList<>();
    private ArrayList<HashMap<String, Integer>> nElemDistintosPorColumna;
    
    @Override
    public void entrenamiento(Datos datosTrain) {
        //para poder generar individuos geneticos tenemos que buscar cuantos 
        //elementos son diferentes de cada columna de clasificacion
        Elemento[][] datos = datosTrain.getDatos();
        this.nElemDistintosPorColumna = new ArrayList<>();
        this.poblacion = new ArrayList<>();
        this.individuoClasificador = null;
        this.lastMejorSum = 0;
        this.nEpocasSMCount = 0;
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
            Collections.shuffle(this.poblacion);
            for(int j = 0; j < this.tam_poblacion ; j+=2){
                
                IndividuoGenetico ind1 = this.poblacion.get(j);
                IndividuoGenetico ind2 = this.poblacion.get(j+1);
                
                ArrayList<IndividuoGenetico> hijos =  ind1.cruzar(ind2);
                for(IndividuoGenetico ind : hijos){
                    nuevaPoblacion.add(ind);
                }
                
            }
            
            this.poblacion = nuevaPoblacion;
            //mutacion
            for(int j = 0; j < this.tam_poblacion; j++){
                IndividuoGenetico ind = this.poblacion.get(j);
                ind.mutar();
            }
           
            //seleccion de progenitores
                //ruleta ponderada
            ArrayList<SeccionRuletaPonderada> seccionesRuleta = new ArrayList<>();
                //generamos las secciones
            for(int j = 0; j < this.tam_poblacion; j++){
                IndividuoGenetico ind = this.poblacion.get(j);
                SeccionRuletaPonderada seccion = new SeccionRuletaPonderada(ind);
                seccionesRuleta.add(seccion);
            }
                //por cada elemento en test, llamamos a test de la seccion
            for(Elemento[] fila: datos){
                ReglaGenetica individuoTest = this.generaReglaFila(fila);
                String clase = fila[fila.length-1].getValorNominal();
                for(int j = 0; j < seccionesRuleta.size(); j++){
                    SeccionRuletaPonderada seccion = seccionesRuleta.get(j);
                    seccion.testClase(individuoTest, clase);
                }
            }
                //add a la ruleta de las secciones
                //generamos la ruleta
            RuletaPonderada ruleta = new RuletaPonderada();
            for(SeccionRuletaPonderada s: seccionesRuleta ){
                ruleta.addElemento(s);
            }
            //System.out.println(ruleta.getSuma());
            nuevaPoblacion = new ArrayList<>();
            
            for(int j = 0; j < this.tam_poblacion - 2; j++){
                nuevaPoblacion.add((IndividuoGenetico)ruleta.getIndividuo().clone());
            }
            SeccionRuletaPonderada mejorSeccion = ruleta.getMejorSeccion();
            if(this.lastMejorSum <= mejorSeccion.getAciertos()){
                this.individuoClasificador = mejorSeccion.getIndividuo();
                this.lastMejorSum  = mejorSeccion.getAciertos();
                this.nEpocasSMCount = 0;
            }
            this.nEpocasSMCount++;
            if(this.nEpocasSMCount > nEpocasSinMejora){
                //salimos del bucle de entrenamiento si en nEpocasSinMejora no ha mejorado el clasificador
                break;
            }
                    
            nuevaPoblacion.add((IndividuoGenetico)individuoClasificador.clone());
            
            if(i%32 == 0){
                //sangre nueva
                IndividuoGeneticoPittsburgh individuo = new IndividuoGeneticoPittsburgh();
                individuo.inicializaIndividuo(nElemDistintosPorColumna);
                nuevaPoblacion.add(individuo);
                System.out.println(this.lastMejorSum);
            }else{
                nuevaPoblacion.add((IndividuoGenetico)individuoClasificador.clone());
            }
            
            
            
            this.poblacion = nuevaPoblacion;
        }
        
    }

    @Override
    public ArrayList<Elemento> clasifica(Datos datosTest) {
        ArrayList<Elemento> clasificacion = new ArrayList<>();
        Elemento[][] datos = datosTest.getDatos();
        
        for(Elemento[] fila: datos){
            ReglaGenetica individuoTest = this.generaReglaFila(fila);
            String claseStr = this.individuoClasificador.getClase(individuoTest);
            //System.out.println(claseStr);
            ElementoNominal clase = new ElementoNominal(claseStr);
            clasificacion.add(clase);
        }
        
        
        return clasificacion;
    }
    
    private ReglaGenetica generaReglaFila(Elemento[] fila){
        HashMap<String, Integer> clasesHSM =  nElemDistintosPorColumna.get(nElemDistintosPorColumna.size()-1);
        nElemDistintosPorColumna.remove(nElemDistintosPorColumna.size()-1);

        ReglaGenetica regla = new ReglaGenetica();
        regla.inicializaReglaAClasificar(nElemDistintosPorColumna, fila);
        nElemDistintosPorColumna.add(clasesHSM);
        
        return regla;
    }
}
