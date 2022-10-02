package com.example.junit5_study.study;

import com.example.junit5_study.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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
    }
}