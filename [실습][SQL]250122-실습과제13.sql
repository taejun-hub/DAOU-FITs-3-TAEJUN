--SQL 실습 과제

--1. 지점별 승인된 대출의 총 금액을 계산한 뒤 
-- 해당 금액이 전체 지점에서 승인된 대출 금액의 평균보다 낮은 지점의 지점명과 승인된 대출의 총 금액, 전체 지점에서 승인된 대출 금액의 평균을 출력
-- 평균값은 반올림하여 소수점 아래 둘째자리까지 표현합니다.
SELECT 
    (
        SELECT NAME
        FROM BRANCHES B
        WHERE B.BRANCH_ID = T.BRANCH_ID
    ) BRANCH_NAME,
    SUM_BRANCH,
    AVG_TOTAL
FROM (
    SELECT
        DISTINCT BRANCH_ID,
        SUM(AMOUNT) OVER (PARTITION BY BRANCH_ID) SUM_BRANCH,
        ROUND(AVG(AMOUNT) OVER (), 2) AVG_TOTAL
    FROM LOANS
    WHERE STATUS = 'APPROVED'
) T
WHERE SUM_BRANCH < AVG_TOTAL;


--2. 모든 직원의 사번과 이름을 출력하되, 각 직원의 상사의 이름과 상사가 속한 부서 이름도 함께 출력
-- 상사가 없는 직원은 'NO MANAGER'로 표시하고, 상사가 속한 부서가 없을 경우 'NO DEPARTMENT'로 표시
SELECT
    E1.EMPLOYEE_ID,
    E1.NAME EMPLOY_NAME,
    NVL(E2.NAME, 'NO MANAGER') MANAGER_NAME,
    NVL((
        SELECT DEPARTMENT_NAME
        FROM DEPARTMENTS
        WHERE DEPARTMENT_ID = E2.DEPARTMENT_ID
    ), 'NO DEPARTMENT') M_DEPARTMENT_NAME
FROM EMPLOYEES E1
LEFT JOIN EMPLOYEES E2
ON E1.MANAGER_ID = E2.EMPLOYEE_ID;


--3. 각 지원의 급여와 그 급여가 전체 직원 급여에서 차지하는 백분율을 계산
-- 출력 컬럼은 사번, 이름, 급여, 백분율이며 백분율은 반올림하여 소수점 아래 둘째자리까지 표현
-- 급여가 없는 지원들은 제외, 백분율로 내림차순
SELECT
    EMPLOYEE_ID,
    NAME,
    SALARY,
    ROUND(SALARY/(SUM(SALARY) OVER()) * 100, 2) PERCENT
FROM EMPLOYEES
WHERE SALARY IS NOT NULL
ORDER BY PERCENT DESC;

--4. 각 계좌의 최고 거래 금액이 높은 순으로 상위 5위까지의 계좌를 출력하세요.
-- 동일 순위가 존재할 경우 각각의 순위를 모두 출력하고 출력 컬럼은 계좌 ID, 최고 거래금액
SELECT *
FROM (
    SELECT 
        ACCOUNT_ID,
        MAX(AMOUNT) MAX_AMOUNT,
        DENSE_RANK () OVER (ORDER BY MAX(AMOUNT) DESC) DENSE_RANK
    FROM TRANSACTIONS
    GROUP BY ACCOUNT_ID
)
WHERE DENSE_RANK <= 5;


--5. 각 부서에서 급여가 가장 높은 직원의 급여와 가장 낮은 직원의 급여 차이를 계산하여 출력
-- 부서 ID로 오름차순 정렬
SELECT
    DEPARTMENT_ID,
    MAX(SALARY) - MIN(SALARY)
FROM EMPLOYEES
GROUP BY DEPARTMENT_ID
ORDER BY DEPARTMENT_ID;
