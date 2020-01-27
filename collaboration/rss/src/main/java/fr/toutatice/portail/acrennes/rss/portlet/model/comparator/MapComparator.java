package fr.toutatice.portail.acrennes.rss.portlet.model.comparator;

import java.util.Comparator;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MapComparator implements Comparator<String> {

	Map<String, String> base;

	public MapComparator(Map<String, String> base) {
		this.base = base;
	}

	@Override
	public int compare(String o1, String o2) {
		int result;
		if(o1 == null || base.get(o1) == null) {
			result = -1;
		} else if(o2 == null || base.get(o2) == null) {
			result = 1;
		} else {
			result = base.get(o1).compareToIgnoreCase(base.get(o2));
		}
		
		return result;
	}
}
