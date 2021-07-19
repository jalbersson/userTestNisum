package com.nisum.usercreation.apiuser.model;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
    private String mensaje;

    public ErrorResponse() {
    }

    public ErrorResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
