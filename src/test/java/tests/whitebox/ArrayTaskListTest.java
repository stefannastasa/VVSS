package tests.whitebox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayTaskListTest {
    private ArrayTaskList atl;
    private Date date;

    @BeforeEach
    public void setUp() {
        date = new Date(0L);
        atl = new ArrayTaskList();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testRemove_TaskNotFound_ReturnsFalse() {
        // arrange
        atl.add(new Task("task42", date));
        atl.add(new Task("task55", date));
        Task task = new Task("task1", date);

        // act
        boolean result = atl.remove(task);

        // assert
        assertFalse(result);
    }

    @Test
    public void RemoveTaskNull() {
        // arrange
        atl.add(new Task("task42", date));
        Task task = null;

        // act
        boolean result = atl.remove(task);

        // assert
        assertFalse(result);
    }

    @Test
    public void RemoveTask() {
        // arrange
        atl.add(new Task("task1", date));
        atl.add(new Task("task2", date));
        Task task = new Task("task1", date);

        // act
        boolean result = atl.remove(task);

        // assert
        assertTrue(result);
    }

    @Test
    public void testRemove_EmptyList_ReturnsFalse() {
        // arrange
        Task task = new Task("task1", date);

        // act
        boolean result = atl.remove(task);

        // assert
        assertFalse(result);
    }
}
