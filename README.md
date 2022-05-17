# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* BufferedReader를 사용하여 InputStream으로 들어온 값을 라인 별로 읽을 수 있다.
* String 클래스의 split 메서드는 토큰을 만들 때 여전히 유용하게 사용할 수 있었다.
* 응답을 줄 때 byte[] 를 사용하여 response body를 생성해준다.
  * `index.html`과 같은 특정 파일을 전달해줄 때에도 byte[]로 변환하여 전달한다.
* `Controller`라는 클래스를 만들어서 `index.html`을 리턴할 수 있도록 코드를 작성 했는데, 다양한 요청을 처리할 수 있는 코드를 작성하는 방법은 무엇일까.

### 요구사항 2 - get 방식으로 회원가입
- 쿼리 파라미터에 포함된 문자열을 파싱하고 User 클래스에 저장했다.

### 요구사항 3 - post 방식으로 회원가입
- POST 방식은 HTTP 본문에 회원가입에 필요한 정보가 포함되는데, 이 정보를 읽어오는 과정이 쉽지 않았다.
- HTTP 본문은 헤더가 모두 나온 뒤 공백 라인 다음부터 시작되는데, `Content-Length`를 통해 본문의 길이를 가져와 제공되는 `IOUtils`의 `readData`를 이용해야만 했다.

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 