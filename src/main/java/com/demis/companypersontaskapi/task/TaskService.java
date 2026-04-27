package com.demis.companypersontaskapi.task;

import java.util.UUID;

import com.demis.companypersontaskapi.PageResponse;
import com.demis.companypersontaskapi.PageResponseMapper;
import com.demis.companypersontaskapi.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final PageResponseMapper pageResponseMapper;

    public TaskResponse create(TaskRequest request) {
        Task task = new Task(UUID.randomUUID().toString(), request.title(), request.description());
        Task saved = taskRepository.save(task);
        return taskMapper.toResponse(saved);
    }

    public TaskResponse getById(String id) {
        return taskMapper.toResponse(getEntityById(id));
    }

    public PageResponse<TaskResponse> getAll(int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(parseDirection(sortDir), sortBy));
        Page<TaskResponse> taskPage = taskRepository.findAll(pageable).map(taskMapper::toResponse);
        return pageResponseMapper.toResponse(taskPage);
    }

    public TaskResponse update(String id, TaskRequest request) {
        Task task = getEntityById(id);
        task.setTitle(request.title());
        task.setDescription(request.description());
        return taskMapper.toResponse(taskRepository.save(task));
    }

    public void delete(String id) {
        taskRepository.deleteById(id);
    }

    public Task getEntityById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id %s not found".formatted(id)));
    }

    private static Sort.Direction parseDirection(String sortDir) {
        return "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
