package your_code;

public class MyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Chicken val;
        Node prev;
        Node next;

        private Node(Chicken d, Node prev, Node next) {
            this.val = d;
            this.prev = prev;
            this.next = next;
        }

        private Node(Chicken d) {
            this.val = d;
            prev = null;
            next = null;
        }
    }

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(Chicken c) {
        addLast(c);
    }

    public Chicken pop() {
        return removeLast();
    }

    // O(1)
    public void addLast(Chicken c) {
        Node newNode = new Node(c);
        if (size == 0){
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // O(1)
    public void addFirst(Chicken c) {
        Node newNode = new Node(c);
        if (size == 0){
            head = newNode;
            tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    // O(n)
    private Node getNode(int index) {
        if (index >= size && index < 0) throw new IndexOutOfBoundsException();

        Node currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    // O(n)
    public Chicken get(int index) {
        return getNode(index).val;
    }

    // O(n)
    public Chicken remove(int index) {
        if (size == 0) return null;

        Node removeNode = getNode(index);

        if(size == 1) {
            head = tail = null;
            size--;
            return removeNode.val;
        } else if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node prevNode = removeNode.prev;
            Node nextNode = removeNode.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            size--;
            return removeNode.val;
        }
    }

    // O(1)
    public Chicken removeFirst() {
        if (size == 0) return null;

        Node removeNode = head;

        if(size == 1) {
            head = tail = null;
            size--;
            return removeNode.val;
        } else {
            Node nextNode = head.next;
            nextNode.prev = null;
            head.next = null;
            head = nextNode;
            size--;
            return removeNode.val;
        }
    }

    // O(1)
    public Chicken removeLast() {
        if (size == 0) return null;

        Node removeNode = tail;

        if(size == 1) {
            head = tail = null;
            size--;
            return removeNode.val;
        } else {
            Node prevNode = tail.prev;
            prevNode.next = null;
            tail.prev = null;
            tail = prevNode;
            size--;
            return removeNode.val;
        }
    }
}