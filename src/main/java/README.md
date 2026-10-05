


① 프로젝트 소개

주제와 관리하는 데이터

    title 영화제목
    director 감독
    genre 장르
    year 출시연도
    rating 평점
프로젝트 구조
로컬 실행 방법
API Endpoint 표
요청·응답 JSON 예시
GitHub Repository URL과 배포 URL
② 개발환경 및 Dependency

항목

작성 내용

IDE

사용한 IntelliJ IDEA

JDK

실제 사용한 버전

Spring Boot

프로젝트에서 사용한 버전

Build Tool

Gradle 및 사용한 버전

데이터 저장

사용한 Java Collection

배포 환경

수업에서 사용한 배포 방식(배포 URL)

사용한 Dependency의 이름과 프로젝트에서 필요한 이유도 작성합니다. build.gradle 전체를 복사하는 대신 직접 선택하거나 추가한 Dependency를 설명합니다.

③ Solution 분석
    BookRequest, Book, BookResponse의 역할차이는 어떻게 되나요?

        Request는 controller에서 매개변수로 자주 사용되며, 클라이언트가 입력한 내용(JSON)이 
        들어오게된다.
    
        Book은 자바에서 데이터들을 어떤식으로 다룰지 정리해둔 객체 로 service나 repository에서 사용
    
        Response는 controller 반환타입으로 사용되고, 다시 클라이언트에게 보내줄때 포장하기위해 사용된다.

    Controller → Service → Repository → Memory의 요청 처리 흐름은어떻게 되나요?
       
        값이 Controller로 들어간뒤 이후에 Service 입력받은 값들을 이용해 Repository 기능들을 불러오고 다룬다.  
        예시)@PostMapping ...<BookResponse> create(@RequestBody ...)-> bookService.create(request)
        ->public BookResponse create(BookRequest r)-> repository.save(new Book(....)) ->Book save(Book book) { ... }

    새 데이터의 ID가 생성되는 위치는 어떻게 되나요?
        memoryrepository의 save메서드를 살펴보면 setId를 할때+sequence를 통해 새로운 ID를 만들어주고
        Map<Long, Book> 타입인 store변수에 put을 이용해 저장한다.

    존재하지 않는 ID에 대해 404가 반환되는 과정은 어떻게 되나요?
        BookService에서 findBook메서드를 보면  return //(반환) repository.findById(id).
        orElseThrow//(id를 통해 findById를 실행 해보고 null이 반환된다면)(() 
        -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not found: "+id));
        //(404 NOT_FOUND와 "Book not found: "+id를 반환)
        
    Domain 객체를 Response DTO로 변환하는 과정은 어떻게 되나요?
        
         service의 BookResponse toResponse(Book b)를 보면 Book 을 매개 변수로 받아서 
        new BookResponse(...)로 응답용 DTO객체를 만든다.

④ 개발 과정 요약

프로젝트를 만든 과정을 5단계 내외로 순서대로 정리합니다. 각 단계에서 다음 내용을 간단히 설명합니다.

무엇을 만들거나 변경했는가?
어느 클래스·메서드를 작성했는가?
실행 또는 테스트로 어떻게 확인했는가?
⑤ 기능 수정·확장

STEP 5의 A와 B에 대해 다음 내용을 작성합니다.

기능을 추가한 이유
수정한 클래스와 메서드
테스트에 사용한 요청과 예상 결과
실제 응답 결과
⑥ 배포 과정 요약

자신이 수행한 빌드 및 배포 순서
배포를 위해 추가하거나 수정한 파일·설정
배포 중 발생한 문제와 해결 방법
배포 URL로 확인한 요청과 응답
⑦ Weekly Report

Key Learning: 직접 구현하며 이해한 내용 3가지
Problem & Solution: 개발 또는 배포 중 겪은 문제 1개와 해결 과정
Code Review: 자신이 작성한 중요한 메서드 1개와 동작 설명
AI Usage: AI에 질문한 내용, 참고한 답변, 직접 확인·수정한 부분
Reflection: 더 공부하고 싶은 내용 또는 궁금한 점

    

