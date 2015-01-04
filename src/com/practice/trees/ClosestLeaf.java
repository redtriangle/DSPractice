package com.practice.trees;

/*
 * Find the closest leaf in a Binary Tree
 *   		  A
            /    \    
           B       C
                 /   \  
                E     F   
               /       \
              G         H
             / \       /
            I   J     K

Closest leaf to 'H' is 'K', so distance is 1 for 'H'
Closest leaf to 'C' is 'B', so distance is 2 for 'C'
Closest leaf to 'E' is either 'I' or 'J', so distance is 2 for 'E' 
Closest leaf to 'B' is 'B' itself, so distance is 0 for 'B' 
 */


/**
 * Answer
 * The main point to note here is that a closest key can 
 * either be a descendent of given key or can be reached through one of the ancestors.
 * The idea is to traverse the given tree in preorder and keep track of ancestors
 * in an array. When we reach the given key, we evaluate distance of the 
 * closest leaf in subtree rooted with given key. We also traverse all ancestors
 * one by one and find distance of the closest leaf in the subtree 
 * rooted with ancestor. We compare all distances and return minimum.
 *
 */
public class ClosestLeaf {
	
	static class Node {
		String key;
		Node leftNode;
		Node rightNode;
		Node(String key) {
			this.key = key;
		}
	}

	public int findClosestLeaf(Node root, String key) {
		Node[] ancestors = new Node[100];
		return getClosestDistance(root, key, ancestors, 0);
	}

	private int getClosestDistance(Node root, String key, Node[] ancestors, int index) {
		if(root == null)
			return Integer.MAX_VALUE;
		if(root.key == key) {
			// Find the closest leaf for the subtree.
			int res = getClosestLeafForSubtree(root);
			for(int i = index-1; i>=0 ; i--) {
				//Find the closest leaf of all the ancestors and compare it with the result from the subtree.
				res = getMin(res, index - i + getClosestLeafForSubtree(ancestors[i]));
			}
			return res;
		}
		ancestors[index] = root;
		return getMin(getClosestDistance(root.leftNode, key, ancestors, index+1),
				getClosestDistance(root.rightNode, key, ancestors, index+1));
	}

	private int getClosestLeafForSubtree(Node root) {
		if(root == null)
			return Integer.MAX_VALUE;
		if(root.leftNode == null && root.rightNode == null)
			return 0 ;
		return 1 + getMin(getClosestLeafForSubtree(root.leftNode), getClosestLeafForSubtree(root.rightNode));
	}

	private int getMin(int left, int right) {
		if(left < right)
			return left;
		else 
			return right;
	}
	
	public static void main(String[] args) {
		ClosestLeaf leaf = new ClosestLeaf();
		Node a = new Node("a");
		Node b = new Node("b");
		Node c = new Node("c");
		Node d = new Node("d");
		Node e = new Node("e");
		Node f = new Node("f");
		Node g = new Node("g");
		Node h = new Node("h");
		Node i = new Node("i");
		Node j = new Node("j");
		Node k = new Node("k");
		a.leftNode = b;
		a.rightNode = c;
		c.leftNode = e;
		c.rightNode = f;
		e.leftNode = g;
		g.leftNode = i;
		g.rightNode = j;
		f.rightNode = h;
		h.leftNode = k;
		System.out.println("Closest leaf to h is "+ leaf.findClosestLeaf(a, "h"));
		System.out.println("Closest leaf to e is "+ leaf.findClosestLeaf(a, "e"));
		System.out.println("Closest leaf to c is "+ leaf.findClosestLeaf(a, "c"));
		System.out.println("Closest leaf to k is "+ leaf.findClosestLeaf(a, "k"));

	}
}
