import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.List;

/**
 * 利用ArrayList生成Stack
 * Created by fred on 2018/6/9.
 */
public class StackVersion02<E> {
    List<E> list = new ArrayList<E>();

    public StackVersion02() {
    }

    public void push(E o){
        list.add(o);
    }

    //return the the top item of the stack and remove it from the stack
    public E pop(){
        E o = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        return o;
    }

    public E peek(){
        return list.get(list.size() - 1);
    }

    public int getSize(){
        return list.size();
    }

    public boolean isEmpty(){
        return list.size() == 0;
    }
}
