


① 프로젝트 소개

주제와 관리하는 데이터

    title 영화제목
    director 감독
    genre 장르
    year 출시연도
    rating 평점
프로젝트 구조

    Layered Architecture을 적용하였으며, 
    controller: HTTP의 요청과 응답을 처리
    service: 예외처리및 비지니스 로직, DTO(JSON<->Java객체)변환
    rspository: 메모리를 기반으로 데이터 저장및 조회.
    domain/dto: 영화 데이터의 데이터 구조 및 클라이언트 요청 및 응답용 데이터구조. 
로컬 실행 방법

    Intellij에서 해당 폴더 찾아서 열기-> Week5MovieCrudApplication.java 실행 혹은
    터미널 에서 .\gradlew bootRun 입력-> 브라우저나 postman에서 링크(http://localhost:8080)접속
    -> 링크 뒷쪽에 상황에 맞는 URL 입력
API Endpoint 표
        
    | 기능 | Method | URL | 상태 코드 | 반환 타입 |
    | --- |   ---  | --- |    ---   |   ---   |
    | 영화 등록 | POST | `/api/movies` | 200 Ok | `MovieResponse` |
    | 전체 조회 | GET | `/api/movies` | 200 OK | `List<MovieResponse>` |
    | 단건 조회 | GET | `/api/movies/{id}` | 200 OK | `MovieResponse` |
    | 영화 수정 | PUT | `/api/movies/{id}` | 200 OK | `MovieResponse` |
    | 영화 삭제 | DELETE | `/api/movies/{id}` | 200 OK | `Void` |
    | 평점 필터링| GET | `/api/movies/rating/{minR}`| 200 OK | `List<MovieResponse>` |
요청·응답 JSON 예시
    
    요청(Post)
    {
    "title": "자바스크립트",
    "director": "Jang",
    "genre": "아무거나",
    "year": 2026,
    "rating": 10.0
    }

    {
    "id": 1
    "title": "자바스크립트",
    "director": "Jang",
    "genre": "아무거나",
    "year": 2026,
    "rating": 10.0
    }

GitHub Repository URL과 배포 URL

    https://github.com/2026-2-WebService/assign05-c01-22300644
    https://documenter.getpostman.com/view/58672842/2sBYHNY3cU

② 개발환경 및 Dependency

항목

작성 내용

    IDE: IntelliJ IDEA
    
    JDK: Java 17
    
    Spring Boot: 4.1.1
    
    Build Tool: Gradle 9.7.1
    
    데이터 저장 :Map (<Long, Movie>)
    
    배포 환경: postman(https://documenter.getpostman.com/view/58672842/2sBYHNY3cU)

    spring-boot-starter-web을 사용하였다. REST 서버를 구축하고 클라이언트 HTTP 요청을 처리하기위해
    이프로젝트에 필요했습니다.
    @RestController, @GetMapping, @PostMapping 등의 이노테이션을 이용해 URL 매핑을 구현하였고,
    @RequestBody로 JSON데이터를 자바 객체로 변환 할수있었습니다.
    ResponseStatusException를 사용하여 잘못된 입력이나 없는 ID를 입력했을때 오류 코드들을 제어 하였습니다.

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

    1.DTO/Domain  설계
    영화 정보를 담을 기본 데이터 구조와 클라이언트의 요청과 응답을 할때 필요한 데이터구조를 만들었습니다.
    Movie 클래스를 만들고, 클라이언트와 주고받을 데이터인 MovieRequest, MovieResponse는 Record로 만들었습니다.
    오류가 나지 않는것만 확인하여 이후 문제가 생겼을떄 수정했습니다.

    2. Repository 구현
    Map을 사용해 메모리에 영화를 저장하고 불러오는 역할을 구현했습니다.
    MovieRepository 인터페이스를 만들고, MemoryMovieRepository 클래스에 
    save, findAll,findById,update,deleteById 등의 5개의 메서드를 짰습니다.
    이번에도 오류가 나지 않는것만 확인하여 이후 코드를 작성할때 문제가 생기면 수정했습니다.

    3. Service 구현
    전체적인 실행 로직을 만들고,없는 영화를 찾으려고 할 때 404 에러를 뱉도록 예외 처리를 했습니다.
    MovieService 클래스를 만들고 save, findAll,findById,update,deleteById 등을 짰습니다. 
    findById,update,deleteById이 실행될때 아이디를 못 찾으면 ResponseStatusException(404)을 던지게 했습니다.
    오류가 나는지만 확인하고 오류가나면 수정했습니다.

    4.controller구현
    
    URL로 요청을 받아 앞에서 만든 기능들을 실행해주는 controller를 완성했습니다.
    MovieController 클래스를 만들고 @RestController를 달고,
    @PostMapping, @GetMapping, @Putmapping, @Deletemapping 도 달아주어 원하는 주소도 연결 시켜주었습니다.
    웹 브라우저 주소창에 직접 쳐서 실행이 되는지만 확인 해보고 기능들은 테스트 해보지 않았습니다.
    
    5. 추가 기능 구현 및 테스트, 전체적인 오류 수정

    추가적인 입력 오류들을 추가하고, 평점 필터링 기능을 추가했습니다. 그리고 테스트 해보며
    오류는 나지않지만 실행이 되지않는 기능들이나 전체적으로 연결이 잘 되어있는지 AI의 도움으로 
    전체적인 수정을 했습니다.
    MovieController에 잘못된 입력을 막는 if문을 추가했습니다. 
    service, Control, rrepository에 ratingCut연결과 주소를 추가.
    
    postman 을 활용해 배포및 테스트를 진행 했습니다. 잘못된 입력 및 추가기능 등 
    확인해봤습니다.
⑤ 기능 수정·확장

    A.잘못된 입력 처리 
    비어있거나 잘못된 데이터가 입력 되어도 실행이 종료되지않거나 들어가지 않게 하기위해서입니다.
    MovieService의 Post,Put등을 수정했습니다.
    평점과 개봉년도를 초과하여 넣었습니다. 
    400에러 코드와 제가 입력한 안내 문자가 같이 출력될것으로 예상했으나 400에러코드만 출력 되었습니다.
    
    B.평점 필터링 기능
    실제 사용할때 있을것 같은 기능을 구현해 보고 싶었고,
    낮은 평점의 영화를 원치않는 이용자가 있을것 같아서 구현해봤습니다.
    Controller,service,repository에 전부 Rating()메소드를 추가하였습니다.
    GET /api/movies/rating/8 를 url에 입력 하면 4.0 이상의 영화만 출력될것으로 예상하였고
    실제로 평점8이상의 영화만 출력 되었습니다.

⑥ 배포 과정 요약

    postman을 이용해 테스트및 수정을 마친후 postman의 Publish기능으로 주소를 만들었습니다.
    
    요청및 응답 확인:
    
⑦ Weekly Report
    
    1.이번과제까지 반복적으로  Layered Architecture를 구현해보며 확실하게 
    Controller-Service-Repository를 나눠 구현하는지 구조에대해 더욱 깊게 이해한것 같습니다.
    2.ResponseStatusException를 활용해 잘못된 입력을 받았을시에 올 처리에 대해 이해 할수 있었습니다.
    3. Response나 Request들을 사용해보며 { "title": "자바" } 같은 형태 바꿔 응답해주고
    입력을 받는 과정을 조금더 이해할수있었습니다.

    코드를 다 짜고 서버를 실행해 봤는데 오류가 발생하여 실행이 되지않았습니다.
    오류 코드를 검색해보니@Repositoy 어노테이션이 빠져있다는것을 알게되어 추가하였습니다.

    public List<Movie> ratingCut(float minR){
        List<Movie> ratingMovie=new ArrayList<>();
        for(Movie movie : store.values()){
            if(movie.getRating()>=minR){
                ratingMovie.add(movie);
            }
        }
        return ratingMovie;
    }
    추가 구현을 해본 평점 필터링 기능입니다. 우선 몇점이상을 필터링할지 minR을 매개변수로 받고,
    Movie 형식의 어레이 리스트를 생성합니다. for each문을 통해 LinkedHashMap 형태인 
    데이터들이 저장되어있는 store에서 Rating이 minR이상인 데이터들만 만들어둔 어레이리스트에
    저장 해준뒤 store의 데이터를 전부 확인 했다면 저장해둔 어레이 리스트를 반환합니다.
    이후 service로 넘어가서 response 메서드를 통해 응답에 맞는 형식으로 변환되어 control로 넘어가 응답으로 출력됩니다.
    질문: Controller를 구현할때 각 메서드들의 반환 타입과 해당 타입으로 반환 되는이유는? 
    등록,번호조회,수정은 MovieResponse로 반환되고 한가지의 Movie를 Response로 변환하여 반환하기 때문이고
    전체 조회 는 List<MovieResponse>로 반환되고 전체Movie타입의 데이터를Response로 변환하기 때문에 List를 사용.
    삭제는 반환할것이 없기 때문에 void타입입니다.
    어노테이션을 직접 사용해보며 편리성과 중요성을 다시한번 느낄 수 있었습니다.
    

    

