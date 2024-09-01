import java.util.ArrayList;

public class Exam_3 {
	private final static int INFINITY = 999;

	// ------This program is divided into 3 parts------//
	// ------------------Part 1 main------------------//
	public static void main(String args[]) {
		// 1.input from exam.
		int input[][] = { { 1, 2, 1 }, { 2, 3, 2 }, { 1, 3, 4 }, { 3, 4, 1 } };
		int nodeStart = 1, nodeEnd = 4;
		ArrayList<Node> graph = new ArrayList<Node>();
		// 2.add node to the graph from input[i][0]
		for (int i = 0; i < input.length; i++) {
			boolean haveNode = false;
			int nodeId = input[i][0];
			for (int j = 0; j < graph.size(); j++) {
				if (nodeId == graph.get(j).getNodeId())
					haveNode = true;
			}
			if (!haveNode)
				graph.add(new Node(nodeId));

		}
		// 3.add node to the graph from input[i][1]
		for (int i = 0; i < input.length; i++) {
			boolean haveNode = false;
			int nodeId = input[i][1];
			for (int j = 0; j < graph.size(); j++) {
				if (nodeId == graph.get(j).getNodeId())
					haveNode = true;
			}
			if (!haveNode)
				graph.add(new Node(nodeId));

		}
		// 3.add nextNod and weight from input array to all node in the graph
		for (int i = 0; i < graph.size(); i++) {
			for (int j = 0; j < input.length; j++) {
				if (graph.get(i).getNodeId() == input[j][0]) {
					int weight = input[j][2];
					Node node = Node.findNodeById(graph, input[j][1]);
					NextNode next = new NextNode(node, weight);
					graph.get(i).getNextNode().add(next);
				}
			}
		}

		// 4.display all node for graph, node: nextnodeId[weight] ... nextnodeId[weight]
		System.out.println("Display all node from graph");
		for (Node node : graph) {
			System.out.print(node.getNodeId() + ": ");
			for (NextNode next : node.getNextNode())
				System.out.print(String.format("%d[%d] ", next.getNode().getNodeId(), next.getWeight()));
			System.out.println();
		}
		// 5.use Dijkstra algorithm
		int totalDistance = Dijkstra(graph, nodeStart, nodeEnd);
		System.out.println("Total distance = " + totalDistance);
	}

	// ------------------Part 2 Dijkstra Algorithm------------------//
	public static int Dijkstra(ArrayList<Node> graph, int nodeStart, int nodeEnd) {
		// 1. set initial value distance graph ,give distance all node except start node
		// = infinity,give distance start node = 0.
		for (Node node : graph) {
			if (node.getNodeId() == nodeStart)
				continue;
			node.setTotalDistance(INFINITY);
		}
		// 2. create min heap and total distance for Dijkstra shorted part.
		Heap heap = new Heap();
		int totalDistance = 0;
		for (Node node : graph) {
			heap.insertHeap(node);
		}
		// 3. loop for find the next node that will going.
		while (heap.getNode().size() != 0) {
			Node u = heap.removeHeap();
			totalDistance = u.getTotalDistance();
			for (NextNode nextNode : u.getNextNode()) {
				int du = u.getTotalDistance();
				int dv = nextNode.getNode().getTotalDistance();
				int w = nextNode.getWeight();
				if (du + w < dv) {
					nextNode.getNode().setTotalDistance(du + w);
					nextNode.getNode().setParent(u);
					heap.decreaseKey(nextNode.getNode());
				}
			}

		}
		// 4. return distance.
		return totalDistance;
	}

	// ------------------Part 3 Heap and Node class------------------//
	private static class Heap {
		private ArrayList<Node> node = new ArrayList<Node>();

		public ArrayList<Node> getNode() {
			return node;
		}

		public void setNode(ArrayList<Node> node) {
			this.node = node;
		}

		public void insertHeap(Node node) {
			int index = this.node.size();
			node.setIndexOfnode(index);
			this.node.add(node);
			while (index >= 0) {
				int indexParent = (index - 1) / 2;
				Node current = this.node.get(index);
				Node parent = this.node.get(indexParent);
				if (current.getTotalDistance() < parent.getTotalDistance()) {
					swap(current.getIndexOfNode(), parent.getIndexOfNode());
					index = indexParent;
				} else
					break;

			}
		}

		public Node removeHeap() {
			int index = 0;
			int left = 2 * index + 1;
			int right = 2 * index + 2;
			ArrayList<Node> heap = this.node;
			Node leafNode = heap.get(heap.size() - 1);
			Node removeNode = heap.get(0);
			leafNode.setIndexOfnode(0);
			heap.set(0, leafNode);
			heap.remove(heap.size() - 1);
			int n = this.node.size();
			boolean leftVirtual, rightVirtual;
			leftVirtual = rightVirtual = false;
			if (left >= n) {
				heap.add(new Node());
				leftVirtual = true;
			}
			if (right >= n) {
				heap.add(new Node());
				rightVirtual = true;
			}
			while (left < n) {
				if ((heap.get(index).getTotalDistance() <= heap.get(left).getTotalDistance())
						&& (heap.get(index).getTotalDistance() <= heap.get(right).getTotalDistance())) {
					break;

				}
				if (heap.get(left).getTotalDistance() < heap.get(right).getTotalDistance()) {
					swap(heap.get(index).getIndexOfNode(), heap.get(left).getIndexOfNode());
					index = left;
				} else {
					swap(heap.get(index).getIndexOfNode(), heap.get(right).getIndexOfNode());
					index = right;
				}
				removeVirtualNode(heap, leftVirtual, rightVirtual);
				left = 2 * index + 1;
				right = 2 * index + 2;
			}
			removeVirtualNode(heap, leftVirtual, rightVirtual);
			return removeNode;
		}

		public void removeVirtualNode(ArrayList<Node> heap, boolean leftVirtual, boolean rightVirtual) {
			if (rightVirtual == true) {
				heap.remove(heap.size() - 1);
				rightVirtual = false;
			}
			if (leftVirtual == true) {
				heap.remove(heap.size() - 1);
				leftVirtual = false;
			}
		}

		public void swap(int indexX1, int indexX2) {
			ArrayList<Node> node = this.node;
			Node temp = node.get(indexX1);
			Node temp2 = node.get(indexX2);
			temp.setIndexOfnode(indexX2);
			temp2.setIndexOfnode(indexX1);
			node.set(indexX1, temp2);
			node.set(indexX2, temp);
		}

		public void decreaseKey(Node v) {
			ArrayList<Node> oldHeap = this.node;
			Heap newHeap = new Heap();
			for (Node node : oldHeap) {
				if (node.getNodeId() == v.getNodeId()) {
					newHeap.insertHeap(v);
					continue;
				}
				newHeap.insertHeap(node);
			}
			this.node = newHeap.getNode();
		}
	}

	private static class Node {
		private int nodeId;
		private int indexOfNode;
		private int totalDistance;
		private Node parent;
		private ArrayList<NextNode> nextNode = new ArrayList<NextNode>();

		public Node() {
			nodeId = -1;
			setTotalDistance(INFINITY);
		}

		public Node(int id) {
			this.setNodeId(id);
		}

		public static Node findNodeById(ArrayList<Node> graph, int id) {
			for (Node node : graph) {
				if (id == node.getNodeId())
					return node;
			}
			return null;
		}

		public void setIndexOfnode(int index) {
			this.indexOfNode = index;
		}

		public int getIndexOfNode() {
			return indexOfNode;
		}

		public void setTotalDistance(int totalDistance) {
			this.totalDistance = totalDistance;
		}

		public int getTotalDistance() {
			return totalDistance;
		}

		public int getNodeId() {
			return nodeId;
		}

		public void setNodeId(int nodeId) {
			this.nodeId = nodeId;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public ArrayList<NextNode> getNextNode() {
			return nextNode;
		}

		public void setNextNode(ArrayList<NextNode> nextNode) {
			this.nextNode = nextNode;
		}
	}

	private static class NextNode {
		private Node node;
		private int weight;

		public NextNode(Node nextNode, int weight) {
			this.setNode(nextNode);
			this.setWeight(weight);
		}

		public Node getNode() {
			return node;
		}

		public void setNode(Node nextNode) {
			this.node = nextNode;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}
	}
}