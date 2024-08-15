# API 명세서

### 일정 등록
- **URL** : /api/schedules
- **Method** : POST
- **request** : body
- **response** : 등록 정보
### 일정 조회
#### 엔드포인트:
- **URL** : /api/schedules
- **Method** : GET
- **request** : param
- **response** : 전체 등록 정보
#### 쿼리 매개변수:
- **수정일** (date, optional) : 조건 수정일로 필터링. 형식[YYYY-MM-DD]
- **담당자명** (name, optional) : 조건 담당자명으로 필터링.
### 선택 일정 조회
- **URL** : /api/schedules/{id}
- **Method** : GET
- **request** : param
- **response** : 단건 응답 정보

### 일정 수정
- **URL** : /api/schedules
- **Method** : PUT
- **request** : param
- **response** : 수정 정보
### 일정 삭제
- **URL** : /api/schedules
- **Method** : DELETE
- **request** :param
- **response** : -