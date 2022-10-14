# N + 1 문제 해결하기

이전에 N + 1 문제가 무엇이고 언제 발생하는지에 대해서 정리해봤습니다. 다시 한 번 복습 차원에서 정리해보면, N + 1 문제는 `1번의 쿼리로 도출된 결과(N개)`와 연관된 엔티티를 추가적으로 조회하기 위해 N번의 쿼리가 추가적으로 수행되는 문제였습니다.

그러면 이 문제를 해결하는 가장 이상적인 방법이 무엇인지 생각해보면 `1번의 쿼리를 수행할 때 그와 연관된 엔티티도 바로 가져오면` 좋을 거 같습니다. 그리고 이를 해결할 수 있는 방법은 JPQL의 문법인 `fetch join`을 사용하는 방법과 Spring Data JPA에서 제공하는 `@EntityGraph`를 사용하는 방법이 있습니다.

### fetch join
Spring Data JPA에서 JPQL을 작성할 수 있는 @Query와 fetch join을 사용하여 이 문제를 해결해보도록 하겠습니다. 

```java
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m join fetch m.team")
    List<Member> findAllUsingFetchJoin();
}
```

위 코드처럼 직접 정의한 메서드 위에 @Query 어노테이션을 붙인 후 JPQL을 작성하면 됩니다. JPQL에 대한 설명은 생략하도록 하며, 여기서 유심히 봐야 할 부분은 `join fetch` 입니다. 페치 조인은 SQL의 조인 종류가 아니며, JPQL에서 성능 최적화를 위해 제공하는 기능 중 하나입니다. 

그래서 위처럼 정의한 메서드를 통해 Member를 조회하고 Member와 연관된 Team을 조회해보면 다음과 같습니다.

```java
@Test
@DisplayName("fetch join을 사용하여 멤버 조회")
void findAllMemberUsingFetchJoinTest() {
    List<Member> findMembers = memberRepository.findAllUsingFetchJoin();

    findMembers.forEach(member -> {
        System.out.println(member.getTeam().getClass());
    });

    findMembers.forEach(member -> {
        System.out.println(member.getTeam().getName());
    });
}
```

이렇게 N + 1 문제를 확인했던 코드와 똑같이 테스트 코드를 작성하여 결과를 확인해보면 다음과 같습니다.

![N + 1 문제](/TIL/Spring/JPA/img/N%20%2B%201%20%EB%AC%B8%EC%A0%9C%20%ED%95%B4%EA%B2%B0(fetch%20join).png)

맨 처음 빨간색 상자는 모든 Member를 조회하기 위한 쿼리입니다.   
두 번째 상자는 조회한 Member와 연관 관계를 가지고 있는 Team이 어떤 클래스 타입을 가지고 있는지 출력을 한 것인데 여기서 이전에 N + 1 문제에서 다뤘던 타입과 다른 것을 확인할 수 있습니다. 분명 fetch join을 사용하지 않은 상태에서는 `Proxy 타입의 클래스`가 나타났지만 이번에는 우리가 직접 정의한 Domain Layer의 `Team 클래스`임을 확인할 수 있습니다. 그 말인 즉슨 가짜 객체가 아닌 실제 객체이며 값이 정상적으로 들어있는 객체라고 유추할 수 있습니다.   
세 번째 상자는 조회한 모든 Member가 속해있는 Team 이름을 출력하는 구문입니다. 별도의 쿼리 없이 Team 이름을 출력하고 있음을 확인할 수 있습니다.   

fetch join 을 사용함으로써 1 + 3의 쿼리가 1번의 쿼리로 줄어든 것을 확인할 수 있습니다.

### @EntityGraph
그럼 이번에는 다른 방법인 @EntityGraph를 사용해보도록 하겠습니다. @EntityGraph는 Spring Data JPA에서 기본적으로 제공하는 findAll(), findById() 등과 같은 메서드에 사용할 수 있으며, @Query을 사용하여 직접 작성한 JPQL에서도 사용할 수 있습니다. 방식은 다음과 같습니다.

```java
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m join fetch m.team")
    List<Member> findAllUsingFetchJoin();

    @EntityGraph(attributePaths = "team")
    @Query("select m from Member m")
    List<Member> findAllUsingJPQLAndEntityGraph();

    @Override
    @EntityGraph(attributePaths = "team")
    List<Member> findAll();
}
```

위 처럼 @EntityGraph + @Query(JPQL) 의 조합을 통해 N + 1 문제를 해결할 수 있으며, 기존에 Spring Data JPA에서 제공하는 findAll 메서드를 오버라이드 한 이후에 @EntityGraph을 사용하여 해결할 수 있습니다.

@EntityGraph는 attributePaths에 우리가 조회하려는 엔티티와 연관된 엔티티의 변수명을 적어서 사용하면 됩니다. 또한, 한 엔티티에 여러 개의 연관 관계가 존재할 수 있고, 조회 이후에 application layer에서 활용하는 엔티티는 특정 상황에 따라 다르기 때문에 attributePaths는 배열 형태로 인자를 넘겨줄 수 있습니다. 

![N + 1 문제](/TIL/Spring/JPA/img/attributePaths.png)

그렇기 때문에 요구사항 및 사용하는 API에 따라서 다양하게 연관 관계를 찾아오면 됩니다.

### 주의할 점
생각하고 가면 좋을 만한 부분이 있어서 그냥 적어보도록 하겠습니다.

1. Kotlin 에서는 String[] 과 같이 배열 형식으로 인자를 넘겨줘야 하는 곳에서는 Java와 다르게 인자가 한 개 일지라도 `[]`를 사용하여 값을 넘겨줘야 합니다.

2. @EntityGraph가 성능을 향상시키고 N + 1 문제를 해결하지만 상황에 따라 적절하게 사용할 필요는 있습니다. 단순히 Member 만을 조회하는 API에서 굳이 fetch join 혹은 @EntityGraph를 사용하여 Team을 조회할 필요는 없습니다. 또한 다른 상황을 고려해보면 findAll()과 같이 모든 엔티티를 조회(물론 페이징 처리하여 대량의 데이터를 application으로 긁어오는 경우는 없겠지만)하는 메서드에 연관된 엔티티를 같이 조회하게 되면 application의 메모리에 과부하가 발생할 수 있고 이는 장애로 이어질 것 입니다. 그렇기 때문에 항상 생각하고 유념하면서 개발을 해야될 거 같습니다.
