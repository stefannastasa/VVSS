package tasks.integration;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

        import javafx.collections.ObservableList;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Date;

class TaskServiceArrayTaskListIntegrationTest {
    @Mock
    private Task mockTask;
    private TasksService tasksService;
    private ArrayTaskList taskList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        taskList = new ArrayTaskList();
        taskList.add(mockTask);
        tasksService = new TasksService(taskList);
    }

    @Test
    void testGetObservableList() {
        ObservableList<Task> result = tasksService.getObservableList();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(mockTask, result.get(0));
    }

    @Test
    void testFilterTasks() {
        when(mockTask.nextTimeAfter(any())).thenReturn(new Date());
        Date start = new Date();
        Date end = new Date(start.getTime() + 1000 * 3600 * 24); // 24 hours later

        Iterable<Task> filteredTasks = tasksService.filterTasks(start, end);
        assertNotNull(filteredTasks);
        assertTrue(filteredTasks.iterator().hasNext());
        assertEquals(mockTask, filteredTasks.iterator().next());

        verify(mockTask, atLeastOnce()).nextTimeAfter(any(Date.class));
    }
}