public class Stack<Item> {

    public class Cell {
        private Item item;
        private Cell next;
    }
    public int size;
    public Cell top;

    public Stack() {
        top = null;
        size = 0;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void push(Item item) {
        Cell oldtop = top;
        top = new Cell();
        top.item = item;
        top.next = oldtop;
        size++;
    }

    public Item pop() {
        if (isEmpty())
            System.err.println("Stack underflow");
        Item item = top.item;
        top = top.next;
        size--;
        return item;
    }

    public Item peek() {
        if (isEmpty())
            System.err.println("Stack underflow");
        return top.item;
    }
}