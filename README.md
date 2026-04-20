# 🛒 E-Commerce REST API

쿠팡 스타일의 현대적인 이커머스 플랫폼 REST API

## 📋 목차
- [기술 스택](#기술-스택)
- [주요 기능](#주요-기능)
- [시작하기](#시작하기)
- [API 문서](#api-문서)
- [프로젝트 구조](#프로젝트-구조)
- [다음 단계](#다음-단계)

## 🚀 기술 스택

### Backend
- **Java 17**
- **Spring Boot 3.5.8**
- **Spring Security** - JWT 기반 인증
- **Spring Data JPA** - ORM
- **SQL Server** - 데이터베이스

### 문서화
- **Swagger/OpenAPI 3.0** - API 문서 자동 생성

### 빌드 도구
- **Gradle 8.x**

## ✨ 주요 기능

### 완료된 기능 ✅

#### 1. 인증 시스템
- JWT 기반 회원가입/로그인
- Access Token & Refresh Token
- 비밀번호 암호화 (BCrypt)
- 역할 기반 접근 제어 (ROLE_USER, ROLE_ADMIN)

#### 2. 상품 관리
- 상품 CRUD (관리자)
- 상품 목록 조회 (페이징)
- 상품 검색 (키워드, 카테고리, 가격)
- 상품 정렬 (가격, 평점, 판매량, 최신순)
- 조회수 자동 증가

#### 3. 도메인 모델
- **User** - 사용자 정보
- **Product** - 상품 정보
- **Category** - 카테고리 (계층 구조)
- **Order** - 주문 관리
- **OrderItem** - 주문 상품
- **Cart** - 장바구니
- **CartItem** - 장바구니 아이템
- **Review** - 리뷰 시스템
- **Address** - 배송지 관리

### 개발 예정 기능 🔜

#### 3단계: 장바구니 & 주문 시스템
- [ ] 장바구니 CRUD API
- [ ] 주문 생성 및 관리 API
- [ ] 재고 관리 (동시성 제어)
- [ ] 주문 상태 관리

#### 4단계: 결제 시스템
- [ ] 결제 API 연동 (토스페이먼츠)
- [ ] 결제 성공/실패 처리
- [ ] 환불 처리

#### 5단계: 리뷰 시스템
- [ ] 리뷰 CRUD API
- [ ] 평점 시스템
- [ ] 리뷰 이미지 업로드

#### 6단계: 성능 최적화
- [ ] Redis 캐싱
- [ ] 쿼리 최적화 (N+1 문제 해결)
- [ ] 인덱스 설계

## 🏁 시작하기

### 사전 요구사항
- Java 17 이상
- SQL Server
- Gradle 8.x

### 1. 데이터베이스 설정

SQL Server에서 데이터베이스를 생성합니다:

\`\`\`sql
CREATE DATABASE shop;
\`\`\`

### 2. 애플리케이션 설정

`src/main/resources/application.yaml` 파일에서 데이터베이스 연결 정보를 수정합니다:

\`\`\`yaml
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=shop;encrypt=false
    username: your_username
    password: your_password
\`\`\`

JWT 시크릿 키도 변경하세요:

\`\`\`yaml
jwt:
  secret: your-256-bit-secret-key-change-this-in-production
\`\`\`

### 3. 애플리케이션 실행

\`\`\`bash
# Gradle로 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
\`\`\`

서버가 `http://localhost:8080`에서 실행됩니다.

## 📚 API 문서

애플리케이션 실행 후 Swagger UI에 접속하세요:

**Swagger UI**: http://localhost:8080/swagger-ui.html

### 주요 API 엔드포인트

#### 인증 API
- `POST /api/auth/register` - 회원가입
- `POST /api/auth/login` - 로그인
- `POST /api/auth/refresh` - 토큰 갱신

#### 상품 API
- `GET /api/products` - 상품 목록 조회
- `GET /api/products/{id}` - 상품 상세 조회
- `GET /api/products/search` - 상품 검색
- `POST /api/products` - 상품 등록 (관리자)
- `PUT /api/products/{id}` - 상품 수정 (관리자)
- `DELETE /api/products/{id}` - 상품 삭제 (관리자)

### API 사용 예시

#### 1. 회원가입
\`\`\`bash
curl -X POST http://localhost:8080/api/auth/register \\
  -H "Content-Type: application/json" \\
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "name": "홍길동",
    "phone": "010-1234-5678"
  }'
\`\`\`

#### 2. 로그인
\`\`\`bash
curl -X POST http://localhost:8080/api/auth/login \\
  -H "Content-Type: application/json" \\
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
\`\`\`

응답:
\`\`\`json
{
  "success": true,
  "message": "로그인 성공",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "testuser"
  },
  "timestamp": "2026-04-20T10:30:00"
}
\`\`\`

#### 3. 상품 조회 (인증 필요)
\`\`\`bash
curl -X GET http://localhost:8080/api/products \\
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
\`\`\`

#### 4. 상품 검색
\`\`\`bash
curl -X GET "http://localhost:8080/api/products/search?keyword=노트북&sortBy=price&sortDirection=asc" \\
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
\`\`\`

## 📁 프로젝트 구조

\`\`\`
src/main/java/com/shop/ecommerce/
├── config/                    # 설정 클래스
│   ├── SecurityConfig.java    # Spring Security 설정
│   ├── JwtAuthenticationFilter.java  # JWT 필터
│   └── OpenApiConfig.java     # Swagger 설정
├── controller/                # REST API 컨트롤러
│   ├── AuthRestController.java
│   └── ProductRestController.java
├── dto/                       # 데이터 전송 객체
│   ├── ApiResponse.java
│   ├── ProductDto.java
│   ├── UserDto.java
│   ├── OrderDto.java
│   ├── CartDto.java
│   └── ReviewDto.java
├── entity/                    # JPA 엔티티
│   ├── User.java
│   ├── Category.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Review.java
│   └── Address.java
├── model/                     # 도메인 모델
│   └── Product.java
├── repository/                # JPA 리포지토리
│   ├── UserRepository.java
│   ├── ProductRepository.java
│   ├── CategoryRepository.java
│   ├── OrderRepository.java
│   ├── CartRepository.java
│   ├── ReviewRepository.java
│   └── AddressRepository.java
├── service/                   # 비즈니스 로직
│   ├── AuthService.java
│   ├── ProductService.java
│   └── CustomUserDetailsService.java
├── util/                      # 유틸리티
│   └── JwtUtil.java
├── exception/                 # 예외 처리
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
└── EcommerceApplication.java  # 메인 클래스
\`\`\`

## 🔐 보안

- **JWT 인증**: Bearer 토큰 기반 인증
- **비밀번호 암호화**: BCrypt 해싱
- **CORS 설정**: React 개발 서버 허용 (localhost:3000, localhost:5173)
- **역할 기반 접근 제어**: USER, ADMIN 역할

## 🎯 다음 단계

### 즉시 구현 가능
1. **장바구니 API** - CartRestController 구현
2. **주문 API** - OrderRestController 구현
3. **리뷰 API** - ReviewRestController 구현
4. **카테고리 API** - CategoryRestController 구현

### 중기 목표
1. **결제 시스템** - 토스페이먼츠 연동
2. **파일 업로드** - 상품 이미지, 리뷰 이미지
3. **Redis 캐싱** - 성능 최적화
4. **검색 고도화** - Elasticsearch 연동

### 장기 목표
1. **프론트엔드** - React + Next.js 개발
2. **모바일 앱** - React Native
3. **마이크로서비스** - 서비스 분리
4. **클라우드 배포** - AWS/Azure

## 📝 라이선스

이 프로젝트는 학습 목적으로 제작되었습니다.

## 👨‍💻 개발자

- **Developer**: Bang

## 🤝 기여

이슈와 PR은 언제나 환영합니다!

---

**Happy Coding! 🚀**
