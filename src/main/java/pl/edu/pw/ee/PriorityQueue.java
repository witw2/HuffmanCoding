package pl.edu.pw.ee;

public class PriorityQueue<T>{
    static class Node<T>{
        private T value;
        private int priority;
        public Node(T value, int priority){
            this.value = value;
            this.priority = priority;
        }
        public T getValue(){
            return value;
        }
        public int getPriority(){
            return priority;
        }
    }
    private Node<T>[] queue;
    private int size = 0;
    public PriorityQueue(){
        queue = new Node[100];
    }
    public void push(T value, int priority){
        Node<T> node = new Node<>(value, priority);
        if(size == queue.length){
            Node<T>[] newQueue = new Node[queue.length * 2];
            System.arraycopy(queue, 0, newQueue, 0, queue.length);
            queue = newQueue;
        }
        queue[size] = node;
        size++;
        int i = size - 1;
        while(i > 0 && queue[i].getPriority() < queue[(i - 1) / 2].getPriority()){
            Node<T> temp = queue[i];
            queue[i] = queue[(i - 1) / 2];
            queue[(i - 1) / 2] = temp;
            i = (i - 1) / 2;
        }
    }
    public T pop(){
        if(size == 0){
            return null;
        }else{
            T value = queue[0].getValue();
            queue[0] = queue[size - 1];
            size--;
            int i = 0;
            while(i < size){
                int min = i;
                if(2 * i + 1 < size && queue[2 * i + 1].getPriority() < queue[min].getPriority()){
                    min = 2 * i + 1;
                }
                if(2 * i + 2 < size && queue[2 * i + 2].getPriority() < queue[min].getPriority()){
                    min = 2 * i + 2;
                }
                if(min == i){
                    break;
                }
                Node<T> temp = queue[i];
                queue[i] = queue[min];
                queue[min] = temp;
                i = min;
            }
            return value;
        }
    }

    public T top() {
        if (size == 0) {
            return null;
        } else {
            return queue[0].getValue();
        }
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void clear(){
        size = 0;
    }
}