package sc.my.kitchen.util;

import java.util.List;

import sc.my.kitchen.entity.MenuSort;

public interface MenuSortCallback {

	/**
	 * ����ȡ����׷����ִ�еĻص�����
	 */
	void onLoadMenuSort(List<MenuSort> sorts);
	
}
