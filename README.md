# SogogiGame

## 게임 방법 

1. Game start를 클릭
2. 총 8개의 고기를 5초 안에 알맞게 구워, 올린다. 알맞게 구워지면 점수 (+ 500)
3. 5초이상 고기를 불판에 둘경우, 살짝 탄 고기 -> 다 탄 고기 순으로 바뀌게 된다
4. 45초가 지나면 timeout으로 게임종료 OR 2000점 획득 후 게임 종료됨

## 주요 기능
💻 고기 하나하나에 Thread를 주어 불판 위에 올려지기 시작한 이후로 count를 세준다.
💻 불판에 올려질때, 치익 소리가 난다
💻 불판에 올려질때, Lottie를 사용하여 연기 효과를 주었다.

## 아쉬운 점 & 아직 해결 못한 점
👹 게임 랭킹 생성 (shared preference를 사용해서 하고싶었는데 죽었다깨나도 안됐다)
👹 view들이 겹쳤을때, 우선순위? (키가 제대로 먹지 않을때가 있었다)를 좀더 고려해봐야할 것 같다.
👹 고기 하나의 thread가 불판위에서만 count가 ++ 되어야하는데, count가 ++이 불판이 아닌 곳에서도 count가 막 세져서 고기가 탄다
👹 좀 더 똑같은 예쁜 UI

<img width="80%" src="https://user-images.githubusercontent.com/78431728/154843038-c17d4441-5b0c-407a-a0d7-cfdcfd13862a.png"/>
<img width="80%" src="https://user-images.githubusercontent.com/78431728/154843095-fa38a89a-d8c3-443a-8c9d-3afc99654b36.gif"/>
