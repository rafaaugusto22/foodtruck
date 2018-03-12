package br.com.foodtruck.model.persitent;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;


/**
 * The persistent class for the dependente database table.
 * 
 */
@Entity
@Table(name = "Dependente")
@XmlRootElement
@NamedQuery(name="Dependente.findAll", query="SELECT d FROM Dependente d")
public class Dependente implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="DEPENDENTE_NUDEPENDENTE_GENERATOR", sequenceName="SEQ_NUDEPENDENTE")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DEPENDENTE_NUDEPENDENTE_GENERATOR")
    private long nudependente;

    private String cpf;

    @Temporal(TemporalType.DATE)
    private Date dtnasc;

    private String nome;

    private String sexo;

    //bi-directional many-to-one association to Empregado
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="nuempregado")
    private Empregado empregado;

    public Dependente() {
    }

    public long getNudependente() {
        return this.nudependente;
    }

    public void setNudependente(long nudependente) {
        this.nudependente = nudependente;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDtnasc() {
        return this.dtnasc;
    }

    public void setDtnasc(Date dtnasc) {
        this.dtnasc = dtnasc;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Empregado getEmpregado() {
        return this.empregado;
    }

    public void setEmpregado(Empregado empregado) {
        this.empregado = empregado;
    }

}