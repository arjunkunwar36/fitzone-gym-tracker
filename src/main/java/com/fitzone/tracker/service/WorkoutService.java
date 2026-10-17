package com.fitzone.tracker.service;

import com.fitzone.tracker.dto.MemberSummary;
import com.fitzone.tracker.entity.Member;
import com.fitzone.tracker.entity.Workout;
import com.fitzone.tracker.exception.ResourceNotFoundException;
import com.fitzone.tracker.repository.MemberRepository;
import com.fitzone.tracker.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final MemberRepository memberRepository;

    public WorkoutService(WorkoutRepository workoutRepository, MemberRepository memberRepository) {
        this.workoutRepository = workoutRepository;
        this.memberRepository = memberRepository;
    }

    public Workout logWorkout(Workout workout) {
        ensureMemberExists(workout.getMemberId());
        workout.setId(null);
        if (workout.getWorkoutDate() == null) {
            workout.setWorkoutDate(LocalDate.now());
        }
        return workoutRepository.save(workout);
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Workout getWorkoutById(String id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id " + id));
    }

    public Workout updateWorkout(String id, Workout updated) {
        Workout existing = getWorkoutById(id);
        ensureMemberExists(updated.getMemberId());
        existing.setMemberId(updated.getMemberId());
        existing.setExerciseType(updated.getExerciseType());
        existing.setDurationMinutes(updated.getDurationMinutes());
        existing.setCaloriesBurned(updated.getCaloriesBurned());
        existing.setNotes(updated.getNotes());
        if (updated.getWorkoutDate() != null) {
            existing.setWorkoutDate(updated.getWorkoutDate());
        }
        return workoutRepository.save(existing);
    }

    public void deleteWorkout(String id) {
        Workout existing = getWorkoutById(id);
        workoutRepository.delete(existing);
    }

    public List<Workout> getWorkoutsByMember(String memberId) {
        ensureMemberExists(memberId);
        return workoutRepository.findByMemberId(memberId);
    }

    public List<Workout> getWorkoutsByType(String exerciseType) {
        return workoutRepository.findByExerciseTypeIgnoreCase(exerciseType);
    }

    public MemberSummary getMemberSummary(String memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id " + memberId));
        List<Workout> workouts = workoutRepository.findByMemberId(memberId);

        int totalMinutes = workouts.stream().mapToInt(Workout::getDurationMinutes).sum();
        int totalCalories = workouts.stream().mapToInt(Workout::getCaloriesBurned).sum();
        double average = workouts.isEmpty() ? 0.0
                : Math.round((double) totalMinutes / workouts.size() * 100.0) / 100.0;

        return new MemberSummary(member.getId(), member.getName(),
                workouts.size(), totalMinutes, totalCalories, average);
    }

    private void ensureMemberExists(String memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found with id " + memberId);
        }
    }
}
