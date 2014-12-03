/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package individuos;

import datos.Elemento;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author dani
 */
public class AtributoGenetico implements Cloneable {
    //un atributo genetico es una lista de elementos binarios
    //
    private ArrayList<ValorGenetico> valoresGeneticos;
    
    public AtributoGenetico(){
        this.valoresGeneticos = new ArrayList<>();
    }
    
    public void inicializaAleatorio(int nValores){
        for(int i = 0 ; i < nValores; i++){
            if(Math.random() >= 0.500000){
                ValorGenetico valor = new ValorGenetico(true);
                this.valoresGeneticos.add(valor);
            }else{
                ValorGenetico valor = new ValorGenetico(false);
                this.valoresGeneticos.add(valor);
            }
        }
    }
    
    public void inicializaSignificado(HashMap<String, Integer> hasmSignificado){
        Set<String> significados = hasmSignificado.keySet();
        for(String significado : significados){
            Integer count = hasmSignificado.get(significado);
            if(count >= 1){
                ValorGenetico valor = new ValorGenetico(true, significado);
                this.valoresGeneticos.add(valor);
            }else{
                ValorGenetico valor = new ValorGenetico(false, significado);
                this.valoresGeneticos.add(valor);
            }
            
        }
    }
    public void inicializaSignificadoClasificacion(HashMap<String, Integer> hasmSignificado, Elemento elem){
        Set<String> significados = hasmSignificado.keySet();
        for(String significado : significados){
            if(significado.equals(elem.getValorNominal())){
                ValorGenetico valor = new ValorGenetico(true, significado);
                this.valoresGeneticos.add(valor);
            }else{
                ValorGenetico valor = new ValorGenetico(false, significado);
                this.valoresGeneticos.add(valor);
            }
            
        }
    }
    
    public void inicializaSignifAleatorio(HashMap<String, Integer> hasmSignificado){
        Set<String> significados = hasmSignificado.keySet();
        for(String significado : significados){
            if(GestorRand.getDouble() >= 0.5){
                ValorGenetico valor = new ValorGenetico(true, significado);
                this.valoresGeneticos.add(valor);
            }else{
                ValorGenetico valor = new ValorGenetico(false, significado);
                this.valoresGeneticos.add(valor);
            }
        }
    }
     
    public Boolean cumpleCondicion(AtributoGenetico debeCumplir){
        for(int i = 0; i < this.valoresGeneticos.size(); i++){
            Boolean condicionTest  = this.valoresGeneticos.get(i).getValor();
            Boolean condicionToTest = debeCumplir.valoresGeneticos.get(i).getValor();
            if(condicionTest == false && condicionToTest == true){
                return false;
            }
        }
        return true;
    }
    
    public void mutaValorGenetico(double probMutacion){
        int nValores = this.valoresGeneticos.size();
        int indexVal= GestorRand.getInt(nValores);
        ValorGenetico val = this.valoresGeneticos.get(indexVal);
        if(GestorRand.getDouble() <= probMutacion){
            if(val.valor == true){
                val.valor = false;
            }else{
                val.valor = true;
            }
        }
        
    }
    @Override
    public Object clone(){
        AtributoGenetico clon = new AtributoGenetico();
        for(ValorGenetico v : this.valoresGeneticos){
            clon.valoresGeneticos.add((ValorGenetico)v.clone());
        }
        return clon;
    }
}
