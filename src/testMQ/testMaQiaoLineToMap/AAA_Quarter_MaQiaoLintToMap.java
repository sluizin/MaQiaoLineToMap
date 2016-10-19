package testMQ.testMaQiaoLineToMap;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import MaQiao.MaQiaoLineToMap.IMQLine;
import MaQiao.MaQiaoLineToMap.MaQiaoLineToMap;
import MaQiao.MaQiaoLineToMap.MaQiaoLineToMap.Node;

public class AAA_Quarter_MaQiaoLintToMap {

	@Test
	public void test() {
		try {
			MaQiaoLineToMap maQiaoLineToMap = new MaQiaoLineToMap();
			String a1 = "", a2 = "", a3 = "";
			int a4 = 400;
			
			
			line e = new line(a4, a1, a2, a3);
			maQiaoLineToMap.put(e);
			
			StringBuilder sb = new StringBuilder();
			sb.append("<table border='1'>");
			;
			@SuppressWarnings("unchecked")
			List<Node> list = maQiaoLineToMap.getMasterList();
			for (int i = 0, len = list.size(); i < len; i++) {
				sb.append("<tr>");
				Node f = list.get(i);
				System.out.print("[" + f.getKey() + "]\t终端节点数：" + f.checkTerminals() + "\t总数量：" + f.checkCount() + "\t分支：" + f.checkNodeMaxBranch());
				System.out.println("\t总汇总数量：" + f.checkValueSort());
				sb.append(f.checkToTableTr());
			}

			sb.append("</table>");
			;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static final class line implements IMQLine {
		public line(long value, String... arrays) {
			this.value = value;
			this.arrays = arrays;
		}

		/*
		 * (non-Javadoc)
		 * @see MaQiao.MaQiaoLineToMap.IMQLine#signArray()
		 */
		String[] arrays;

		@Override
		public Object[] signArray() {
			return arrays;
		}

		long value = 0;

		/*
		 * (non-Javadoc)
		 * @see MaQiao.MaQiaoLineToMap.IMQLine#value()
		 */
		@Override
		public long value() {
			return value;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("line [arrays=");
			builder.append(Arrays.toString(arrays));
			builder.append(", value=");
			builder.append(value);
			builder.append("]");
			return builder.toString();
		}

	}
}
