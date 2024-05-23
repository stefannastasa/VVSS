package tests.integration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceArrayListNonMock {
    private TasksService tasksService;
    private ArrayTaskList taskList;
    private Task task;

    private Date now;

    @BeforeEach
    public void setUp() {
        taskList = new ArrayTaskList();
        now = new Date();
        task = new Task("Integration Test Task1", now, new Date(now.getTime() + 1000 * 3600 * 2), 3600); // every hour
        taskList.add(task);
        tasksService = new TasksService(taskList);
    }

    @Test
    public void testGetObservableList() {
        ObservableList<Task> result = tasksService.getObservableList();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(task, result.get(0));
    }

    @Test
    public void testFilterTasks() {
        Date end = new Date(now.getTime() + 1000 * 3600 * 24); // 24 hours later

        Iterable<Task> filteredTasks = tasksService.filterTasks(now, end);
        assertNotNull(filteredTasks);
        assertFalse(filteredTasks.iterator().hasNext());
    }
}