package br.com.foodtruck.model.persitent;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the empresa database table.
 * 
 */
@Entity
@Table(name = "Empresa")
@NamedQueries({ @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e"),
        @NamedQuery(name = "Empresa.findById", query = "SELECT e FROM Empresa e WHERE e.nuempresa = :id"),
        @NamedQuery(name = "Empresa.findByCnpj", query = "SELECT e from Empresa e where e.cnpj = :cnpj")

})
@XmlRootElement(name = "empresa")
@XmlAccessorType(XmlAccessType.FIELD)
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Empresa.findAll";
    public static final String FIND_BY_ID = "Empresa.findById";
    public static final String FIND_BY_SIGLA = "Empresa.findByCnpj";

    // o allocationSize=1 foi usado para corrigir uma falha na implementação do
    // JPA, onde a cheave é gerada com valor negativo pela sequence.
    @Id
    @SequenceGenerator(name = "EMPRESA_NUEMPRESA_GENERATOR", sequenceName = "SEQ_NUEMPRESA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPRESA_NUEMPRESA_GENERATOR")
    @XmlElement(required = true)
    @Column(name = "nuempresa")
    private long nuempresa;

    @Column(name = "ativo")
    @XmlElement(required = true)
    private Boolean ativo;

    //@CNPJ
    @Column(name = "cnpj")
    //@Cnpj(message = "{REGRA_CNPJ}")
    @XmlElement(required = true)
    private String cnpj;

    @Column(name = "nomefantasia")
    @Size(min = 0, max = 256, message = "{REGRA_NOFANTASIA}")
    @XmlElement(required = true)
    private String nomefantasia;

    @Column(name = "razaosocial")
    @Size(min = 0, max = 256, message = "{REGRA_NORAZAOSOCIAL}")
    @XmlElement(required = true)
    private String razaosocial;

    @XmlElement(required = true)
    // bi-directional many-to-one association to Unidade
    @OneToMany(mappedBy = "empresa")
    private List<Unidade> unidades;

    public Empresa() {
    }

    public Empresa(long nuempresa) {
        this.nuempresa = nuempresa;
    }

    public Empresa(long nuempresa, Boolean ativo) {
        this.nuempresa = nuempresa;
        this.ativo = ativo;
    }

    public Empresa(long nuempresa, Boolean ativo, String nomefantasia) {
        this.nuempresa = nuempresa;
        this.ativo = ativo;
        this.nomefantasia = nomefantasia;
    }

    public Empresa(long nuempresa, Boolean ativo, String nomefantasia, String razaosocial) {
        this.nuempresa = nuempresa;
        this.ativo = ativo;
        this.nomefantasia = nomefantasia;
        this.razaosocial = razaosocial;
    }
    
    public Empresa(String cnpj, long nuempresa, String razaosocial) {
        
        this.cnpj = cnpj;
        this.nuempresa = nuempresa;
        this.razaosocial = razaosocial;
    }

    public long getNuempresa() {
        return this.nuempresa;
    }

    public void setNuempresa(long nuempresa) {
        this.nuempresa = nuempresa;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomefantasia() {
        return this.nomefantasia;
    }

    public void setNomefantasia(String nomefantasia) {
        this.nomefantasia = nomefantasia;
    }

    public String getRazaosocial() {
        return this.razaosocial;
    }

    public void setRazaosocial(String razaosocial) {
        this.razaosocial = razaosocial;
    }

    public List<Unidade> getUnidades() {
        return this.unidades;
    }

    public void setUnidades(List<Unidade> unidades) {
        this.unidades = unidades;
    }

    public Unidade addUnidade(Unidade unidade) {
        getUnidades().add(unidade);
        unidade.setEmpresa(this);

        return unidade;
    }

    public Unidade removeUnidade(Unidade unidade) {
        getUnidades().remove(unidade);
        unidade.setEmpresa(null);

        return unidade;
    }

}