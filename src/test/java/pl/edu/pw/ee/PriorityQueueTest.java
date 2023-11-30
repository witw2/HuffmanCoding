package pl.edu.pw.ee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityQueueTest {

    private PriorityQueue<Integer> priorityQueue;

    @BeforeEach
    public void setup() {
        priorityQueue = new PriorityQueue<>();
    }

    @Test
    public void pushAddsElementToEmptyQueue() {
        priorityQueue.push(1, 1);
        assertFalse(priorityQueue.isEmpty());
    }

    @Test
    public void pushAddsElementToNonEmptyQueue() {
        priorityQueue.push(1, 1);
        priorityQueue.push(2, 2);
        assertEquals(2, priorityQueue.size());
    }

    @Test
    public void popReturnsNullWhenQueueIsEmpty() {
        assertNull(priorityQueue.pop());
    }

    @Test
    public void popReturnsAndRemovesFirstElement() {
        priorityQueue.push(1, 1);
        priorityQueue.push(2, 2);
        assertEquals(1, priorityQueue.pop());
        assertEquals(1, priorityQueue.size());
    }

    @Test
    public void topReturnsNullWhenQueueIsEmpty() {
        assertNull(priorityQueue.top());
    }

    @Test
    public void topReturnsFirstElementWithoutRemoving() {
        priorityQueue.push(1, 1);
        priorityQueue.push(2, 2);
        assertEquals(1, priorityQueue.top());
        assertEquals(2, priorityQueue.size());
    }

    @Test
    public void isEmptyReturnsTrueWhenQueueIsEmpty() {
        assertTrue(priorityQueue.isEmpty());
    }

    @Test
    public void isEmptyReturnsFalseWhenQueueIsNotEmpty() {
        priorityQueue.push(1, 1);
        assertFalse(priorityQueue.isEmpty());
    }

    @Test
    public void sizeReturnsZeroWhenQueueIsEmpty() {
        assertEquals(0, priorityQueue.size());
    }

    @Test
    public void sizeReturnsCorrectSizeWhenQueueIsNotEmpty() {
        priorityQueue.push(1, 1);
        priorityQueue.push(2, 2);
        assertEquals(2, priorityQueue.size());
    }

    @Test
    public void clearEmptiesTheQueue() {
        priorityQueue.push(1, 1);
        priorityQueue.push(2, 2);
        priorityQueue.clear();
        assertTrue(priorityQueue.isEmpty());
    }
}