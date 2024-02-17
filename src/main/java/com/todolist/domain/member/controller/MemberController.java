package com.todolist.domain.member.controller;

import com.todolist.domain.member.dto.MemberDto;
import com.todolist.domain.member.entity.Member;
import com.todolist.domain.member.mapper.MemberMapper;
import com.todolist.domain.member.service.MemberService;
import com.todolist.global.dto.MultiResponseDto;
import com.todolist.global.dto.SingleResponseDto;
import com.todolist.global.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@Validated
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/v1/members";
    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @RequestMapping(value = "/v1/members", method = RequestMethod.POST)
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
        Member member = mapper.memberPostDtoToMember(requestBody);

        Member createdMember = memberService.createMember(member);
        URI location = UriCreator.creatreUri(MEMBER_DEFAULT_URL, createdMember.getMemberId());

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/v1/members/{member-id}", method = RequestMethod.PATCH)
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberDto.Patch requestBody) {
        requestBody.setMemberId(memberId);
        Member member = mapper.memberPatchToMember(requestBody);
        Member updatedMember = memberService.updateMember(member);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponse(updatedMember)), HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{member-id}", method = RequestMethod.GET)
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {
        Member member = memberService.findMember(memberId);

        return new ResponseEntity(
                new SingleResponseDto<>(
                        mapper.memberToMemberResponse(member)), HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{member=id}", method = RequestMethod.GET)
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Member> pagerMembers = memberService.findMembers(page - 1, size);
        List<Member> members = pagerMembers.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.membersToMemberResponses(members),
                        pagerMembers), HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/members/{member-id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
