-----------SQL 실습과제 13

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


--6. 각 직원이 속한 부서별 평균 급여와 자신의 급여를 비교하여 급여의 차이를 계산하여 출력
-- 자신의 급여가 높으면 양수, 낮으면 음수로 표현
-- 출력 컬럼은 사번, 이름, 급여, 부서 평균 급여, 급여의 차이 급여의 차이는 반올림하여 정수로 출력
SELECT
    EMPLOYEE_ID,
    NAME,
    SALARY,
    ROUND(AVG(SALARY) OVER (PARTITION BY DEPARTMENT_ID)) AVG_SAL,
    ROUND(SALARY - AVG(SALARY) OVER (PARTITION BY DEPARTMENT_ID)) SALARY_DIFFERENCE 
FROM EMPLOYEES
WHERE SALARY IS NOT NULL;



--7. 이름이 'Employee 241'인 사원의 모든 상사들을 출력하세요
-- 출력데이터는 Employee 241 > Employee 179 > ... 형식이며 한 줄로 나타냅니다.
SELECT
    MAX(LTRIM(SYS_CONNECT_BY_PATH(NAME, ' > '),  '> ')) PATH
FROM EMPLOYEES
START WITH NAME = 'Employee 241'
CONNECT BY PRIOR MANAGER_ID = EMPLOYEE_ID;


--8. 사원 테이블에서 급여가 높은 상위 10명의 사원 정보를 출력
-- 급여가 동일한 경우 사원 이름 순으로 정렬하여 순위를 부여
SELECT *
FROM (
    SELECT
        NAME,
        SALARY,
        RANK() OVER(ORDER BY SALARY DESC NULLS LAST, NAME) RANK
    FROM EMPLOYEES
)
WHERE RANK <= 10;


--9. 입사 년도별 급여 합계를 출력하고 전체 급여 합계를 출력하세요.
-- 전체 급여 합계는 가장 아랫줄에 'ALL YEARS'로 표시
SELECT 
    NVL(TO_CHAR(HIRE_DATE, 'YYYY'), 'ALL YEARS') YEAR,
    SUM(SALARY) YEAR_SALARY
FROM EMPLOYEES
GROUP BY ROLLUP(TO_CHAR(HIRE_DATE, 'YYYY'))
ORDER BY YEAR;


--10. 부서별 급여 합계, 직무별 급여 합계, 그리고 전체 급여 합계를 출력
-- 부서 정보가 NULL 인 행은 제외하고 부서별, 직무별로 정렬하며 출력 컬럼은 부서명, 직무ID, 급여 합계
SELECT
    (
        SELECT DEPARTMENT_NAME
        FROM DEPARTMENTS
        WHERE DEPARTMENT_ID = E.DEPARTMENT_ID
    ) DEPARTMENT_NAME,
    JOB_ID,
    SUM(SALARY) SUM_SAL
FROM EMPLOYEES E
WHERE DEPARTMENT_ID IS NOT NULL
GROUP BY GROUPING SETS(DEPARTMENT_ID, JOB_ID, ())
ORDER BY DEPARTMENT_ID, JOB_ID;


-- 11. 각 직원의 급여와 해당 직원의 부서에서 그 직원의 급여보다 100 적은 급여부터 100 큰 급여까지에 해당하는 급여의 합계 계산
-- 출력 컬럼은 부서 ID, 사원이름, 급여, 계산한 급여 합계
SELECT
    DEPARTMENT_ID,
    NAME,
    SALARY,
    SUM(SALARY) OVER(PARTITION BY DEPARTMENT_ID ORDER BY SALARY RANGE BETWEEN 100 PRECEDING AND 100 FOLLOWING) SUM_SAL
FROM EMPLOYEES
ORDER BY DEPARTMENT_ID;


--12. 각 직무별 평균 급여와 각 직원의 급여를 비교하여 '직무 평균 이상' 또는 '직무 평균 이하' 여부 표시
-- 전체 평균 급여와의 비교 결과도 '전체 평균 이상' 또는 '전체 평균 이하'로 함께 표시
-- 출력 칼럼은 사번, 급여, 직무 평균 급여 비교, 전체 평균 비교
SELECT 
    EMPLOYEE_ID,
    SALARY,
    CASE
        WHEN SALARY >= AVG(SALARY) OVER (PARTITION BY JOB_ID) THEN '직무 평균 이상'
        ELSE '직무 평균 미만'
    END COMPARED_TO_JOB_AVG,
    CASE
        WHEN SALARY >= AVG(SALARY) OVER () THEN '전체 평균 이상'
        ELSE '전체 평균 미만'
    END COMPARED_TO_GLOBAL_AVG  
FROM EMPLOYEES
WHERE SALARY IS NOT NULL;
