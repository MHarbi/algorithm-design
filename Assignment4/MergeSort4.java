public class MergeSort4 {

	private static class Node {
		int data;
		Node next;

		public Node(int data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

	private static class Split { Node a, b; }

	public Node mergesort(Node head) {

		if (head == null || head.next == null)
			return head;

		Split split = new Split();
		doSplit(head, split);

		Node a = split.a;
		Node b = split.b;

		a = mergesort(a);
		b = mergesort(b);

		head = merge(a, b);
		return head;

	}

	private Node merge(Node a, Node b) {

		Node result;

		if (a == null) return b;

		if (b == null) return a;

		if (a.data < b.data) {
			result = a;
			result.next = merge(a.next, b);
		} else {
			result = b;
			result.next = merge(a, b.next);
		}

		return result;

	}

	private void doSplit(Node head, Split split) {

		Node fast = head.next, slow = head;

		if (head == null || head.next == null) {
			split.a = head;
			split.b = null;
			
		} else {

			while (fast != null) {
				fast = fast.next;
				if (fast != null) {
					slow = slow.next;
					fast = fast.next;
				}
			}
			
			split.a = head;
			split.b = slow.next;
			slow.next = null;
		}
		

	}

	// Driver program
	public static void main(String[] args) {

		// 2->3->20->5->10->15

		Node head = new Node(2, new Node(3, new Node(20, new Node(5, new Node(10, new Node(50, new Node(15, null)))))));
		Node link = new MergeSort4().sort(head);

		while (link != null) {
			System.out.print(link.data + " ");
			link = link.next;

		}
		System.out.println();

	}
}
