-- 고객 테이블에서 특정주소('Address 100')을 가진 고객의 이름과 전화번호 출력
SELECT NAME, PHONE FROM CUSTOMERS
WHERE ADDRESS = 'Address 100';

-- 계좌 테이블에서 잔액이 50,000이상인 계좌의 계좌ID와 잔액을 출력
SELECT ACCOUNT_ID, BALANCE FROM ACCOUNTS
WHERE BALANCE >= 50000;

-- 거래 테이블에서 거래 금액이 음수인 거래의 ID와 금액을 출력
SELECT TRANSACTION_ID, AMOUNT FROM TRANSACTIONS
WHERE AMOUNT < 0;

-- 대출 테이블에서 상태가 "APPROVED"인 대출의 대출 금액과 고객ID를 출력
SELECT AMOUNT, CUSTOMER_ID FROM LOANS
WHERE STATUS = 'APPROVED';
