# 電子圖書館服務

使用者可以瀏覽數位內容、查看詳情、借閱、歸還，以及查看自己目前借閱中的書籍。

技術棧：Java 11、Spring Boot 2.7、Spring Data JPA、H2。

## 假設

- 數位內容用同時借閱席位限制，不模擬實體複本。每本書有 `licenseCount`，剩餘席位是 `availableSeats`。
- 不實作登入。借閱、歸還、我的借閱用請求標頭 `X-User-Id` 代表使用者。
- 借期 14 天。逾期仍可歸還。同一使用者對同一本書同時只能有一筆未歸還借閱。
- 電子書與期刊用 `contentType` 區分：`EBOOK`、`JOURNAL`。
- 未借閱時，詳情不回傳 `contentUrl`。目前有有效借閱才回傳。

## 架構

- `domain`：`Book`、`Loan` 與借閱規則，不依賴 Spring。
- `application`：瀏覽、借閱、歸還、查詢目前借閱。
- `infrastructure`：JPA 實體與倉儲映射。
- `api`：HTTP、DTO、統一錯誤回應。

借閱時鎖住該書的資料列，再扣減席位，避免同一席位被借走兩次。生產環境可以改成條件更新：`UPDATE ... SET available_seats = available_seats - 1 WHERE available_seats > 0`。

## 執行

```bash
mvn spring-boot:run
```

```bash
curl "http://localhost:8080/api/books?keyword=domain"
curl -H "X-User-Id: alice" -H "Content-Type: application/json" -d "{\"bookId\":\"book-ddd\"}" http://localhost:8080/api/loans
curl -H "X-User-Id: alice" http://localhost:8080/api/me/loans
curl -X POST -H "X-User-Id: alice" http://localhost:8080/api/loans/{loanId}/return
```

測試：

```bash
mvn test
```

## API

| 方法 | 路徑 | 說明 |
| --- | --- | --- |
| GET | `/api/books?keyword=&type=&page=&size=` | 瀏覽。`type` 為 `EBOOK` 或 `JOURNAL` |
| GET | `/api/books/{id}` | 詳情。帶 `X-User-Id` 且有有效借閱時才含 `contentUrl` |
| POST | `/api/loans` | 借閱。標頭 `X-User-Id`，本文 `{"bookId":"..."}` |
| POST | `/api/loans/{id}/return` | 歸還 |
| GET | `/api/me/loans` | 目前借閱 |

錯誤本文：

```json
{ "code": "NO_AVAILABLE_SEAT", "message": "No seat left for book book-clean" }
```

| code | HTTP |
| --- | --- |
| `BOOK_NOT_FOUND` / `LOAN_NOT_FOUND` | 404 |
| `ALREADY_BORROWED` / `NO_AVAILABLE_SEAT` / `LOAN_ALREADY_RETURNED` | 409 |
| `LOAN_OWNER_MISMATCH` | 403 |
| 缺少 `X-User-Id`、參數不合法 | 400 |
