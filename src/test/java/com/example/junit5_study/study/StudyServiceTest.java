package com.example.junit5_study.study;

import com.example.junit5_study.domain.Member;
import com.example.junit5_study.domain.Study;
import com.example.junit5_study.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest  {

    /**
     * 밑의 인터페이스를 Mockito 를 사용하는 방법을 어노테이션으로 사용한 것이다.
     * @Mock 어노테이션을 사용하기 위해서는 class 위에서 @ExtendWith(MockitoExtension.class) 를 정의해 주어야 한다.
     */
    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    /**
     * void createStudyService(@Mock MemberSerivce memberSerivce, @Mock StudyRepository studyRepository)
     * 형식으로도 Mock 작성이 가능하다.
     */

    @Test
    void createStudyService() {
        /**
         * MemberService 와 StudyRepository 는 아무것도 구현되어 있지 않은 인터페이스 이다.
         * 이 인터페이스를 사용하기 위해서는 구현을 해주어야 하는데, 이 과정을 대신 해주는 것이 Mockito 이다.
         *
         *   MemberService memberService = Mockito.mock(MemberService.class);
         *   StudyRepository studyRepository = Mockito.mock(StudyRepository.class);
         */

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("keesun@email.com");

        // Stubbing
        /**
         * 해당 값일 때 어떠한 값을 반환해라 => Stubbing
         */
        // Mockito.when -> static 변수로 저장 하면 when 사용 가능
        // any -> ArgumentMatchers.any;
        when(memberService.findById(any())).thenReturn(Optional.of(member));

        Study study = new Study(10, "java");

        assertEquals("keesun@email.com", memberService.findById(1L).get().getEmail());
        assertEquals("keesun@email.com", memberService.findById(2L).get().getEmail());

        // 1일 경우 예외를 던진다.
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });

        // 2의 경우는 정상적으로 호출된다.
        memberService.validate(2L);

    }

    @Test
    void createStudyService2() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("keesun@email.com");

        // 처음 호출시에는 member 리턴
        // 두번째 호출시에는 예외
        // 세번째 호출시에는 empty
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());


        Optional<Member> byId = memberService.findById(1L);
        assertEquals("keesun@email.com", byId.get().getEmail());

        assertThrows(RuntimeException.class, () -> {
            memberService.findById(2L);
        });

        assertEquals(Optional.empty(), memberService.findById(3L));

    }
}