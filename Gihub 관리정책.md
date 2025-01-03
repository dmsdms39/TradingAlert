# f-lab-springboot-project-template

# branch


- master, release, develop 3단계 전략 사용
    1. master
        - 사용자에게 공개된 실사용 버전
    2. release
        - 릴리즈 가능 버전
            - QA 테스트
    3. develop
        - 개발용

## branch tag

- feat/대기능
- feat/대기능/소기능
- test

# commit
## 단위

- compile 성공 시에 commit 가능
- unit test (+integratoin test) 통과 후에 merge request 가능

## tag

|  | 용도 | 비고 |
| --- | --- | --- |
| **feat** | 기능 개발 | 새로운 기능 구현, 기존 기능 수정 등 |
| **bugfix** | 버그 수정 |  |
| **chore** | 사소한 변경사항 | 빌드 스크립트 수정 등 |
| **test** | 테스트 | 테스트 코드 추가, 수정, 삭제 등 |
| **refactor** | 리팩토링 |  |
| **docs** | 문서 작업 | 코드에 전혀 영향이 없는 문서 작업 |

## 메시지 형식

- [태그] 제목 + 세부 내용

  > [태그] 제목
    - 세부 내용
    - 세부 내용
    - ...
  > 제목으로 설명이 충분해서 작성할 세부 내용이 없는 경우 굳이 작성하지 않아도 됨
- 예시

  > [feat] 암호화된 배포 URL 적용
    - Controller에서 대화가 들어오는 API path 수정
    - UrlUtils를 이용해 project id를 구하는 로직 추가
    - 관련 테스트 코드 수정


# MR 및 코드리뷰

---

## 프로세스

1. 작업하던 branch의 개발 완료
2. 테스트 코드 작성 및 통과 완료
3. develop 브랜치에 MR (Merge Request)
4. 코드 리뷰
    1. 오프라인 회의로 진행.
    2. 회의실에서 코드를 보여주며, 반영사항 브리핑 및 코드 관련 팀원 피드백을 받는다.
5. 피드백 반영
6. Merge 완료

[코드리뷰가 쏘아올린 작은공 | 우아한형제들 기술블로그](https://techblog.woowahan.com/2712/)

## MR 작성

- `default` template 선택하여 작성

    - 참고 자료
        - template 등록 방법: [https://velog.io/@ss-won/Git-GitLab-Issue-MR-Template-만들기](https://velog.io/@ss-won/Git-GitLab-Issue-MR-Template-%EB%A7%8C%EB%93%A4%EA%B8%B0)

- assignee를 지정하여 MR 등록

    - 지정된 assignee가 해당 MR 건의 수정사항을 확인 후, MR 작성자가 아닌 assignee가 직접 merge 버튼을 눌러 코드가 반영되도록 한다.
- 머지된 이후 origin에서 해당 브랜치가 삭제되기를 원하는 경우
    - 대부분의 브랜치에서 develop에 머지하는 경우 체크 하는 것이 좋다.
    - 아래와 같이 체크하면 된다.


