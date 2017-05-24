package module.orm;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class Dao<T, PK extends Serializable> {

	protected Class<T> entityClass;

//	@Resource
	protected SessionFactory sessionFactoryMaster;

//	@Resource
	protected SessionFactory sessionFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Dao() {
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	// -------------------------------------------------------------

	@Transactional
	public void saveOrUpdate(final T entity) {
		getSessionMaster().saveOrUpdate(entity);
	}

	@Transactional
	public Integer save(final T entity) {
		return (Integer) getSessionMaster().save(entity);
	}

	@Transactional
	public PK savePK(final T entity) {
		return (PK) getSessionMaster().save(entity);
	}
	
	@Transactional
	public void update(final T entity) {
		getSessionMaster().update(entity);
	}

	@Transactional
	public void delete(final T entity) {
		getSessionMaster().delete(entity);
	}

	@Transactional
	public void delete(final PK id) {
		delete(get(id));
	}

	@SuppressWarnings("unchecked")
	public T get(final PK id) {
		return (T) getSession().get(entityClass, id);// load
	}

	// -------------------------------------------------------------

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Session getSessionMaster() {
		return sessionFactoryMaster.getCurrentSession();
	}

	/**
	 * 取得对象的主键名.
	 */
	protected String getIdName() {
		ClassMetadata meta = sessionFactoryMaster.getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

    protected SessionFactory getSessionFactoryMaster () {

        return sessionFactoryMaster;
    }

    protected void setSessionFactoryMaster (SessionFactory sessionFactoryMaster) {

        this.sessionFactoryMaster = sessionFactoryMaster;
    }

    protected SessionFactory getSessionFactory () {

        return sessionFactory;
    }

    protected void setSessionFactory (SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }
}