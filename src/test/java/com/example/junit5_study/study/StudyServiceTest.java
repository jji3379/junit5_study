package com.example.junit5_study.study;

import com.example.junit5_study.domain.Member;
import com.example.junit5_study.domain.Study;
import com.example.junit5_study.member.MemberService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
    static Member member;
    static Study study;

    static StudyService studyService;

    /**
     * BeforeAll 을 사용하는 메소드는 static 으로 정의해 주어야 한다.
     */
    @BeforeAll
    @DisplayName("기본 객체 세팅")
    static void objectSetting(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
        studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        member = new Member();
        member.setId(1L);
        member.setEmail("keesun@email.com");

        study = new Study(10, "java");
    }

    @Test
    @DisplayName("Mock Stubbing")
    void createStudyService() {
        /**
         * MemberService 와 StudyRepository 는 아무것도 구현되어 있지 않은 인터페이스 이다.
         * 이 인터페이스를 사용하기 위해서는 구현을 해주어야 하는데, 이 과정을 대신 해주는 것이 Mockito 이다.
         *
         *   MemberService memberService = Mockito.mock(MemberService.class);
         *   StudyRepository studyRepository = Mockito.mock(StudyRepository.class);
         */

        // Stubbing
        /**
         * 해당 값일 때 어떠한 값을 반환해라 => Stubbing
         */
        // Mockito.when -> static 변수로 저장 하면 when 사용 가능
        // any -> ArgumentMatchers.any;
        when(memberService.findById(any())).thenReturn(Optional.of(member));

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
    @DisplayName("각 호출마다 리턴 다르게 및 예외 던지기")
    void createStudyService2() {
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

    @Test
    @DisplayName("Stubbing Quiz")
    void StubbingQuiz() {
        // TODO memberService 객체에 findById 메소드를 1L 값으로 호출하면 member 객체를 리턴하도록 Stubbing
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Study study = new Study(10, "java");

        // TODO studyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 Stubbing
        when(studyRepository.save(study)).thenReturn(study);
    }

    @Test
    @DisplayName("호출 수, 순서 검증")
    void verifyAndOrder() {
//        studyService.createNewStudy(1L, study);
//        verify(memberService, times(1)).notify(study);
        // 어떤한 인터렉션도 일어나면 안 된다.
//        verifyNoInteractions(memberService);
//        verify(memberService, times(1)).notify(member);
//        InOrder inOrder = inOrder(memberService);
//        inOrder.verify(memberService).notify(study);
//        inOrder.verify(memberService).notify(member);
//        verify(memberService, never()).validate(any());
    }
}