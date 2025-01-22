-- 실습 12.

-- 사원 테이블에서 부서별로 급여의 합계를 계산하고 전체합계를 함께 출력
-- (단, 부서데이터가  NULL인 사원은 제외)
SELECT 
    NVL(TO_CHAR(DEPARTMENT_ID), '총합') DEPARMTNE_ID,
    SUM(SALARY) SUM_SAL
FROM EMPLOYEES
WHERE DEPARTMENT_ID IS NOT NULL
GROUP BY ROLLUP(DEPARTMENT_ID)
ORDER BY DEPARTMENT_ID;

-- 같은 날짜에 입사한 사원이 2명 이상인 날짜와 입사한 사원수, 마지막 행을 총 직원수를 함께 출력 하시오
SELECT
    NVL(TO_CHAR(HIRE_DATE), '총 직원수') HIRE_DATE,
    COUNT(EMPLOYEE_ID) EMP_CNT
FROM EMPLOYEES
GROUP BY ROLLUP(HIRE_DATE)
HAVING COUNT(EMPLOYEE_ID) >= 2
ORDER BY HIRE_DATE;


-- 각 지점별 대출 금액 합계와 모든 지점의 총합계를 출력
-- 출력 컬럼은 지점명, 대출 금액 합계이며 지점 총합계는 'All Branch'로 출력
-- 지점명으로 정렬하되 총합계는 맨 아랫줄에 나타내시오
SELECT NVL(BRANCH_NAME, 'All Branch') BRANCH_NAME, SUM_AMT
FROM (
    SELECT
        (
            SELECT NAME
            FROM BRANCHES
            WHERE L.BRANCH_ID = BRANCH_ID
        ) BRANCH_NAME,
        SUM(AMOUNT) SUM_AMT
    FROM LOANS L
    GROUP BY ROLLUP(BRANCH_ID)
    ORDER BY BRANCH_NAME NULLS LAST
);
