package ${package}.dao;

import java.util.List;

import ${package}.dao.model.${name};
import ${package}.dao.model.${name}Helpper;
import com.tower.service.dao.IHelpperDAO;

public interface I${name}HelpperDAO<T extends ${name}Helpper> extends IHelpperDAO<T> {

	public List<${name}> queryByHelpper(${name}Helpper helpper, String tabNameSuffix);

}
