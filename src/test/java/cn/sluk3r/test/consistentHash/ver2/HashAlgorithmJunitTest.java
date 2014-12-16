package cn.sluk3r.test.consistentHash.ver2;

import cn.sluk3r.test.consistentHash.self.Node;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class HashAlgorithmJunitTest {
	static Random ran = new Random();
	
	/** key's count */
	private static final Integer EXE_TIMES = 100000;
	
	private static final Integer NODE_COUNT = 5;
	
	private static final Integer VIRTUAL_NODE_COUNT = 160;

	@Test
	public void test() {
		/** Records the times of locating node*/
		ConcurrentMap<Node, AtomicLong> nodeVisitRecord = new ConcurrentHashMap<Node, AtomicLong>();
		
		List<Node> allNodes = createNodesForTest(NODE_COUNT);
		KetamaNodeLocator locator = new KetamaNodeLocator(allNodes, HashAlgorithm.KETAMA_HASH, VIRTUAL_NODE_COUNT);
		
		List<String> allKeys = createRandomStringForTest();
		for (String key : allKeys) {
			Node node = locator.getPrimary(key);

			nodeVisitRecord.putIfAbsent(node, new AtomicLong(0));
			nodeVisitRecord.get(node).incrementAndGet();
		}
		
		System.out.println("Nodes count : " + NODE_COUNT + ", Keys count : " + EXE_TIMES + ", Normal percent : " + (float) 100 / NODE_COUNT + "%");
		System.out.println("-------------------- boundary  ----------------------");
		for (Map.Entry<Node, AtomicLong> entry : nodeVisitRecord.entrySet()) {
			System.out.println("Node name :" + entry.getKey() + " - Times : " + entry.getValue() + " - Percent : " + (float)entry.getValue().get() / EXE_TIMES * 100 + "%");
		}
		
	}
	
	
	/**
	 * Gets the mock node by the material parameter
	 * 
	 * @param nodeCount 
	 * 		the count of node wanted
	 * @return
	 * 		the node list
	 */
	private List<Node> createNodesForTest(int nodeCount) {
		List<Node> nodes = new ArrayList<Node>();
		
		for (int k = 1; k <= nodeCount; k++) {
			Node node = new Node("node" + k);
			nodes.add(node);
		}
		
		return nodes;
	}
	
	/**
	 *	All the keys	
	 */
	private List<String> createRandomStringForTest() {
		List<String> allStrings = new ArrayList<String>(EXE_TIMES);
		
		for (int i = 0; i < EXE_TIMES; i++) {
			int length = ran.nextInt(50);
			StringBuffer sb = new StringBuffer(length);

			for (int j = 0; j < length; j++) {
				sb.append((char) (ran.nextInt(95) + 32));
			}

			allStrings.add(sb.toString());
		}
		
		return allStrings;
	}
}
