package com.fitzone.tracker.controller;

import com.fitzone.tracker.dto.MemberSummary;
import com.fitzone.tracker.entity.Member;
import com.fitzone.tracker.entity.MembershipPlan;
import com.fitzone.tracker.service.MemberService;
import com.fitzone.tracker.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final WorkoutService workoutService;

    public MemberController(MemberService memberService, WorkoutService workoutService) {
        this.memberService = memberService;
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(member));
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable String id) {
        return memberService.getMemberById(id);
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable String id, @Valid @RequestBody Member member) {
        return memberService.updateMember(id, member);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable String id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Member> searchByName(@RequestParam String name) {
        return memberService.searchByName(name);
    }

    @GetMapping("/plan/{plan}")
    public List<Member> getByPlan(@PathVariable MembershipPlan plan) {
        return memberService.getByPlan(plan);
    }

    @GetMapping("/{id}/summary")
    public MemberSummary getSummary(@PathVariable String id) {
        return workoutService.getMemberSummary(id);
    }
}
