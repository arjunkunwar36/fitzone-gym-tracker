package com.fitzone.tracker.repository;

import com.fitzone.tracker.entity.Workout;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WorkoutRepository extends MongoRepository<Workout, String> {

    List<Workout> findByMemberId(String memberId);

    List<Workout> findByExerciseTypeIgnoreCase(String exerciseType);

    void deleteByMemberId(String memberId);
}
