/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package individuos;

import datos.Elemento;

/**
 *
 * @author dani
 */
public class ValorGenetico implements Cloneable {
    Boolean valor;
    String significado;
    
    public ValorGenetico(Boolean valorP){
        this.valor = valorP;
    }
    public ValorGenetico(Boolean valorP, String signf){
        this.valor = valorP;
        this.significado = signf;
    }
    public Boolean getValor(){
        return this.valor;
    }
    public String getSignificado(){
        return this.significado;
    }
    
    @Override
    public Object clone(){
        ValorGenetico clon = new ValorGenetico(this.valor, this.significado);
        return clon;
    }
}
