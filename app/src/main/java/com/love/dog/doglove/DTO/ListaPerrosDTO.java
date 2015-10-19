package com.love.dog.doglove.DTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hugo on 10/7/2015.
 */
public class ListaPerrosDTO implements Serializable {
    List<MascotaDTO> perros;

    public ListaPerrosDTO(List<MascotaDTO> perros) {

        this.perros = perros;
    }

    public List<MascotaDTO> getPerros() {
        return perros;
    }

    public void setPerros(List<MascotaDTO> perros) {
        this.perros = perros;
    }


}
