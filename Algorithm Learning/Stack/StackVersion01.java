/**
 * Stack version 01
 * Created by fred on 2018/6/9.
 */
public class StackVersion01<E> {
    private Object[] list;
    private int size;
    private static final int DEFAULT_CAPACITY = 2;

    public StackVersion01() {
        this(DEFAULT_CAPACITY);
    }

    public StackVersion01(int capacity) {
       list = new Object[capacity];
    }

    public void push(E o){
        if(size >= list.length){
            Object[] temp = new Object[list.length * 2];
            System.arraycopy(list, 0, temp, 0, list.length);
            list = temp;
        }
        list[size++] = o;
    }

    public E pop(){
        return (E)list[--size];
    }

    public E peek(){
        return (E)list[size - 1];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int getSize(){
        return size;
    }
}
