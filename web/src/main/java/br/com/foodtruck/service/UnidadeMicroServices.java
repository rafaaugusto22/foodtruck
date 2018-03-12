package br.com.foodtruck.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;

import br.com.foodtruck.model.persitent.Empresa;
import br.com.foodtruck.model.persitent.Unidade;
import br.com.foodtruck.util.exceptions.DataNotFoundException;
import br.com.foodtruck.util.validation.DataValidation;

/**
 * Classe responsável por disponibilizar todos os serviços relacionados à
 * UNIDADES.
 * <p>
 * Define o contrato relacionado ao serviço, além de abstrair os serviços entre
 * os inúmeros canais necessários à sua disponibilização.
 * 
 * @author 
 * @see Response.ResponseBuilder
 * @since 1.0
 */
@Stateless
public class UnidadeMicroServices extends GenericDataAcessService<Unidade> {

	private static final Logger log = Logger.getLogger(UnidadeMicroServices.class.getName());

	public UnidadeMicroServices() {
		super(Unidade.class);
	}

	@Inject
	DataValidation validador;

	@Inject
	EmpresaMicroServices empresaServices;

	public void create(Unidade unidade, long idEmpresa) {
		log.info("Registrando " + unidade.getSigla());
		Empresa empresa = empresaServices.findEmpresaByID(idEmpresa);
		unidade.setEmpresa(empresa);
		super.save(unidade);
	}

	public Unidade update(Unidade unidade) {
		return super.update(unidade);
	}

	public List<Unidade> listAllUnidades() {
		return super.listAll(Unidade.FIND_ALL);
	}

	public Unidade findUnidadeByID(Long id) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		return super.findById(id);
	}

	public List<Unidade> listByPagination(int inicio, int fim) {
		return super.listByPagination(Unidade.FIND_ALL, inicio, fim);
	}

	@SuppressWarnings("unchecked")
	public List<Empresa> listByPaginationAndFilter(int inicio, int fim, String filtro) {
		List<Empresa> empresas = new ArrayList<>();
		String consulta = "SELECT nu_empresa, co_cnpj_empresa, no_razao_social, nu_inscricao_estadual, nu_inscricao_municipal,nu_atividade_economica, no_fantasia, dt_pre_cadastro "
				+ "FROM AACTB001_EMPRESA " + "WHERE no_fantasia " + "LIKE :filtro";
		Query query = em.createNativeQuery(consulta);
		query.setMaxResults(fim);
		query.setFirstResult(inicio);
		query.setParameter("filtro", "%" + filtro + "%");
		try {
			empresas = query.getResultList();
		} catch (NoResultException e) {
			throw new DataNotFoundException("Não foram encontrados registos");
		}
		return empresas;
	}

	public Object findEmpresaByIDWithQuery(long id, String param) {
		String queryParametro = buildQueryParamentros(param);
		String consulta = "SELECT " + queryParametro + " FROM AACTB001_EMPRESA " + "WHERE nu_empresa = :id";
		Query query = em.createNativeQuery(consulta);
		query.setParameter("id", id);
		log.info(consulta);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new DataNotFoundException("Não foram encontrados registos para o id " + id);
		}
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

	public Empresa findByCnpj(String cnpj) {
		String consulta = "SELECT e FROM Empresa e WHERE e.coCnpj = :cnpj";
		TypedQuery<Empresa> query = em.createQuery(consulta, Empresa.class);
		query.setParameter("cnpj", cnpj);
		Empresa newEmpresa = null;
		try {
			newEmpresa = query.getSingleResult();
		} catch (NoResultException e) {
			throw new DataNotFoundException("Não foram encontrados registos para o cnpj " + cnpj);
		}
		return newEmpresa;
	}

}