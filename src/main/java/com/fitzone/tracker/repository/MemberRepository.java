package com.fitzone.tracker.repository;

import com.fitzone.tracker.entity.Member;
import com.fitzone.tracker.entity.MembershipPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberRepository extends MongoRepository<Member, String> {

    boolean existsByEmail(String email);

    List<Member> findByNameContainingIgnoreCase(String name);

    List<Member> findByPlan(MembershipPlan plan);
}
