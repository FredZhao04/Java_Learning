/**
 * establish a single linked list
 * 创建一个单链表
 * @author fred
 *
 */
public class SingleLinkedList<E> {
	private Node<E> head;
	private E data;
	private int size;

	//constructor
	public SingleLinkedList() {
		head = new Node();
	}
	
	//get the size of the list
	//得到该链表的大小
	public int getSize() {
		return size;
	}
	
	//insert data into the end of the list 
	//向链表顺序添加data
	public void add(E data) {
		Node<E> node = new Node(data, null);
		Node<E> temp = head;
		while(temp.next != null) {
			temp = temp.next;
		}
		temp.next = node;
		size++;
	}
	
	//insert data into the list at the special position
	//在链表中指定的位置index添加data
	public void insertByIndex(E data, int index) {
		if(index < 1 || index > size) {
			System.out.println("脚标越界");
			return;
		}
		Node<E> node = new Node(data, null);
		Node<E> temp = head;
		
		int count = 1;
		while(temp.next != null) {
			if(count++ == index) {
				node.next = temp.next;
				temp.next = node;
				size++;
				return;
			}
			else {
				temp = temp.next;
			}
		}
		
	}
	
	//delete the data at a sepcial position
	//删除链表中指定位置的数据
	public void deleteByIndex(int index) {
		Node<E> temp = head;
		int count = 1;
		while(temp.next != null) {
			if(count++ == index) {
				temp.next = temp.next.next;
				return;
			}
			else {
				temp = temp.next;
			}
		}
	}
	
	//clear the list
	//清空链表
	public void clear() {
		size = 0;
		head.next = null;
	}
	
	@Override
	public String toString() {
		String result = "data: ";
		Node<E> temp = head;
		while(temp.next != null) {
			temp = temp.next;
			result += temp.data + ", ";
		}
		return result;
	}

	//establish a inner Node class
	//叶子类
	private class Node<E>{
		private E data;
		private Node<E> next;
		
		public Node(){
			this.data = null;
			this.next = null;
		}
		
		public Node(E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}
	}
	
}
