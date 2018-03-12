#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ${package}.model.persitent.Empregado;
import ${package}.model.persitent.Empresa;
import ${package}.model.persitent.Funcao;
import ${package}.model.persitent.Unidade;
import ${package}.util.exceptions.DataNotFoundException;

/**
 * Classe responsável por disponibilizar todos os serviços relacionados à
 * UNIDADES.
 * <p>
 * Define o contrato relacionado ao serviço, além de abstrair os serviços entre
 * os inúmeros canais necessários à sua disponibilização.
 * 
 * @author 
 * @see GenericDataAcessService
 * @since 1.0
 * @see
 */
@Stateless
public class EmpregadoMicroServices extends GenericDataAcessService<Empregado> {

	private static final Logger log = Logger.getLogger(EmpregadoMicroServices.class.getName());

	@Inject
	private Event<Empregado> empregadoObserver;

	public EmpregadoMicroServices() {
		super(Empregado.class);
	}

	public void create(Empregado empregado) {
		log.info("Registrando " + empregado.getNome());
		empregadoObserver.fire(empregado);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		super.save(empregado);
		for (int i = 0; i < 5; i++) {
			try {
				Thread.currentThread().setName("THREAD_" + "MetodoCreateEmpregadoMicroServices");
				Thread.sleep(1000);
				log.info(EmpregadoMicroServices.class.getName() + "Parada programa nº " + i + " para a Thread - "
						+ Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Empregado update(Empregado empregado) {
		empregado.setUnidade(em.find(Unidade.class, empregado.getUnidade().getNuunidade()));
		empregado.getUnidade().setEmpresa(em.find(Empresa.class, empregado.getUnidade().getEmpresa().getNuempresa()));
		empregado.setFuncao(em.find(Funcao.class, empregado.getFuncao().getNufuncao()));
		return em.merge(empregado);
	}

	public List<Empregado> listAllEmpregados() {
		return super.listAll(Empregado.FIND_ALL);
	}

	public Empregado findEmpregadoByID(Long id) {
		return super.findById(id);
	}

	@SuppressWarnings("unchecked")
	public List<Empregado> listByPagination(int inicio, int fim) {
		List<Empregado> empresas = new ArrayList<>();
		Query query = em.createNamedQuery("Empresa.findAll");
		query.setMaxResults(fim);
		query.setFirstResult(inicio);
		try {
			empresas = query.getResultList();
		} catch (NoResultException e) {
			throw new DataNotFoundException("Não foram encontrados registos");
		}
		return empresas;
	}

	public String buildQueryParamentros(String parametros) {
		StringBuilder query = new StringBuilder();
		if (parametros != null || parametros != "") {
			String[] params = parametros.split(",");
			for (int i = 0; i < params.length; i++) {
				if (i < params.length - 1) {
					query.append(params[i] + ",");
				} else {
					query.append(params[i]);
				}
			}
		}
		return query.toString();
	}

}