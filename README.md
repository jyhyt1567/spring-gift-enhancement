# spring-gift-enhancement

# step 1
- repository의 Jdbc Client 기반 코드를 Jpa로 리팩토링
  - 기존 도메인 객체를 엔티티로 변경
  - 기존 Jdbc Client 로 구현된 repository 구현체 삭제
  - repository 인터페이스를 JpaRepository를 상속하도록 변경
  - service 레이어의 코드 변경한 Jpa 코드와 호환되도록 변경
  - 테스트 코드 작성

# step 1 피드백 반영
- 주석 제거
- 주생성자 활용 (생성자 체이닝을 통한 코드 중복 최소화)
- 엔티티 요구사항 충족 (not null, unique)
- 도메인 클래스에서 dto 변환 로직 제거
- display name을 통해 어떤 테스트인지 표현
- 엔티티 클래스 파라미터 없는 생성자 접근 제한자 수정