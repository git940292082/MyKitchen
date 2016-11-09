package sc.my.kitchen.util;

/**
 * Url工厂
 * @author Sheng
 *
 */
public class UrlFactory {

	/**
	 * 得到菜谱的分类
	 * @param parentId
	 * @return
	 */
	public static String getSearchMenuSort(int parentId){
		String url = "http://apis.juhe.cn/cook/category?key=213d403a5f1fc4c6c3824575cf2512ea&parentid=" + parentId;
		return url;
	}
	
	/**
	 * 根据分类获得菜谱
	 * @param cid
	 * @param startIndex
	 * @param number
	 * @return
	 */
	public static String getSearchDish(int cid, String startIndex, String number){
		String url = "http://apis.juhe.cn/cook/index?key=213d403a5f1fc4c6c3824575cf2512ea&cid=" + cid + "&pn=" + startIndex + "&rn=" + number; 
		return url;
	}
	
	/**
	 * 根据菜名获得菜谱
	 * @param menu
	 * @param number
	 * @param startIndex
	 * @return
	 */
	public static String getSearchDishByMenu(String menu, String number, String startIndex){
		String url = "http://apis.juhe.cn/cook/query?key=213d403a5f1fc4c6c3824575cf2512ea&menu=" + menu + "&rn=" + number + "&pn=" + startIndex;
		return url;
	}
	
}
