package fr.toutatice.portail.acrennes.rss.portlet.model.comparator;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import fr.toutatice.portail.acrennes.rss.portlet.model.TitleItem;

@Component
public class TitleItemComparator implements Comparator<TitleItem> {

	public TitleItemComparator() {
		super();
	}

	@Override
	public int compare(TitleItem o1, TitleItem o2) {
		int result;
		if(o1 == null || o1.getTitle() == null) {
			result = -1;
		} else if(o2 == null || o2.getTitle() == null) {
			result = 1;
		} else {
			result = o1.getTitle().compareToIgnoreCase(o2.getTitle());
		}
		
		return result;
	}

}
