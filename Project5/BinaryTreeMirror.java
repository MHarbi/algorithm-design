import java.util.*;
// Java program to convert binary tree into its mirror
 
/* Class containing left and right child of current
   node and key value*/
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
 
class BinaryTreeMirror
{
    private static Node mirror(Node node)
    {
        
        Node m_root = null;
        if (node == null)
            return m_root;
        
        m_root = new Node(node.data);

        /* do the subtrees *//* swap the left and right pointers */
        m_root.left = mirror(node.right);
        m_root.right = mirror(node.left);

        return m_root;
    }

    // Encodes a tree to a single string.
    public static String serialize(Node root) {
        if (root == null) {
            return "";
        }

        Queue<Node> queue = new LinkedList<Node>();
        queue.offer(root);

        StringBuffer sb = new StringBuffer();

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            if (curr != null) {
                sb.append(curr.data + ",");
                queue.offer(curr.left);
                queue.offer(curr.right);
            } else {
                sb.append("#" + ",");
            }
        }

        // Remove the trailing #
        int j = sb.length() - 1;
        while (j > 0 && (sb.charAt(j) == ',' && sb.charAt(j-1) == '#')) {
            j -= 2;
        }

        return "[" + sb.substring(0, j).toString().replace("#", "null") + "]";
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

    
 
    /* testing for example nodes */
    public static void main(String args[])
    {
        /* creating a binary tree and entering the nodes */

        Node oroot = deserialize("[3,9,20,null,null,15,7]"); // case 1
        // Node oroot = deserialize("[5,14,15,null,3,6,9,1]"); // case 2
        // Node oroot = deserialize("[3,20,9,null,null,1,5,2,4,null,null,15]"); // case 3
 
        /* Serialize the input tree */
        System.out.println("\nSerializing the input tree is :");
        System.out.println(serialize(oroot));
        System.out.println("");

        long startRecording = System.nanoTime();
        /* convert tree to its mirror */
        Node mroot = mirror(oroot);

        long endRecording = System.nanoTime();
 
        /* Serialize the input tree */
        System.out.println("Serializing the binary tree is : ");
        System.out.println(serialize(mroot));
        System.out.println();

        long time=(endRecording-startRecording)/1000;
        System.out.println("Time Complexity " + time + " Microseconds\n");
 
    }
}