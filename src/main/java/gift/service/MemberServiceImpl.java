package gift.service;

import gift.dto.CreateMemberRequestDto;
import gift.dto.DeleteMemberRequestDto;
import gift.dto.JWTResponseDto;
import gift.dto.UpdateMemberPasswordRequestDto;
import gift.entity.Member;
import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final TokenService tokenService;

    public MemberServiceImpl(MemberRepository memberRepository, TokenService tokenService) {
        this.memberRepository = memberRepository;
        this.tokenService = tokenService;
    }

    @Override
    public JWTResponseDto createMember(CreateMemberRequestDto requestDto) {
        throwIfMemberFindByEmail(requestDto.email());
        Member newMember = new Member(requestDto.email(), requestDto.password(), "user");
        Member savedMember = memberRepository.save(newMember);
        String accessToken = tokenService.createAccessToken(savedMember);
        return new JWTResponseDto(accessToken);
    }

    @Override
    public JWTResponseDto loginMember(CreateMemberRequestDto requestDto) {
        Member find = findMemberByEmailOrElseThrow(requestDto.email());

        throwIfPasswordIncorrect(find, requestDto.password());

        String accessToken = tokenService.createAccessToken(find);
        return new JWTResponseDto(accessToken);
    }

    @Override
    @Transactional
    public void updateMemberPassword(UpdateMemberPasswordRequestDto requestDto) {
        Member find = findMemberByEmailOrElseThrow(requestDto.email());
        throwIfPasswordIncorrect(find, requestDto.oldPassword());
        find.setPassword(requestDto.newPassword());
    }

    @Override
    public void deleteMember(DeleteMemberRequestDto requestDto) {
        Member find = findMemberByEmailOrElseThrow(requestDto.email());

        throwIfPasswordIncorrect(find, requestDto.password());

        memberRepository.deleteById(find.getId());
    }

    private Member findMemberByEmailOrElseThrow(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NotRegisterd));
    }

    private void throwIfMemberFindByEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    throw new CustomException(ErrorCode.AlreadyRegistered);
                });
    }

    private void throwIfPasswordIncorrect(Member member, String password) {
        if (!member.getPassword().equals(password)) {
            throw new CustomException(ErrorCode.Unauthorized);
        }
    }
}
