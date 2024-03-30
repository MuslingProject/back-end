# Musling

<div align="center">
<img width="1000" alt="image" src="https://github.com/MuslingProject/back-end/assets/68382824/a1c4a374-7c29-42b5-ac3c-d0946dd996fe">

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https://github.com/MuslingProject/back-end&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

</div>


# Musling Application v1.0.0
**졸업 프로젝트** <br/>

## 🚀 배포 주소

> **백엔드 서버** : <br>

## 🖥️ 프로젝트 소개
음악과 감정의 연결고리를 탐색하는 감정 및 날씨 분석 기반의 개인화된 음악 추천 서비스입니다.
<br>

## 🕰️ 개발 기간
**2023-03-21 ~ 2023-12-14**


## 🧑‍🤝‍🧑 멤버구성
 - 팀원 : [오승미](https://github.com/seungmio) - 백엔드 개발
 - 팀원 : [이나경](https://github.com/nakyung128) - 프론트엔드 개발
 - 팀원 : [정희원](https://github.com/heehortus) - 인공지능 개발

---
<!--추가할 내용 의논-->
## Stacks 🐈

### 개발 환경
`Java 11` </br>
`IntelliJ` </br>
`Ubuntu 22.04` </br>
`Spring Boot 2.7.11` </br>
`Python` </br>
`Jupyter Notebook` </br>

### 사용 기술
`AWS EC2, RDS, S3` </br>
`JWT` </br>
`Spring Security` </br>
`MySQL` </br>
`JPA` </br>
`Flask` </br>
`ngrok` </br>

### 보안 기술
`JWT`  </br>

### 협업 도구
`Notion` </br>
`Discord` </br>
`Git` </br>

### 문서화 도구
`Postman`

---
<!--추가할 내용 의논-->
## 화면 구성 📺
| 메인 페이지  |  프로필 설정 페이지   |
| :-------------------------------------------: | :------------: |
|  <img width="329" src="https://github.com/MuslingProject/back-end/assets/68382824/5d761960-f737-490c-8e79-a59379cab49d"/> |  <img width="329" src="https://github.com/MuslingProject/back-end/assets/68382824/54503765-10a9-4c32-a376-7dae6779aa88"/>|  
| 기록 페이지  |  노래 추천 페이지   |
| <img width="329" src="https://github.com/MuslingProject/back-end/assets/68382824/a06866e8-e4e1-4577-9356-bf7082daaf2c"/>   |  <img width="329" src="https://github.com/MuslingProject/back-end/assets/68382824/37f52b29-da9a-4d21-9a32-81f8cee1bacf"/>     |
| 감정 달력 페이지  |  감정 통계 페이지   |
| <img width="329" src="https://github.com/MuslingProject/back-end/assets/68382824/0dbd3b65-79af-4619-a6e3-0298f015b9f6"/>   |  <img width="329" src="https://github.com/MuslingProject/back-end/assets/68382824/8bd5fd91-f263-4039-a2ee-d58697dede6a"/>     |
---
## 주요 기능 📦

### ⭐️ 사용자 프로필 설정
- 사용자는 구글 계정 연동을 통해 로그인할 수 있으며, 서비스 자체 계정 생성으로도 가입이 가능합니다.
- 회원가입 과정에서 사용자는 자신의 연령대와 선호하는 음악 장르를 선택할 수 있습니다.
- 선택한 연령대와 장르는 추천 음악 알고리즘에 반영되어, 사용자 맞춤형 음악 플레이리스트를 제공받게 됩니다.
- 주요 서비스 : **Google 로그인, 사용자 정보 추가입력, MyPage**
</br>

### ⭐️  일상 기록 감정 기반 음악 추천
- 사용자는 자신의 일상과 감정을 기록하고, 이를 기반으로 개인화된 음악 추천을 받습니다.
- 기록된 내용은 감정 분석 모델을 통해 처리되며, 분석 결과에 따라 사용자의 감정을 반영하거나 상반된 분위기의 음악이 추천됩니다.
- 사용자는 추천받은 음악을 좋아요 기능을 통해 관심 음악으로 저장하고, 추후에 쉽게 접근할 수 있습니다.
- 주요 서비스 : **일상 기록 기반 음악 추천, 관심 음악 저장 및 관리**
</br>

### ⭐️ 감정 달력
- 감정 달력 기능을 통해 사용자는 자신의 일상 기록과 감정 변화를 월간 달력 형태로 확인할 수 있습니다.
- 달력에는 각 날짜별로 사용자의 감정을 시각화한 이모지가 표시되어, 감정 변화를 한눈에 파악할 수 있습니다.
- 사용자는 특정 날짜를 선택하여 그날의 일상 기록과 연관된 음악 추천 목록을 확인할 수 있습니다.
- 주요 서비스 : **월간 감정 추적, 일상 기록 조회, 감정 통계 제공**
</br>

---
## 서비스 흐름도 📄
<div align="center">
<img width="600" alt="image" src="https://github.com/MuslingProject/back-end/assets/68382824/1275ce95-8812-43d2-85a9-17668594e579">
</div>

---
## 아키텍처 🗂️
<div align="center">
<img width="600" alt="image" src="https://github.com/MuslingProject/back-end/assets/68382824/2df5f479-f241-4005-a8ba-cfa9ae9e1bb7">
</div>
