/**
 * 
 */
package MaQiao.MaQiaoLineToMap;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public interface IMQLine {
	/**
	 * 标志数组，用于进行图型路径
	 * @return Object[]
	 */
	Object[] signArray();
	/**
	 * 对象值，初步用于汇总的总数
	 * @return long
	 */
	long value();
}
