#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.persitent;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;


/**
 * The persistent class for the unidade database table.
 * 
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name="Unidade.findAll", query="SELECT u FROM Unidade u"),
    @NamedQuery(name = "Unidade.findById", query = "SELECT u FROM Unidade u WHERE u.nuunidade = :id"),
    @NamedQuery(name = "Unidade.findBySigla", query = "SELECT u from Unidade u where u.sigla = :sigla")

})
public class Unidade implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String FIND_ALL = "Unidade.findAll";
    public static final String FIND_BY_ID = "Unidade.findById";
    public static final String FIND_BY_SIGLA = "Unidade.findBySigla";

    @Id
    @SequenceGenerator(name="UNIDADE_NUUNIDADE_GENERATOR", sequenceName="SEQ_NUUNIDADE")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="UNIDADE_NUUNIDADE_GENERATOR")
    private long nuunidade;

    private Boolean ativo;

    private String bairro;

    private String cep;

    private String complemento;

    private String localidade;

    private String logradouro;

    private String nome;

    private String sigla;

    private String uf;

    //bi-directional many-to-one association to Empregado
    @OneToMany(mappedBy="unidade")
    private List<Empregado> empregados;

    //bi-directional many-to-one association to Empresa
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="nuempresa")
    private Empresa empresa;

    public Unidade() {
    }

    public long getNuunidade() {
        return this.nuunidade;
    }

    public void setNuunidade(long nuunidade) {
        this.nuunidade = nuunidade;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getLocalidade() {
        return this.localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return this.sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getUf() {
        return this.uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public List<Empregado> getEmpregados() {
        return this.empregados;
    }

    public void setEmpregados(List<Empregado> empregados) {
        this.empregados = empregados;
    }

    public Empregado addEmpregado(Empregado empregado) {
        getEmpregados().add(empregado);
        empregado.setUnidade(this);

        return empregado;
    }

    public Empregado removeEmpregado(Empregado empregado) {
        getEmpregados().remove(empregado);
        empregado.setUnidade(null);

        return empregado;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

}