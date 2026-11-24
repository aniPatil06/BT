import java.util.Scanner;
public class Main {
 static final int T = 3;
 static class Node { int n=0; int[] keys=new int[2*T-1]; Node[] child=new Node[2*T]; boolean leaf=true; }
 static Node root=new Node();
 static boolean search(Node x,int key) {
  int i=0; while(i<x.n && key>x.keys[i]) i++;
  if(i<x.n && key==x.keys[i]) return true;
  if(x.leaf) return false;
  return search(x.child[i],key);
 }
 static void splitChild(Node parent,int index) {
  Node y=parent.child[index], z=new Node(); z.leaf=y.leaf; z.n=T-1;
  for(int j=0;j<T-1;j++) z.keys[j]=y.keys[j+T];
  if(!y.leaf) for(int j=0;j<T;j++) z.child[j]=y.child[j+T];
  int middleKey=y.keys[T-1]; y.n=T-1;
  for(int j=parent.n;j>=index+1;j--) parent.child[j+1]=parent.child[j];
  parent.child[index+1]=z;
  for(int j=parent.n-1;j>=index;j--) parent.keys[j+1]=parent.keys[j];
  parent.keys[index]=middleKey; parent.n++;
 }
 static void insertNonFull(Node x,int key) {
  int i=x.n-1;
  if(x.leaf) { while(i>=0 && key<x.keys[i]) { x.keys[i+1]=x.keys[i]; i--; } x.keys[i+1]=key; x.n++; }
  else { while(i>=0 && key<x.keys[i]) i--; i++;
   if(x.child[i].n==2*T-1) { splitChild(x,i); if(key>x.keys[i]) i++; }
   insertNonFull(x.child[i],key);
  }
 }
 static void insert(int key) {
  if(search(root,key)) return;
  if(root.n==2*T-1) { Node newRoot=new Node(); newRoot.leaf=false; newRoot.child[0]=root; splitChild(newRoot,0); root=newRoot; }
  insertNonFull(root,key);
 }
 static void display(Node x) {
  int i; for(i=0;i<x.n;i++) { if(!x.leaf) display(x.child[i]); System.out.print(x.keys[i]+" "); }
  if(!x.leaf) display(x.child[i]);
 }
 public static void main(String[] args) {
  Scanner sc=new Scanner(System.in); System.out.print("Enter number of keys: "); int n=sc.nextInt();
  if(n<0) { System.out.println("Invalid input."); return; }
  System.out.println("Enter keys:"); for(int i=0;i<n;i++) insert(sc.nextInt());
  System.out.print("B-Tree: "); display(root);
  System.out.print("\nEnter key to search: "); int key=sc.nextInt();
  System.out.println(search(root,key) ? "Key found." : "Key not found.");
 }
}
