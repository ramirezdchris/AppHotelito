package com.example.hotelitoreservacionfacilito.models;

import java.util.Date;

public class Promocion{

    private int idPromocion;

    private double descuento;
    private Date fechaIinicioProm;
    private Date fechaFinProm;

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public Date getFechaIinicioProm() {
        return fechaIinicioProm;
    }

    public void setFechaIinicioProm(Date fechaIinicioProm) {
        this.fechaIinicioProm = fechaIinicioProm;
    }

    public Date getFechaFinProm() {
        return fechaFinProm;
    }

    public void setFechaFinProm(Date fechaFinProm) {
        this.fechaFinProm = fechaFinProm;
    }

    @Override
    public String toString() {
        return "Promocion{" + "idPromocion=" + idPromocion + ", descuento=" + descuento + ", fechaIinicioProm=" + fechaIinicioProm + ", fechaFinProm=" + fechaFinProm + '}';
    }
    
}
