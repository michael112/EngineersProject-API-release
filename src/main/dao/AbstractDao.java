// Based on: http://websystique.com/spring-security/spring-security-4-hibernate-annotation-example/

package main.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;

import main.model.abstracts.AbstractModel;

public abstract class AbstractDao<PK extends Serializable, T extends AbstractModel> {
	
	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public AbstractDao(){
		this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T findByID(PK id) {
		return (T) getSession().get(persistentClass, id);
	}

	@SuppressWarnings("unchecked")
	public Set<T> findByQuery(String queryStr) {
		Query query = getSession().createQuery(queryStr);
		return new HashSet<> ( (List<T>) query.list() );
	}

	@SuppressWarnings("unchecked")
	public Set<T> findAll() {
		Criteria criteria = createEntityCriteria();
		return new HashSet<>( (List<T>) criteria.list() );
	}

	public void save(T entity) {
		getSession().save(entity);
	}
	
	public void update(T entity) {
		getSession().update(entity);
	}

	public void delete(T entity) {
		getSession().delete(entity);
	}

	protected Criteria createEntityCriteria(){
		return getSession().createCriteria(persistentClass);
	}

}
