package com.demis.companypersontaskapi.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.demis.companypersontaskapi.ResourceNotFoundException;
import com.demis.companypersontaskapi.PageResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Spy
    private TaskMapper taskMapper;

    @Spy
    private PageResponseMapper pageResponseMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createShouldPersistTask() {
        // given
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        TaskResponse response = taskService.create(new TaskRequest("Launch MVP", "Initial release"));

        // then
        assertThat(response.title()).isEqualTo("Launch MVP");
        assertThat(response.description()).isEqualTo("Initial release");
        assertThat(response.id()).isNotBlank();
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void getByIdShouldReturnTaskWhenFound() {
        // given
        when(taskRepository.findById("t1")).thenReturn(Optional.of(new Task("t1", "Launch MVP", "Initial release")));

        // when
        TaskResponse response = taskService.getById("t1");

        // then
        assertThat(response.id()).isEqualTo("t1");
        assertThat(response.title()).isEqualTo("Launch MVP");
    }

    @Test
    void getByIdShouldThrowWhenTaskMissing() {
        // given
        when(taskRepository.findById("missing")).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> taskService.getById("missing"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("missing");
    }
}
