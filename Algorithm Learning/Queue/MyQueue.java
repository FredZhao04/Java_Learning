import sun.text.normalizer.UBiDiProps;

/**
 * Created by fred on 2018/6/9.
 */
public class MyQueue<E> {
    private Object[] list;
    private int size;
    private int head;
    private int tail;
    private static final int DEFAULT_CAPACITY = 2;

    public MyQueue() {
        this(DEFAULT_CAPACITY);
    }

    public MyQueue(int capacity) {
        list = new Object[capacity];
    }

    //进入队列
    public void enqueue(E o){
        if(size >= list.length){
            Object[] temp = new Object[list.length * 2];
            System.arraycopy(list, 0, temp, 0, list.length);
            list = temp;
        }
        list[tail++] = o;
        size++;
    }

    //出队列
    public E dequeue() throws Exception {
        if(isEmpty()){
            throw new Exception("the queue is empty");
        }
        E o = (E)list[head++];
        size--;
        return o;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int getSize(){
        return size;
    }
}
