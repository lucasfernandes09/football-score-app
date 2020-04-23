package com.app.app1.model.notificacao;

public class Notificacao {

    private String titulo;
    private String body;

    public Notificacao(String titulo, String body) {
        this.titulo = titulo;
        this.body = body;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
