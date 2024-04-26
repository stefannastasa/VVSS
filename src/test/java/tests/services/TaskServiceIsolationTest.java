package tests.services;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import javafx.collections.ObservableList;
import tasks.model.Task;
import tasks.model.TaskList;
import tasks.services.TasksService;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

class TasksServiceIsolationTest {

    @Mock
    private TaskList taskListMock;

    @InjectMocks
    private TasksService tasksService;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        task = new Task("Test Task", new Date());
        when(taskListMock.getAll()).thenReturn(List.of(task));
    }

    @Test
    void testGetObservableList() {
        ObservableList<Task> observableList = tasksService.getObservableList();
        assertNotNull(observableList);
        assertFalse(observableList.isEmpty());
        assertEquals(1, observableList.size());
        assertEquals(task, observableList.get(0));
        verify(taskListMock).getAll();
    }

    @Test
    void testGetIntervalInHours() {
        task.setTime(new Date(), new Date(System.currentTimeMillis() + 3600000), 3600); // 1 hour
        String result = tasksService.getIntervalInHours(task);
        assertEquals("01:00", result);
    }

    @Test
    void testFormTimeUnit() {
        assertEquals("00", tasksService.formTimeUnit(0));
        assertEquals("05", tasksService.formTimeUnit(5));
        assertEquals("10", tasksService.formTimeUnit(10));
    }

    @Test
    void testParseFromStringToSeconds() {
        assertEquals(3600, tasksService.parseFromStringToSeconds("01:00")); // 1 hour in seconds
        assertEquals(7200, tasksService.parseFromStringToSeconds("02:00")); // 2 hours in seconds
    }

}
