public class Node {
    int data;
    boolean isFilled;
    Node next;
    Node prev;
    Node above;
    Node below;

    public Node(int data) {
        this.data = data;
        this.isFilled = false;
        this.next = null;
        this.prev = null;
        this.above = null;
        this.below = null;
    }
}
