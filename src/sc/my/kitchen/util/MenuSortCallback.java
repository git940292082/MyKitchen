package sc.my.kitchen.util;

import java.util.List;

import sc.my.kitchen.entity.MenuSort;

public interface MenuSortCallback {

	/**
	 * 当读取完菜谱分类后执行的回调方法
	 */
	void onLoadMenuSort(List<MenuSort> sorts);
	
}
