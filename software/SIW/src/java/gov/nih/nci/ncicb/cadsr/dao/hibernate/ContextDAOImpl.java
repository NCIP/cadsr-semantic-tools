/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.dao.hibernate;

import gov.nih.nci.ncicb.cadsr.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.domain.Context;

import java.sql.SQLException;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ContextDAOImpl extends HibernateDaoSupport implements ContextDAO {

  public List<Context> findAll(){
    return (List<Context>)getHibernateTemplate().findByNamedQuery("context.findAll");
  }

  public Context findByName(String name) {
    try {
      return (Context)getHibernateTemplate().findByNamedQuery("context.findByName", name).get(0);
    } catch (Throwable t){
      return null;
    } // end of try-catch
  }

  public List find(final Context context) {
    HibernateCallback callback = new HibernateCallback(){
	
	public Object doInHibernate(Session session) throws HibernateException, SQLException {
	  
	  Criteria criteria = session.createCriteria(Context.class);
	  
	  if(context.getName() != null) {
	    criteria.add(Expression.like("name", context.getName()));
	  }
	  
	  return criteria.list();
	}
      };
    
    return (List)getHibernateTemplate().execute(callback);
    
  }


}