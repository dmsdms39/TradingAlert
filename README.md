# f-lab-springboot-project-template

# branch

---

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

---

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
  
    