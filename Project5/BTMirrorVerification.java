import java.util.*;

class Node
{
    int data;
    Node left, right;
 
    public Node(int item)
    {
        data = item;
        left = right = null;
    }
}
 
class BTMirrorVerification
{
    private static boolean verify(Node a, Node b) 
    {
        /* Base case : Both empty */
        if (a == null && b == null)
            return true;
 
        // If only one is empty
        if (a == null || b == null) 
            return false;
 
        /* Both non-empty, compare them recursively
           Note that in recursive calls, we pass left
           of one tree and right of other tree */
        boolean x = a.data == b.data
                && verify(a.left, b.right)
                && verify(a.right, b.left);
        return x;
    }

    // Decodes your encoded data to tree.
    public static Node deserialize(String data) 
    {
        if (data == null || data.length() == 0) {
            return null;
        }
        if(data.startsWith("["))
            data = data.substring(1, data.length());
        if(data.endsWith("]"))
            data = data.substring(0, data.length()-1);

        data = data.replace(" ", ""); // omit any space
        data = data.replace("null", "#");

        Queue<Node> queue = new LinkedList<Node>();
        String[] nodes = data.split(",");

        Node root = new Node(Integer.parseInt(nodes[0]));
        queue.offer(root);
        int i = 1;

        while (!queue.isEmpty() && i < nodes.length) {
            Node curr = queue.poll();
            // left node
            if (nodes[i].equals("#")) {
                curr.left = null;
            } else {
                Node leftNode = new Node(Integer.parseInt(nodes[i]));
                curr.left = leftNode;
                queue.offer(leftNode);
            }

            i++;
            if (i >= nodes.length) {
                break;
            }

            // right node
            if (nodes[i].equals("#")) {
                curr.right = null;
            } else {
                Node rightNode = new Node(Integer.parseInt(nodes[i]));
                curr.right = rightNode;
                queue.offer(rightNode);
            }

            i++;
        }

        return root;
    }

    
    public static void main(String args[])
    {
        // case 1
        // String tree1 = "[3,9,20,null,null,15,7]"; 
        // String tree2 = "[3,20,9,7,15]"; 

        // case 2
        // String tree1 = "[5,14,15,null,3,6,9,1]"; 
        // String tree2 = "[5,15,14,9,6,3,null,null,null,null,1]";

        // case 3
        // String tree1 = "[3,20,9,null,null,1,5,2,4,null,null,15]"; 
        // String tree2 = "[3,9,20,5,1,null,null,null,null,4,2 ,null,15]";

        String tree1 = "[499, 185, 259, 873, 235, 716, 359, 818, 869, 904]"; 

        String tree2 = "[499,259,185,359,716,235,873,null,null,null,null,null,904,869,818]";
 
        /* Serialize the input tree */
        System.out.println("\nSerializing the first binary tree is :");
        System.out.println(tree1);
        System.out.println("");
 
        /* Serialize the input tree */
        System.out.println("Serializing the second binary tree is : ");
        System.out.println(tree2);
        System.out.println();

        Node rootA = deserialize(tree1);
        Node rootB = deserialize(tree2);

        long startRecording = System.nanoTime();
        if (verify(rootA, rootB) == true)
            System.out.println("Yes, mirror images");
        else
            System.out.println("No, not mirror images");

        long endRecording = System.nanoTime();

        long time=(endRecording-startRecording)/1000;
        System.out.println("Time Complexity " + time + " Microseconds\n");
 
    }
}