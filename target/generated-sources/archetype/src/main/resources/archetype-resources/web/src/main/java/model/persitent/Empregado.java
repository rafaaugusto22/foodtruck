#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.persitent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the empregado database table.
 * 
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name="Empregado.findAll", query="SELECT e FROM Empregado e"),
    @NamedQuery(name = "Empregado.findById", query = "SELECT e FROM Empregado e WHERE e.nuempregado = :id")
})
public class Empregado implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Empregado.findAll";
    public static final String FIND_BY_ID = "Empregado.findById";
    public static final String FIND_BY_MATRICULA = "Empregado.findByMatricula";
    
    @Id
    @SequenceGenerator(name="EMPREGADO_NUEMPREGADO_GENERATOR", sequenceName="SEQ_NUEMPREGADO", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMPREGADO_NUEMPREGADO_GENERATOR")
    private long nuempregado;

    private Boolean ativo;

    private String bairro;

    private String cep;

    private String complemento;

    private String cpf;

    @Temporal(TemporalType.DATE)
    private Date dtnasc;

    private String estadocivil;

    private String localidade;

    private String logradouro;

    private String nome;

    private String sexo;

    private String uf;
    

    //bi-directional many-to-one association to Dependente
    @OneToMany(mappedBy="empregado")
    private List<Dependente> dependentes;

    //bi-directional many-to-one association to Funcao
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="nufuncao")
    private Funcao funcao;

    //bi-directional many-to-one association to Unidade
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="nuunidade")
    private Unidade unidade;

    //bi-directional many-to-many association to Habilidade
    @ManyToMany(mappedBy="empregados")
    private List<Habilidade> habilidades;

    public Empregado() {
    }

    public long getNuempregado() {
        return this.nuempregado;
    }

    public void setNuempregado(long nuempregado) {
        this.nuempregado = nuempregado;
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

    public String getEstadocivil() {
        return this.estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
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

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getUf() {
        return this.uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
    
    public List<Dependente> getDependentes() {
        return this.dependentes;
    }

    public void setDependentes(List<Dependente> dependentes) {
        this.dependentes = dependentes;
    }

    public Dependente addDependente(Dependente dependente) {
        getDependentes().add(dependente);
        dependente.setEmpregado(this);

        return dependente;
    }

    public Dependente removeDependente(Dependente dependente) {
        getDependentes().remove(dependente);
        dependente.setEmpregado(null);

        return dependente;
    }

    public Funcao getFuncao() {
        return this.funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public Unidade getUnidade() {
        return this.unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public List<Habilidade> getHabilidades() {
        return this.habilidades;
    }

    public void setHabilidades(List<Habilidade> habilidades) {
        this.habilidades = habilidades;
    }

}