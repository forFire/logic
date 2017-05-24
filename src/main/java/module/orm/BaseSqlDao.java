package module.orm;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 通用Dao
 * 
 * @author zxp
 */
public class BaseSqlDao<T, PK extends Serializable> {

	protected Class<T> entityClass;

	@Resource
	private SessionFactory sessionFactorySpot;

	// -------------------------------------------------------------

	public void setSessionFactorySpot(SessionFactory sessionFactorySpot) {
		this.sessionFactorySpot = sessionFactorySpot;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactorySpot;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseSqlDao() {
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	@Transactional
	public void saveOrUpdate(final T entity) {
		getSession().saveOrUpdate(entity);
	}

	@Transactional
	public Long save(final T entity) {
		return (Long) getSession().save(entity);
	}

	@Transactional
	public void update(final T entity) {
		getSession().update(entity);
	}

	@Transactional
	public void delete(final T entity) {
		getSession().delete(entity);
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

	private Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	/**
	 * 取得对象的主键名.
	 */
	private String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	@SuppressWarnings("unchecked")
	public T getBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	public List<T> findBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	public List<T> findByIds(List<PK> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllOrder(String orderBy, boolean isAsc) {
		Criteria criteria = createCriteria();
		if (isAsc) {
			criteria.addOrder(Order.asc(orderBy));
		} else {
			criteria.addOrder(Order.desc(orderBy));
		}
		return criteria.list();
	}

	// ----------------------------------------------------------------

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Criteria criteria = createCriteria();
		return criteria.list();
	}

	public int countAll() {
		Criteria criteria = createCriteria();
		int totalCount = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		return totalCount;
	}

	// --------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	protected List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	protected Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public <X> X getHql(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	// --------------------------------------------------------------------

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 */
	protected Query createQuery(final String queryString,
			final Object... values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.Map
	 */
	protected Query createQuery(final String queryString,
			final Map<String, Object> values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			// 格式 from Account where name=:name
			query.setProperties(values);
		}
		return query;
	}

	@SuppressWarnings("unchecked")
	public List<T> query(final String sql, final List<Object> obj) {
		SQLQuery query = getSession().createSQLQuery(sql);
		for (int i = 0; i < obj.size(); i++) {
			query.setParameter(i, obj.get(i));
		}
		List<T> list = query.addEntity(this.entityClass).list();
		return list;
	}

	@Transactional
	public void save(final String sql, final List<Object> obj) {
		SQLQuery query = getSession().createSQLQuery(sql);
		for (int i = 0; i < obj.size(); i++) {
			query.setParameter(i, obj.get(i));
		}
		query.executeUpdate();
	}

	/**
	 * 查询所有 无排序 由criteria实现
	 */
	public Page<T> findAll(final Page<T> page) {
		return findPage(page);
	}

	/**
	 * query 实现
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String hql,
			final Object... values) {
		Query query = createQuery(hql, values);
		if (page.isAutoCount()) {
			page.setTotalCount(totalCount(hql, values));
		}
		setByPage(query, page);
		List<T> result = query.list();
		page.setResult(result);
		return page;
	}

	/**
	 * criteria 实现
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
		Criteria criteria = createCriteria(criterions);
		if (page.isAutoCount()) {
			page.setTotalCount(totalCount(criteria));
		}
		setByPage(criteria, page);
		List<T> result = criteria.list();
		page.setResult(result);
		return page;
	}

	// --------------------------------------------------------------------

	/**
	 * 设置条件by page query 实现
	 */
	protected Query setByPage(final Query query, final Page<T> page) {
		if (page.getUse2Page().equals("Y")) {
			query.setFirstResult(page.getFirst());
			query.setMaxResults(page.getPageSize());
		}
		return query;
	}

	/**
	 * 设置条件by page criteria 实现
	 */
	protected Criteria setByPage(final Criteria criteria, final Page<T> page) {
		if (page.getUse2Page().equals("Y")) {
			criteria.setFirstResult(page.getFirst());
			criteria.setMaxResults(page.getPageSize());
		}

		if (page.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');
			Assert.isTrue(orderByArray.length == orderArray.length, "分页多重个数不相等");
			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					criteria.addOrder(Order.asc(orderByArray[i]));
				} else {
					criteria.addOrder(Order.desc(orderByArray[i]));
				}
			}
		} else {
			criteria.addOrder(Order.desc(getIdName()));
		}

		return criteria;
	}

	/**
	 * 获取总数 query 实现
	 */
	protected long totalCount(final String hql, final Object... values) {
		String countHql = HqlUtils.getCountHql(hql);
		return getHql(countHql, values);
	}

	/**
	 * 获取总数 criteria 实现
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected long totalCount(final Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();
		List<CriteriaImpl.OrderEntry> orderEntries = null;
		orderEntries = (List) ReflectionUtils.getFieldValue(impl,
				"orderEntries");
		ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		// 执行Count查询
		long totalCount = (Long) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			criteria.setResultTransformer(transformer);
		}
		ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		return totalCount;
	}

}
