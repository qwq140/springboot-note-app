# 노트 앱 서버
## Version
- Java 17
- Springboot 3.3.1

## 기록
### 스프링 시큐리티에서 던진 예외는 @ControllerAdvice에서 처리하지 못한다.
- @ControllerAdvice는 컨트롤러 계층에서 발생하는 예외를 처리한다.
- 스프링 시큐리티는 요청이 컨트롤러에 도착하기 전에 필터에서 예외를 발생시키기 때문이다.

### supabase db와 연동설정
- supabase 설정 정보 화면
![이미지](./img/스크린샷%202024-07-09%20124605.png)
- application.yml 설정
  - password는 supabase 프로젝트 생성시 만든 password