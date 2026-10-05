STEP 1. Solution 분석

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
        new BookResponse(...)로 새로운 객체를 만든다.
    

