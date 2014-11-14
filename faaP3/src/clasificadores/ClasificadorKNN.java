package clasificadores;

import datos.Datos;
import datos.Elemento;
import datos.ElementoContinuo;
import datos.TiposDeAtributos;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Daniel Garnacho y Diego Castaño
 */
public class ClasificadorKNN extends Clasificador {
    
    Elemento datosTrain[][];
    double maximos[];
    double minimos[];
    int K = 3;
    
    @Override
    public void entrenamiento(Datos datosTrain) {

        this.datosTrain = datosTrain.getDatos();
        

        // Recalcular máximos y mínimos
        this.maximos = new double[this.datosTrain[0].length];
        this.minimos = new double[this.datosTrain[0].length];
        this.calcularMaxMin();
        
        // Normalizar
        this.normalizarDatosTrain();
    }
    
    private double maximo (int columna) {
        double max = -Double.MAX_VALUE;
        for (Elemento[] dato : this.datosTrain) {
            if (dato[columna].getValorContinuo() > max) {
                max = dato[columna].getValorContinuo();
            }
        }
        return max;
    }
    
    private double minimo (int columna) {
        double min = Double.MAX_VALUE;
        for (Elemento[] dato : this.datosTrain) {
            if (dato[columna].getValorContinuo() < min) {
                min = dato[columna].getValorContinuo();
            }
        }
        return min;
    }
    
    private void calcularMaxMin() {
        
        if (this.datosTrain == null)
            return;
        
        // Para cada columna
        for (int i = 0; i < (this.datosTrain[0].length - 1); i++) {
            
            // Si es de tipo continuo
            if (datosTrain[0][i].getTipo() == TiposDeAtributos.Continuo) {
                
                // Encontrar máximo y mínimo
                this.maximos[i] = maximo(i);
                this.minimos[i] = minimo(i);
            }
            
        }
    }
    
    private Elemento[] normalizarFila (Elemento[] fila) {
        for (int i = 0; i < (fila.length - 1); i++) {
            if (fila[i].getTipo() == TiposDeAtributos.Continuo) {
                double valor = fila[i].getValorContinuo();
                double min = minimos[i];
                double max = maximos[i];
                if ((max - min) != 0) {
                    double valorNorm = (valor - min) / (max - min);
                    ((ElementoContinuo)fila[i]).setValor(valorNorm);
                }
            }
        }
        return fila;
    }
    
    private void normalizarDatosTrain () {
        int tam = this.datosTrain.length;
        for (int i = 0; i < tam; i++) {
            this.datosTrain[i] = this.normalizarFila(this.datosTrain[i]);
        }
    }
    
   /*
    * Calcula distancia euclidea al cuadrado
    * @param filaA Fila normalizada
    * @param filaB Fila normalizada
    */
    public double distancia (Elemento[] filaA, Elemento[] filaB) {
        double distancia = 0;
        
        for (int i = 0; i < (filaA.length - 1); i++) {
            distancia += filaA[i].diferencia(filaB[i]);
        }
        return distancia;
    }
    
    @Override
    public ArrayList<Elemento> clasifica(Datos datosTest) {
        ArrayList<Elemento> clases = new ArrayList();
        
        // Ir normalizando filas !!
        for(Elemento[] filaTest: datosTest.getDatos()){
            filaTest = this.normalizarFila (filaTest);
            double distancias[] = new double[this.datosTrain.length];
            int i = 0;
            
            //se calculan las distacias
            for(Elemento[] filaTrain : this.datosTrain){
                distancias[i] = this.distancia(filaTest, filaTrain);
                i++;
            }
            
            
            //se calculan las k distancias
            
            
            ArrayList<Integer> mejoresVecinos = new ArrayList();
            
            for(i = 0; i < this.K; i++){
                double menorDistancia = Double.MAX_VALUE;
                for(int j = 0; j < distancias.length; j++){
                    if(distancias[j] < menorDistancia && !mejoresVecinos.contains(j)){
                        menorDistancia = distancias[j];
                        mejoresVecinos.add(i, j);
                    }
                }
                
            }
            
            //sumamos las incidencias
            HashMap<Elemento, Integer>  incidencias = new HashMap();
            for(i = 0; i < this.K; i++){
                int indiceFila = mejoresVecinos.get(i);
                int indiceUltimaColumna = this.datosTrain[0].length - 1;
                Elemento clase = this.datosTrain[indiceFila][indiceUltimaColumna];
                if(incidencias.containsKey(clase)){
                    int repeticiones = incidencias.get(clase);
                    incidencias.put(clase, repeticiones + 1);
                }else{
                    incidencias.put(clase, 1);
                }
            }
            //cogemos la clase con más incidencias
            Elemento mejorClase = null;
            int maxIncidencia = 0;
            for(Elemento clase : incidencias.keySet()){
                int incidencia = incidencias.get(clase);
                if(incidencia > maxIncidencia){
                    maxIncidencia = incidencia;
                    mejorClase = clase;
                }
            }
            //insertamos la clase 
            clases.add(mejorClase);
            
        }
        return clases;
    }
    
}
