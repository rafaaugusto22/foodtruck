package br.com.foodtruck.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.foodtruck.model.persitent.Empresa;
import br.com.foodtruck.util.exceptions.DataNotFoundException;
import br.com.foodtruck.util.validation.DataValidation;

/**
 * Classe responsável por disponibilizar todos os serviços relacionados à
 * empresa.
 * <p>
 * Define o contrato relacionado ao serviço, além de abstrair os serviços entre
 * os inúmeros canais necessários à sua disponibilização.
 * <p>
 * <b>ATENÇÃO:</b> EntityManager não é necessário nesta classe, porque foi
 * herdada da classe GenericDataAcessService
 * 
 * @author 
 * @see GenericDataAcessService
 * @since 1.0
 */
@Stateless
public class EmpresaMicroServices extends GenericDataAcessService<Empresa> {

	private static final Logger log = Logger.getLogger(EmpresaMicroServices.class.getName());

	@Inject
	DataValidation validador;

	public EmpresaMicroServices() {
		super(Empresa.class);
	}

	public void create(Empresa empresa) throws Exception {
		validador.validateModel(empresa);
		super.save(empresa);
	}

	public Empresa update(Empresa empresa) {
		validador.validateModel(empresa);
		return super.update(empresa);
	}

	public List<Empresa> listAllEmpresas() {
		return super.listAll(Empresa.FIND_ALL);
	}

	public Empresa findEmpresaByID(long id) {
		Empresa result = null;
		result = super.findById(id);
		if (result == null || result.equals("")) {
			throw new NoResultException("Não foram encontrados registos para o id " + id);
		}
		return result;
	}

	public List<Empresa> listByPagination(int inicio, int fim) {
		return super.listByPagination(Empresa.FIND_ALL, inicio, fim);
	}

	public List<Empresa> listByPaginationAndFilter(int inicio, int fim, String filtro, String coluna) {
		String consulta = "SELECT e FROM Empresa e WHERE e." + coluna + " LIKE :filtro";
		TypedQuery<Empresa> query = em.createQuery(consulta, Empresa.class);
		query.setMaxResults(fim);
		query.setFirstResult(inicio);
		query.setParameter("filtro", "%" + filtro + "%");
		return query.getResultList();
	}

	public Empresa findEmpresaByIDWithQuery(long id, String param) {
		String queryParametro = buildQueryParamentros(param);
		String consulta = "SELECT NEW br.com.foodtruck.model.persitent.Empresa(" + queryParametro + ")"
				+ " FROM Empresa e " + "WHERE e.nuempresa = :id";
		TypedQuery<Empresa> query = em.createQuery(consulta, Empresa.class);
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
					query.append("e." + params[i].trim() + ", ");
				} else {
					query.append("e." + params[i]);
				}
			}
		}
		return query.toString();
	}

	public Empresa findByCnpj(String cnpj) {
		String consulta = "SELECT e FROM Empresa e WHERE e.cnpj = :cnpj";
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