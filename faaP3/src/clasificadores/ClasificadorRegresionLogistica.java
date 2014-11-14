/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import datos.Elemento;
import datos.ElementoContinuo;
import datos.ElementoNominal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 *
 */
public class ClasificadorRegresionLogistica extends Clasificador {
    ArrayList<Double> planoClasificacion;
    HashMap<Elemento, Double> clasesToContinuo;
    HashMap<Double, Elemento> continuoToClases;
    Double eta = 0.005;
    int epocas = 1000;
    
    @Override
    public void entrenamiento(Datos datosTrain) {
       
        Elemento[][] datos = datosTrain.getDatos();
        int nColumnas = datos[0].length;
        //generarPlanoAleatorio entre [-0.5, 0.5]
        this.planoClasificacion = this.generarPlanoAleatorio(nColumnas);
        
        //recorremos las filas asignando un valor contiguo a las clases
        double nClasesEncontradas = 1.0;
        this.clasesToContinuo = new HashMap<>();
        this.continuoToClases = new HashMap<>();
        for(int i = 0; i < datos.length; i++){
            Elemento[] fila = datos[i];
            Elemento clase = fila[fila.length -1];
            if(this.clasesToContinuo.containsKey(clase)){
                clase.setValorContinuo(this.clasesToContinuo.get(clase));
            }else{
                this.clasesToContinuo.put(clase, nClasesEncontradas);
                this.continuoToClases.put(nClasesEncontradas, clase);
                clase.setValorContinuo(this.clasesToContinuo.get(clase));
                nClasesEncontradas = nClasesEncontradas - 1;
            }
        }
        
        //recorremos todas las filas ajustando el plano
        for(int i = 0; i<epocas; i++){
            for(Elemento[] fila : datos){
                ArrayList<Double> punto = this.generaPunto(fila);
                double sigmoidal = this.sigmoidalLogistica(this.planoClasificacion, punto);
                Elemento clase = fila[fila.length -1];
                this.actualizaPlanoClasificacion(sigmoidal, clase, punto);
            
            }
        }
        
        
        
        
    }

    @Override
    public ArrayList<Elemento> clasifica(Datos datosTest) {
        ArrayList<Elemento> clasificado = new ArrayList<>();
        
        for(Elemento[] fila : datosTest.getDatos()){
            ArrayList<Double> punto = this.generaPunto(fila);
            double sigmoidal = sigmoidalLogistica(this.planoClasificacion, punto);
            Elemento clase = null;
            if(sigmoidal > 0.5){
                clase = this.continuoToClases.get(1.0);
            }else{
                clase = this.continuoToClases.get(0.0);
            }
            
            clasificado.add(clase);
            
        }
        return clasificado;
    }
    
    private ArrayList<Double> generarPlanoAleatorio(int tam){
        ArrayList<Double> plano = new ArrayList<>();
        
        plano.add(-1.0);
        for(int i = 0; i<tam-1; i++){
            double elementoPlano = Math.random();
            elementoPlano -= 0.5;
            plano.add(elementoPlano);
        }
        return plano;
    }
    
    private ArrayList<Double> generaPunto(Elemento [] fila){
        ArrayList<Double> punto = new ArrayList();
        punto.add(1.0);
        for(int i = 0; i < (fila.length - 1); i++){
            punto.add(fila[i].getValorContinuo());
        }
        
        return punto;
    }
    
    private double sigmoidalLogistica(ArrayList<Double> plano, ArrayList<Double> punto){
        double wPorXi = 0;
        double sigmoidal = 0;
        
        for(int i = 0; i < plano.size(); i++){
            wPorXi += plano.get(i)*punto.get(i);
        }
        
        sigmoidal = 1/(1+Math.pow(Math.E, -wPorXi));
        
        return sigmoidal;
    }
    
    private void actualizaPlanoClasificacion(double sigmoidal, Elemento claseDeI, ArrayList<Double> punto){
        
        double restaSigmoidalClase = sigmoidal - claseDeI.getValorContinuo();

        

        double conCorreccion = restaSigmoidalClase * this.eta;
        
        ArrayList<Double> vectorARestarAlPlano = this.multiplicaVectorPorConstante(punto, conCorreccion);
        this.planoClasificacion = this.restaVectores(this.planoClasificacion, vectorARestarAlPlano);

    }
    
    private ArrayList<Double> restaVectores(ArrayList<Double> v1, ArrayList<Double> v2){
        ArrayList<Double> vectorResultado = new ArrayList<>();
        
        for(int i = 0; i<v1.size(); i++){
            vectorResultado.add(v1.get(i) - v2.get(i));
        }
        
        return vectorResultado;
    }
    private ArrayList<Double> multiplicaVectorPorConstante(ArrayList<Double> v1, double cte){
        ArrayList<Double> vectorResultado = new ArrayList<>();
        
        for(int i = 0; i<v1.size(); i++){
            vectorResultado.add(v1.get(i)*cte);
        }
        
        return vectorResultado;
    }
}
