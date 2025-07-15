# spring-gift-enhancement

# step 1
- repository의 Jdbc Client 기반 코드를 Jpa로 리팩토링
  - 기존 도메인 객체를 엔티티로 변경
  - 기존 Jdbc Client 로 구현된 repository 구현체 삭제
  - repository 인터페이스를 JpaRepository를 상속하도록 변경
  - service 레이어의 코드 변경한 Jpa 코드와 호환되도록 변경
  - 테스트 코드 작성