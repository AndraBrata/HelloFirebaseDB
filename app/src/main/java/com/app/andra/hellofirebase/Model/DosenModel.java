/*
 * Copyright (c) 2018. This Code is created and writed by Komang Candra Brata (k.candra.brata@ub.ac.id).
 * Inform the writer if you willing to edit or modify it for commercial purpose.
 */

package com.app.andra.hellofirebase.Model;

/**
 * Created by Komang Candra Brata on 4/15/2018.
 */

public class DosenModel {
    private String nip;
    private String name;


    public DosenModel() {

    }

    public DosenModel(String nip, String name) {
        this.nip = nip;
        this.name = name;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}