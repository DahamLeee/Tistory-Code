# N + 1 문제

N + 1 문제는 `1번의 쿼리를 수행`하고, 수행된 쿼리의 결과 값이 N개라고 가정했을 때 결과의 엔티티와 연관 관계를 가지는 `엔티티를 찾기 위해 N번의 쿼리가 추가적으로 발생`하는 문제입니다. 이번 정리에서는 N + 1이 정확히 무엇이고 어떤 상황에서 발생하는지 알아보고 다음에 해결 방법에 대해서 정리하도록 하겠습니다.

역시 개발자는 코드를 통해 이해하는 것이 더 빠르기 때문에 코드로 예시를 들어 설명해보도록 하겠습니다.

``` java
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
```

``` java
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
```

![N + 1 문제](/TIL/Spring/JPA/img/N%2B1%20%EB%AC%B8%EC%A0%9C.png)

위와 같이 `Member`, `Team` 엔티티를 사용하여 서로 연관 관계를 맺은 이후에 모든 멤버를 조회하는 쿼리를 날렸다고 가정해보겠습니다. Java 에서는 Spring Data JPA에서 제공하는 findAll() 메서드를 호출한겁니다! (ex. memberRepository.findAll()) 

그러면 사진 처럼 모든 멤버가 조회가 될 것이고 Java 에서는 List`<`Member`>` findMembers의 형태로 결과 값을 활용할 수 있습니다!    

그 이후에 찾은 멤버 별로 어떤 팀에 속하는지 확인해보고자 합니다. 그래서 찾은 멤버(List`<`Member`>` findMembers)에 대해서 loop를 돌면서 연관된 Team에 접근하여 이름을 출력해보도록 하겠습니다.

<br>

``` java
List<Member> findMembers = memberRepository.findAll();
findMembers.forEach(member -> {
    System.out.println(member.getTeam().getName()); // TeamName 출력!
})
```

위처럼 찾은 멤버를 통해 Team 엔티티의 값인 Name에 접근하면 추가적인 쿼리가 발생합니다. 그리고 추가적인 쿼리의 수는 `8`입니다. 위 사진을 보면 `8`명의 멤버는 소속되어 있는 팀이 모두 다 다릅니다. (TEAM_ID는 Team을 식별할 수 있는 PK이며, Member Table은 이를 FK로 가지고 있습니다.) 그렇기 때문에 loop를 돌면서 매번 새로운 Team Entity에 대한 정보를 알아와야 하기 때문에 추가적인 쿼리가 발생하는 것 입니다. 그리고 이는 N + 1 문제에서 나타날 수 있는 `최악의 상황입니다.`   

```
모든 멤버를 조회하는 쿼리 1회 => 결과 List<Member> findMembers
쿼리를 통해 도출된 결과와 연관된 쿼리 수행 N회 => 각각의 Member가 속한 Team을 조회 
```

좀 더 자세하게 설명해보도록 하겠습니다.

그러면 왜 추가적인 쿼리가 발생하는 것일까? memberRepository.findAll()을 통해 Member List를 가지고 오게 되면 Member 엔티티에 관한 값은 정상적으로 가져오게 됩니다. 그러나 Member와 연관 관계를 맺고 있던 Team 엔티티는 `Proxy 객체`로 가지고 옵니다.   

``` 
Proxy 객체는 `빈껍데기` 혹은 `가짜 객체`를 의미합니다. 
```

Team 엔티티는 Proxy 객체이기 때문에 실제 Team 객체가 가지고 있어야 할 값이 채워져 있지 않은 상태입니다. 이때, 우리가 Proxy 객체인 Team 엔티티의 값에 접근하게 될 경우 추가적인 쿼리가 발생하는 것 입니다. 왜냐하면 Proxy 객체는 우리가 원하는 값을 가지고 있지 않기 때문에 그 값을 가지고 있는 DB에 물어보는(쿼리 발생) 작업을 수행함으로써 우리가 원하는 값을 찾아야하기 때문입니다.

<br>

> 의식하는, 생각하는 코딩을 하자!   
> 우리는 JPA를 사용하여 데이터를 조회하고 값에 접근하고 application에서 비지니스 로직을 수행하는 다양한 작업을 할 것입니다. 그리고 우리가 찾은 Entity에 연관된 객체가 Proxy 객체인지 실제 객체인지 직관적으로 확인하는 방법이 없습니다. 코딩을 하다 보면 놓칠 수 있는 부분이고 무수히 많은 연관 관계가 존재하는 프로젝트에서 무심코 접근하여 요구사항을 해결할 수도 있습니다. 그래서 JPA는 조금 더 신중하게 개발해야 하고 수행되는 쿼리가 어떤지 확인하면서 개발해야 합니다.

<br>

> 왜 최악의 경우인가?    
> 만약 Member가 1개의 팀에만 속한다고 가정해보겠습니다. 그렇다면 첫 번째 Member에 해당하는 Team 객체는 Proxy 객체이기 때문에 이때는 어쩔 수 없이 쿼리가 발생합니다. 그러나 두 번째 Member에 해당하는 Team 객체를 조회할 때는 추가적인 쿼리가 발생하지 않습니다. 왜냐하면 우리는 이미 두 번째 Member에 해당하는 Team 객체를 이미 조회했기 때문입니다. 그렇기 때문에 이 경우는 1 + 1로 쿼리가 많이 발생하지는 않습니다.

<br>

지금까지 N + 1 문제가 어떤 것인지 정리해봤습니다. 여기서 가장 좋은 방법은 1번의 쿼리로 우리가 원하는 값을 한 번에 긁어오는 것이라 생각할 수 있을 거 같습니다. 그리고 그 방법을 다음에 정리하도록 하겠습니다.

## 추가적인 자료 (Proxy 객체)
![N + 1 문제](/TIL/Spring/JPA/img/Proxy%20%EA%B0%9D%EC%B2%B4.png)

5명의 Member, 3개의 Team이 존재할 때 findAll() 이후에 찾은 Member가 보유한 Team의 클래스를 확인해보면 위처럼 HibernateProxy가 붙어있음을 확인할 수 있습니다.

## 추가적인 자료 (추가적인 쿼리 수행)
![N + 1 문제](/TIL/Spring/JPA/img/%EC%BF%BC%EB%A6%AC%20%EC%88%98%ED%96%89%20%EA%B2%B0%EA%B3%BC.png)

<br>

```java
findMembers.forEach(member -> {
    System.out.println(member.getTeam().getName());
});
```

위 사진처럼 Member가 속한 Team의 이름을 출력할 경우 추가적인 쿼리가 수행되는 것을 확인할 수 있습니다. 사진을 자세히 확인해보면 team1은 2번 출력되었지만 team1을 찾기 위한 쿼리는 한 번 수행된 것을 확인할 수 있습니다. 이는 처음 team1을 조회할 때는 값이 없기 때문에 조회한 것이고, 두 번째 team1을 조회할 때는 이미 team1의 값을 알고 있기 때문에 추가적인 쿼리 없이 이전에 구한 값을 활용한 것 입니다.