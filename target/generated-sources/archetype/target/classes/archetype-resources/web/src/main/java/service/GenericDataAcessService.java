#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ${package}.util.interceptors.jpa.LazyResolveInterceptor;

/**
 * Classe responsável por prover o <b>EntityManager</b> para as classes por
 * herança e centraliza os métodos de busca mais <b>COMUNS</b> aos dados.
 * <p>
 * 
 * @author 
 * @since 1.0
 */

@Interceptors(LazyResolveInterceptor.class)
@Stateless
public class GenericDataAcessService<T> {

	@PersistenceContext(name = "")
	protected EntityManager em;

	private Class<T> entityClass;

	public GenericDataAcessService() {
	}

	public GenericDataAcessService(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * Método resposnável por salvar um objeto
	 * 
	 * @param entity
	 * @author 
	 * @since 1.0
	 */
	public void save(T entity) {
		em.persist(entity);
	}

	/**
	 * Método responsável por deletar um objeto
	 * 
	 * @param
	 * @author 
	 * @since 1.0
	 */
	protected void delete(Object id, Class<T> classe) {
		T entityToBeRemoved = em.getReference(classe, id);
		em.remove(entityToBeRemoved);
	}

	/**
	 * Método responsável por atualizar um objeto
	 * 
	 * @param
	 * @author 
	 * @since 1.0
	 */
	public T update(T entity) {
		return em.merge(entity);
	}

	/**
	 * Método responsável por buscar um objeto a partir do ID
	 * 
	 * @param
	 * @return Object
	 * @author 
	 * @since 1.0
	 */
	public T findById(long id) throws NoResultException {
		T result = null;
		result = em.find(entityClass, id);
		em.clear();
		return result;
	}

	/**
	 * Método responsável por listar todos os objetos a partir de uma
	 * <b>namedQuery</b>
	 * 
	 * @param
	 * @return List de Objetos
	 * @author 
	 * @since 1.0
	 */
	protected List<T> listAll(String namedQuery) {
		List<T> lista = new ArrayList<>();
		TypedQuery<T> query = em.createNamedQuery(namedQuery, entityClass);
		lista = query.getResultList();
		em.clear();
		return lista;
	}

	/**
	 * Método responsável por buscar um objeto a partir partir de uma
	 * <b>namedQuery</b>
	 * 
	 * @param
	 * @return List de Objetos
	 * @author 
	 * @since 1.0
	 */
	protected T findOneResultByNamedQuerie(String namedQuery, Map<String, Object> parameters) {
		TypedQuery<T> query = em.createNamedQuery(namedQuery, entityClass);
		if (parameters != null && !parameters.isEmpty()) {
			populateQueryParameters(query, parameters);
		}
		T result = query.getSingleResult();
		em.clear();
		return result;
	}

	/**
	 * Método responsável por buscar uma lista de objetos a partir partir de uma
	 * <b>namedQuery</b> com <b>paginação</b>
	 * 
	 * @param
	 * @return List de Objetos
	 * @author 
	 * @since 1.0
	 */
	public List<T> listByPagination(String namedQuery, int inicio, int fim) {
		TypedQuery<T> query = em.createNamedQuery(namedQuery, entityClass);
		query.setMaxResults(fim);
		query.setFirstResult(inicio);
		List<T> lista = query.getResultList();
		em.clear();
		return lista;
	}

	/**
	 * Método responsável por popular a Query com os parâmetros
	 * 
	 * @param
	 * @author 
	 * @since 1.0
	 */
	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

}