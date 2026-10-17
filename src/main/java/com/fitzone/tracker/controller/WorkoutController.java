package com.fitzone.tracker.controller;

import com.fitzone.tracker.entity.Workout;
import com.fitzone.tracker.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<Workout> logWorkout(@Valid @RequestBody Workout workout) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.logWorkout(workout));
    }

    @GetMapping
    public List<Workout> getAllWorkouts() {
        return workoutService.getAllWorkouts();
    }

    @GetMapping("/{id}")
    public Workout getWorkoutById(@PathVariable String id) {
        return workoutService.getWorkoutById(id);
    }

    @PutMapping("/{id}")
    public Workout updateWorkout(@PathVariable String id, @Valid @RequestBody Workout workout) {
        return workoutService.updateWorkout(id, workout);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable String id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/member/{memberId}")
    public List<Workout> getWorkoutsByMember(@PathVariable String memberId) {
        return workoutService.getWorkoutsByMember(memberId);
    }

    @GetMapping("/type/{exerciseType}")
    public List<Workout> getWorkoutsByType(@PathVariable String exerciseType) {
        return workoutService.getWorkoutsByType(exerciseType);
    }
}
