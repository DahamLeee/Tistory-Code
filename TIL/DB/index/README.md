# Index
Index의 사전적 의미는 너무나 잘 알고 있기 때문에 생략하도록 하겠습니다!

## Why Index?
간단명료하게 설명하면 `원하는 값`을 `빠르게 찾기 위해서` 사용한다!

조금 더 길게 설명하면 다음과 같습니다.

> 인덱스란 SQL 명령문의 `처리 속도를 향상`시키기 위해서 컬럼에 대해서 생성하는 오라클(데이터베이스) `객체`입니다. 
<br> - Oracle SQL & PL/SQL 서적 中 -

## Features
인덱스의 특징을 살펴보면 다음과 같습니다.
- 인덱스는 항상 최신의 `정렬 상태`를 유지한다.
- 인덱스도 하나의 데이터베이스 `객체`이다.
- 데이터베이스 크기의 약 10% 정도의 `저장 공간`을 필요로 한다.

## Pros and Cons
대다수의 기술이 장단점을 가지는 거 처럼 인덱스도 장단점이 존재합니다!

<b>장점</b>
- 검색 속도가 `빨라진다.`
- 시스템에 걸리는 `부하를 줄여서` 시스템 `전체 성능을 향상`시킨다.

<b>단점</b>
- 인덱스를 위한 추가적인 `공간`이 필요하다.
- 인덱스를 생성하는 데 `시간`이 걸린다.
- 데이터의 변경 작업(INSERT/UPDATE/DELETE)이 자주 일어날 경우에는 `오히려 성능이 저하`된다.

<br>

# B-Tree
`왜` 인덱스를 사용하는지, 어떤 `특징`을 가지고 있고 무슨 `장단점`이 있는지 간단하게 짚어봤습니다!

여기서 조금 더 깊게 들어가서 인덱스를 사용하면 값을 어떻게 찾는지 내부 동작 방식에 대해 알아보도록 하겠습니다.

## B-Tree? B*Tree? B+Tree?
인덱스가 적용된 컬럼을 통해 값을 찾는 내부 동작 과정을 바로 정리할까 했지만, 이곳저곳 자료를 탐색하면서 B-Tree, B*Tree, B+Tree의 차이가 무엇인지 물어보는 곳이 있다고 했고 저 자신도 이 차이점에 대해 알지 못하고 있기 때문에 정리하고 내부 동작 과정을 정리하도록 하겠습니다.

먼저, B-Trees는 이진트리에서 발전되어 모든 리프 노드들이 같은 레벨을 가질 수 있도록 자동으로 `벨런스를 맞추는 *균형 이진 트리`의 확장판입니다.

그렇기 때문에 B-Tree를 쉽게 이해하기 위해서는 이진 트리를 이해하고 있는 것이 좋지만 내용이 길어질 거 같기 때문에 여기서는 위에 하이라이팅 되어 있는 구문 정도로 정리하도록 하겠습니다.

### B-Tree
1. 이진 트리와는 다르게 `하나의 노드에 많은 정보`를 가질 수 있습니다.
2. 하나의 노드에 여러 정보를 담을 수 있게 되면서 `이진 트리보다 훨씬 많은 데이터를 더 효율적으로 저장소에 담을 수 있게 되었습니다.`
3. 위에서 말한거처럼 B-Tree는 균형 이진 트리의 확장판입니다. 따라서 B-Tree도 균형을 유지하고, 아무리 최악의 경우라도 O(logN)의 검색 성능을 보여줍니다.

그래서 특징을 간결하게 정리해보면 다음과 같습니다.
- 각 노드의 `자료는 정렬`되어 있습니다.
- 자료는 중복되지 않습니다.
- 모든 leaf node는 같은 레벨에 있습니다.
- root node는 자신이 leaf node가 되지 않는 이상 적어도 2개 이상의 자식을 가집니다.
- root node가 아닌 노드들은 적어도 M/2개의 자식 노드를 가지고 있습니다. (최대 M개)

### B*Tree
1. B-Tree의 단점 중 하나는 구조를 유지하기 위해서 추가적인 연산이 수행되거나 새로운 노드가 생성된다는 것입니다.
2. 노드의 추가적인 생성과 추가적인 연산의 최소화를 위해서 B-Tree에서 몇 가지 규칙이 추가된 B*Tree가 등장하게 되었습니다.

### B+Tree
B-Tree는 탐색을 위해서 `노드를 찾아서 이동해야 한다는 단점`을 가지고 있습니다.
이러한 단점을 해소하고자 B+Tree는 `같은 레벨의 모든 키 값들이 정렬`되어 있고, `같은 레벨의 Sibling node는 연결 리스트 형태`로 이어져 있습니다.
(같은 레벨의 Sibling node는 모두 연결되어 있어서 키 값이 중복되지 않습니다!)

특정 값을 찾아야 하는 상황이 된다면 leaf node에 모든 자료들이 존재하고, 그 자료들이 연결 리스트로 연결되어 있으므로 `탐색에 있어서 매우 유리`합니다.

여기서 leaf node가 아닌 node는 Index Node라고 부르고, leaf node는 Data Node라고 부릅니다.

`Index Node`의 Value 값에는 다음 노드를 가리키는 `포인터 주소가 존재`합니다.
`Data Node`의 Value 값에는 우리가 원하는 `데이터가 존재`합니다.

그렇기 때문에 `Key 값은 중복`될 수 있고, 우리가 원하는 데이터를 찾기 위해서는 `반드시 leaf node까지 내려가야 한다`는 특징을 가지고 있습니다.

> 오늘날 데이터베이스에서 가장 중요한 것은 검색 속도이기 때문에 대부분의 데이터베이스는 `B-Tree의 노드를 찾아서 이동해야 한다는 단점을 보완한` B+Tree의 구조를 사용하고 있습니다.

## 내부 동작 방식
B+Tree가 DB index를 위한 자료구조로 적합한 이유를 정리해보면 다음과 같습니다.
1. 항상 `정렬된 상태`를 유지하여 `부등호 연산`에 유리하다.
2. 데이터 탐색뿐 아니라, 저장, 수정, 삭제에도 `항상 O(logN)의 시간 복잡도`를 갖는다.

<br>

![B+Tree First Status](/TIL/DB/img/B%2Btree%20First%20Status.png)

먼저 위 사진을 살펴보면 `학생(Student) 테이블의 이름(name) 컬럼에 인덱스`를 걸었을 때 확인할 수 있는 B+Tree 구조입니다.  
`모든 데이터는 정렬`되어 있으며, B+Tree의 특징에 맞게 leaf node는 연결 리스트로 이어져 있음을 확인할 수 있습니다.   
뿐만 아니라 `Key 값이 중복되고 있음을 확인`할 수 있는데요, level 1에 있는 "박현지"는 level 3에 있고 그 외에도 level 2에 있는 Key 값은 level 3에서 다시 등장하는 것을 확인할 수 있습니다.

그럼 하나의 예시를 통해 값을 어떻게 찾아 나가는지 확인해보도록 하겠습니다.

<br>

![B+Tree query](/TIL/DB/img/B%2Btree%20query.png)

우리는 "배준석"보다 사전적으로 이름이 큰 모든 학생을 찾고 싶다는 `SELECT * FROM Student WHERE 이름 >= "배준석";` 쿼리를 실행했다고 가정해보겠습니다.

1. B+Tree의 root node를 확인합니다.
2. root node에는 `"박현지"라는 search key가 존재`하기 때문에 `"배준석"이랑 비교`를 합니다.
3. `"배준석"의 값이 더 크기 때문에 우측 노드로 이동`합니다.
4. 다음 노드에서 또 `비교를 수행`합니다. 이번에는 "정재현" search key와 "배준석"을 비교합니다.
5. 이번에는 `"배준석"의 값이 더 작기 때문에 왼쪽 노드로 이동`합니다.
6. 그럼 leaf node에 도달한 것을 확인할 수 있습니다.
7. 이동한 leaf node의 첫 번째인 "박현지"를 시작으로 데이터를 비교하면서 우측으로 이동합니다.
8. 여기서 우리가 원하는 "배준석"과 일치하는 값을 찾았다면 해당 위치에 연결되어 있는 레코드를 찾아서 테이블에 해당하는 row를 찾으면 됩니다.
9. 여기서 끝이 아니라 `"배준석" 보다 큰 모든 학생`이기 때문에 값을 더 찾아야 합니다.
10. 여기서 B+Tree의 장점이 발휘됩니다.
    - B+Tree의 `모든 데이터는 정렬`되어 있습니다.
    - leaf node는 `연결 리스트로 이어져` 있습니다.
11. 위 특징에 따라 "배준석"보다 큰 학생은 오른쪽에 위치하고 있을 것이며(이미 정렬되어 있기 때문에), 연결 리스트로 이어져 있기 때문에 다음 node로 쉽게 이동할 수 있습니다.
12. 그래서 "배준석"보다 큰 학생에 대한 값도 leaf node를 이동하면서 그 값을 빠르게 찾을 수 있습니다.

<br>

추가로 나올 수 있는 면접 질문 정리!  
> Q. 데이터를 검색할 때 Hash Table의 시간 복잡도는 O(1)이고 B+Tree의 경우 O(logN)으로 더 느린데 왜 index는 hash table이 아닌 B+Tree를 사용하는 것일까?
>> A. Hash Table을 사용하면 `하나의 데이터를 탐색`하는 시간은 O(1)으로 B+Tree보다 빠르지만, `값이 정렬되어 있지 않기 때문에` `부등호를 사용하는 query`에 대해서는 `매우 비효율적`이게 되어 `데이터를 정렬해서 저장하는 B+Tree를 이용`합니다!

<br>

출처 - https://www.inflearn.com/course/%EA%B0%9C%EB%B0%9C%EC%9E%90-%EC%A0%84%EA%B3%B5%EB%A9%B4%EC%A0%91-cs-%EC%99%84%EC%A0%84%EC%A0%95%EB%B3%B5#curriculum (섹션 5. 데이터베이스 - 내부 동작 방식)  
출처 - https://www.youtube.com/watch?v=edpYzFgHbqs (Index의 특징)   
출처 - [서적] 한번에 이해되는 Oracle SQL & PL/SQL:24가지 미션으로 실무 능력을 키운다! (Index의 장단점)   
출처 - https://ssocoit.tistory.com/217 (B-Tree, B*Tree, B+Tree)
