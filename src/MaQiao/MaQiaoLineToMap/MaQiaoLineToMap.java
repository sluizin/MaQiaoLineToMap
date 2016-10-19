/**
 * 
 */
package MaQiao.MaQiaoLineToMap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sunjian
 * @version 1.0
 * @since jdk1.7
 */
public final class MaQiaoLineToMap {
	public boolean allowRepeat = false;

	List<Node> masterList = new ArrayList<Node>();

	public boolean put(IMQLine newLine) {
		if (newLine == null) return false;
		Object[] signArray = newLine.signArray();
		Node f = null;
		List<Node> list = null;
		IMQLine t = null;
		for (int i = 0, len = signArray.length; i < len; i++) {
			if (i == 0) list = masterList;
			else list = f.childList;
			Object obj = signArray[i];
			if (i == len - 1) t = newLine;
			f = put(list, obj, t);
		}
		return true;
	}

	private Node put(List<Node> list, Object obj, IMQLine newLine) {
		Node e = new Node(obj, newLine);
		if (allowRepeat) {
			list.add(e);
			return e;
		} else {
			for (int i = 0, len = list.size(); i < len; i++) {
				Node f = list.get(i);
				if (f.key == obj || f.key.equals(obj)) { return f; }
			}
			list.add(e);
			return e;
		}
	}

	/**
	 * 把属性值加入到单元f的后面
	 * @param f Node
	 * @param key Object
	 * @param value int
	 * @return boolean
	 */
	public boolean putNode(Node f, Object key, IMQLine value) {
		if (key == null) return false;
		Node e = new Node(key, value);
		return putNode(f, e);
	}

	/**
	 * 把单元e加到单元f后面
	 * @param f Node
	 * @param e Node
	 * @return boolean
	 */
	public boolean putNode(Node f, Node e) {
		if (!checkNode(f, e)) return false;
		List<Node> childList = f.childList;
		Node child = null;
		for (int i = 0, len = childList.size(); i < len; i++) {
			child = childList.get(i);
			if (child.key == e.key || child.key.equals(e.key)) { return false; }
		}
		f.childList.add(e);
		return true;
	}

	/**
	 * 检索多单元的完整性
	 * @param nodeArray Node[]
	 * @return boolean
	 */
	private boolean checkNode(Node... nodeArray) {
		if (nodeArray == null || nodeArray.length == 0) return false;
		for (int i = 0, len = nodeArray.length; i < len; i++)
			if (!isNode(nodeArray[i])) return false;
		return true;
	}

	/**
	 * 检索单个单元的完整性
	 * @param e Node
	 * @return boolean
	 */
	private boolean isNode(Node e) {
		if (e == null || e.key == null || e.childList == null) return false;
		return true;
	}

	public boolean containsKey(Node e, Object obj) {
		return false;
	}






	/**
	 * 单元
	 * @author Sunjian
	 * @version 1.0
	 * @since jdk1.7
	 */
	public static final class Node {
		Object key = null;
		List<Node> childList = new ArrayList<Node>();
		IMQLine value = null;

		public Node(Object key, IMQLine value) {
			this.key = key;
			this.value = value;
		}
		
		public final Object getKey() {
			return key;
		}

		public final void setKey(Object key) {
			this.key = key;
		}

		public final List<Node> getChildList() {
			return childList;
		}

		public final void setChildList(List<Node> childList) {
			this.childList = childList;
		}
		/**
		 * 汇总接口的value()
		 * @return long
		 */
		public final long checkValueSort(){
			if (childList.size() == 0) return 0;
			return privateValueSort(0, this);
			
		}
		private final long privateValueSort(long sort, Node e){
			if (e == null) return sort;
			if (e.isNodeTerminal()) return sort + e.value.value();
			for (int i = 0, len = e.childList.size(); i < len; i++) {
				sort = privateValueSort(sort, e.childList.get(i));
			}
			return sort;
			
		}
		/**
		 * 判断节点有多少终端
		 * @return int
		 */
		public int checkTerminals() {
			if (childList.size() == 0) return 0;
			return privateNodeTerminal(0, this);
		}
		private int privateNodeTerminal(int maxTerminal, Node e) {
			if (e == null) return maxTerminal;
			if (e.isNodeTerminal()) return maxTerminal + 1;
			for (int i = 0, len = e.childList.size(); i < len; i++) {
				maxTerminal = privateNodeTerminal(maxTerminal, e.childList.get(i));
			}
			return maxTerminal;
		}
		/**
		 * 判断此节点是否是终端
		 * @return boolean
		 */
		public boolean isNodeTerminal() {
			if (childList.size() == 0) return true;
			return false;
		}

		/**
		 * 节点下面有多少的分支
		 * @return int
		 */
		public int checkNodeMaxBranch() {
			return privateNodeBranch(0, this);
		}

		private int privateNodeBranch(int maxlevel, Node e) {
			if (e == null) return maxlevel;
			int max = maxlevel;
			if (e.childList.size() > 0) max = maxlevel + 1;else return maxlevel;
			for (int childMax, i = 0, len = e.childList.size(); i < len; i++) {
				childMax = privateNodeBranch(max, e.childList.get(i));
				if (childMax > max) max = childMax;
			}
			return max;
		}
		/**
		 * 得到有接口的数量
		 * @return int
		 */
		public int checkCount(){
			return privateNodeCount(0, this);
		}
		private int privateNodeCount(int maxlevel, Node e) {
			if (e == null) return maxlevel;
			int max = maxlevel;
			if (e.value!=null) max = maxlevel + 1;
			for (int childMax, i = 0, len = e.childList.size(); i < len; i++) {
				childMax = privateNodeCount(max, e.childList.get(i));
				if (childMax > max) max = childMax;
			}
			return max;
		}
		public String checkToTableTr(){			
			StringBuilder sb=new StringBuilder();
			privateNodeToTableTr(sb,this);
			return sb.toString();
		}
		private void privateNodeToTableTr(StringBuilder sb,Node e){
			if (e == null) return;
			if (e.childList.size()==0) {
				sb.append("<td>");
				sb.append("XXX"+e.key.toString()+"XXX");
				sb.append("</td>");
				sb.append("<td>");
				sb.append(e.value.value());
				sb.append("</td>");
				sb.append("</tr>");
			}
			for (int i = 0, len = e.childList.size(); i < len; i++) {
				Node f=e.childList.get(i);
				if(i==0){
					sb.append("<td rowspan='"+ e.checkTerminals() +"'>");
					sb.append("XXX"+e.key.toString()+"XXX["+e.checkValueSort()+"]");
					sb.append("</td>");	
					privateNodeToTableTr(sb, f);				
				}else{
					sb.append("<tr>");
					privateNodeToTableTr(sb, f);
				}
			}			
		}
	}
	public final List<Node> getMasterList() {
		return masterList;
	}

	public final void setMasterList(List<Node> masterList) {
		this.masterList = masterList;
	}
	
}
