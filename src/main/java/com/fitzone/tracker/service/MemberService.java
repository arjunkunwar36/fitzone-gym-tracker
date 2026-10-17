package com.fitzone.tracker.service;

import com.fitzone.tracker.entity.Member;
import com.fitzone.tracker.entity.MembershipPlan;
import com.fitzone.tracker.exception.DuplicateResourceException;
import com.fitzone.tracker.exception.ResourceNotFoundException;
import com.fitzone.tracker.repository.MemberRepository;
import com.fitzone.tracker.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final WorkoutRepository workoutRepository;

    public MemberService(MemberRepository memberRepository, WorkoutRepository workoutRepository) {
        this.memberRepository = memberRepository;
        this.workoutRepository = workoutRepository;
    }

    public Member createMember(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new DuplicateResourceException("A member with email " + member.getEmail() + " already exists");
        }
        member.setId(null);
        if (member.getJoinDate() == null) {
            member.setJoinDate(LocalDate.now());
        }
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id " + id));
    }

    public Member updateMember(String id, Member updated) {
        Member existing = getMemberById(id);
        if (!existing.getEmail().equalsIgnoreCase(updated.getEmail())
                && memberRepository.existsByEmail(updated.getEmail())) {
            throw new DuplicateResourceException("A member with email " + updated.getEmail() + " already exists");
        }
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setAge(updated.getAge());
        existing.setPlan(updated.getPlan());
        existing.setActive(updated.isActive());
        if (updated.getJoinDate() != null) {
            existing.setJoinDate(updated.getJoinDate());
        }
        return memberRepository.save(existing);
    }

    public void deleteMember(String id) {
        Member existing = getMemberById(id);
        workoutRepository.deleteByMemberId(existing.getId());
        memberRepository.delete(existing);
    }

    public List<Member> searchByName(String name) {
        return memberRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Member> getByPlan(MembershipPlan plan) {
        return memberRepository.findByPlan(plan);
    }
}
