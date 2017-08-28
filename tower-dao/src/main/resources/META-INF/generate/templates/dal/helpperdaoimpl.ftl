package ${package}.dao.ibatis;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.SessionFactory;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.tower.service.dao.ibatis.IBatisDAOException;

import ${package}.dao.I${name}HelpperDAO;
import ${package}.dao.ibatis.mapper.${name}HelpperMapper;
import ${package}.dao.model.${name};
import ${package}.dao.model.${name}Helpper;
import com.tower.service.dao.ibatis.AbsHelpperIBatisDAOImpl;
import com.tower.service.dao.ibatis.IBatisDAOException;
import com.tower.service.dao.ibatis.SqlmapUtils;
import com.tower.service.exception.DataAccessException;


@Repository("${name}Helpper")
public class ${name}HelpperIbatisDAOImpl extends AbsHelpperIBatisDAOImpl<${name}Helpper> implements I${name}HelpperDAO<${name}Helpper> {

	@Resource(name = "${masterSessionFactory}")
	private SqlSessionFactory masterSessionFactory;
	
	@Resource(name = "${mapQuerySessionFactory}")
	private SqlSessionFactory mapQuerySessionFactory;
	
	@Override
	public Class<${name}HelpperMapper> getMapperClass() {
		
		return ${name}HelpperMapper.class;
	}
	
	@Override
	public String get$TowerTabName(String tabNameSuffix) {
	  suffixValidate(tabNameSuffix);
	  StringBuilder tableName = new StringBuilder("${tab.name}");
      if(tabNameSuffix!=null&&tabNameSuffix.trim().length()>0){
        tableName.append("_");
        tableName.append(tabNameSuffix.trim()); 
      }
      return tableName.toString();
    }
  
	@Override
	public SqlSessionFactory getMasterSessionFactory(){
		return masterSessionFactory;
	}
	
	@Override
	public SqlSessionFactory getMapQuerySessionFactory(){
		if (mapQuerySessionFactory == null||SqlmapUtils.hasTransaction()) {
 			return getMasterSessionFactory();
 		}
 		return mapQuerySessionFactory;
	}
	
	
	@Override
    public List<${name}> queryByHelpper(${name}Helpper helpper, String tabNameSuffix) {
        validate(helpper);

        helpper.setTowerTabName(this.get$TowerTabName(tabNameSuffix));

        SqlSessionFactory sessionFactory = this.getMapQuerySessionFactory();
	    SqlSession session = SqlmapUtils.openSession(sessionFactory);
        try {

            ${name}HelpperMapper mapper = session.getMapper(getMapperClass());

            return mapper.queryByHelpper(helpper);

        } catch (Exception t) {
            throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
        } finally {
            SqlmapUtils.release(session,sessionFactory);
        }
    }
	
}
