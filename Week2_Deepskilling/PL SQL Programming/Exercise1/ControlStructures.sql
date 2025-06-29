-- =====================================================
-- Exercise 1: Control Structures - Complete Solutions
-- =====================================================
CREATE TABLE Customers (
    CustomerID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    DOB DATE,
    Balance NUMBER,
    LastModified DATE
);

CREATE TABLE Accounts (
    AccountID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    AccountType VARCHAR2(20),
    Balance NUMBER,
    LastModified DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Transactions (
    TransactionID NUMBER PRIMARY KEY,
    AccountID NUMBER,
    TransactionDate DATE,
    Amount NUMBER,
    TransactionType VARCHAR2(10),
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)
);

CREATE TABLE Loans (
    LoanID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    LoanAmount NUMBER,
    InterestRate NUMBER,
    StartDate DATE,
    EndDate DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Employees (
    EmployeeID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    Position VARCHAR2(50),
    Salary NUMBER,
    Department VARCHAR2(50),
    HireDate DATE
);

-- Insert sample data
INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (1, 'John Doe', TO_DATE('1963-05-15', 'YYYY-MM-DD'), 15000, SYSDATE);

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (2, 'Jane Smith', TO_DATE('1990-07-20', 'YYYY-MM-DD'), 8000, SYSDATE);

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (3, 'Bob Wilson', TO_DATE('1955-03-10', 'YYYY-MM-DD'), 12000, SYSDATE);

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (4, 'Alice Brown', TO_DATE('1975-09-25', 'YYYY-MM-DD'), 5000, SYSDATE);

INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (1, 1, 5000, 6.5, SYSDATE-100, SYSDATE+200);

INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (2, 3, 8000, 7.0, SYSDATE-50, SYSDATE+25);

INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (3, 2, 3000, 5.5, SYSDATE-30, SYSDATE+400);

INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (4, 4, 10000, 6.8, SYSDATE-200, SYSDATE+15);

COMMIT;

-- =====================================================
-- SCENARIO 1: Apply discount to loan interest rates for customers above 60
-- =====================================================

DECLARE
    v_customer_age NUMBER;
    v_customer_name VARCHAR2(100);
    v_loan_count NUMBER := 0;
    v_updated_count NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== LOAN INTEREST DISCOUNT FOR SENIOR CUSTOMERS ===');
    DBMS_OUTPUT.PUT_LINE('Processing customers above 60 years old...');
    DBMS_OUTPUT.PUT_LINE('');
    
    -- Loop through all customers
    FOR customer_rec IN (SELECT CustomerID, Name, DOB FROM Customers) LOOP
        -- Calculate customer age
        v_customer_age := FLOOR(MONTHS_BETWEEN(SYSDATE, customer_rec.DOB) / 12);
        v_customer_name := customer_rec.Name;
        
        DBMS_OUTPUT.PUT_LINE('Customer: ' || v_customer_name || ' (Age: ' || v_customer_age || ')');
        
        -- Check if customer is above 60
        IF v_customer_age > 60 THEN
            DBMS_OUTPUT.PUT_LINE('  -> Eligible for senior discount!');
            
            -- Apply 1% discount to all loans for this customer
            FOR loan_rec IN (SELECT LoanID, InterestRate FROM Loans WHERE CustomerID = customer_rec.CustomerID) LOOP
                v_loan_count := v_loan_count + 1;
                
                DBMS_OUTPUT.PUT_LINE('  -> Loan ID ' || loan_rec.LoanID || 
                                   ' - Old Rate: ' || loan_rec.InterestRate || '%');
                
                -- Apply 1% discount
                UPDATE Loans 
                SET InterestRate = InterestRate - 1
                WHERE LoanID = loan_rec.LoanID;
                
                v_updated_count := v_updated_count + 1;
                
                DBMS_OUTPUT.PUT_LINE('  -> Loan ID ' || loan_rec.LoanID || 
                                   ' - New Rate: ' || (loan_rec.InterestRate - 1) || '%');
            END LOOP;
        ELSE
            DBMS_OUTPUT.PUT_LINE('  -> Not eligible for senior discount');
        END IF;
        
        DBMS_OUTPUT.PUT_LINE('');
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE('Summary: Updated ' || v_updated_count || ' loans for senior customers');
    COMMIT;
END;
/

-- =====================================================
-- SCENARIO 2: Set VIP status for customers with balance over $10,000
-- =====================================================

-- First, add IsVIP column to Customers table
ALTER TABLE Customers ADD (IsVIP CHAR(1) DEFAULT 'N');

DECLARE
    v_vip_count NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== VIP STATUS UPDATE ===');
    DBMS_OUTPUT.PUT_LINE('Processing customers for VIP status...');
    DBMS_OUTPUT.PUT_LINE('');
    
    -- Loop through all customers
    FOR customer_rec IN (SELECT CustomerID, Name, Balance FROM Customers) LOOP
        DBMS_OUTPUT.PUT_LINE('Customer: ' || customer_rec.Name || 
                           ' (Balance: $' || customer_rec.Balance || ')');
        
        -- Check if balance is over $10,000
        IF customer_rec.Balance > 10000 THEN
            -- Set VIP flag to TRUE (Y)
            UPDATE Customers 
            SET IsVIP = 'Y' 
            WHERE CustomerID = customer_rec.CustomerID;
            
            v_vip_count := v_vip_count + 1;
            DBMS_OUTPUT.PUT_LINE('  -> Status: VIP CUSTOMER!');
        ELSE
            -- Set VIP flag to FALSE (N)
            UPDATE Customers 
            SET IsVIP = 'N' 
            WHERE CustomerID = customer_rec.CustomerID;
            
            DBMS_OUTPUT.PUT_LINE('  -> Status: Regular Customer');
        END IF;
        
        DBMS_OUTPUT.PUT_LINE('');
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE('Summary: ' || v_vip_count || ' customers promoted to VIP status');
    COMMIT;
END;
/

-- =====================================================
-- SCENARIO 3: Send reminders for loans due within next 30 days
-- =====================================================

DECLARE
    v_reminder_count NUMBER := 0;
    v_days_remaining NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== LOAN PAYMENT REMINDERS ===');
    DBMS_OUTPUT.PUT_LINE('Checking for loans due within next 30 days...');
    DBMS_OUTPUT.PUT_LINE('');
    
    -- Loop through all loans due in next 30 days
    FOR loan_rec IN (
        SELECT l.LoanID, l.CustomerID, l.LoanAmount, l.InterestRate, 
               l.EndDate, c.Name, c.CustomerID as CustID
        FROM Loans l
        JOIN Customers c ON l.CustomerID = c.CustomerID
        WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30
        ORDER BY l.EndDate
    ) LOOP
        v_days_remaining := FLOOR(loan_rec.EndDate - SYSDATE);
        v_reminder_count := v_reminder_count + 1;
        
        DBMS_OUTPUT.PUT_LINE('REMINDER #' || v_reminder_count);
        DBMS_OUTPUT.PUT_LINE('Customer: ' || loan_rec.Name || ' (ID: ' || loan_rec.CustomerID || ')');
        DBMS_OUTPUT.PUT_LINE('Loan ID: ' || loan_rec.LoanID);
        DBMS_OUTPUT.PUT_LINE('Loan Amount: $' || loan_rec.LoanAmount);
        DBMS_OUTPUT.PUT_LINE('Interest Rate: ' || loan_rec.InterestRate || '%');
        DBMS_OUTPUT.PUT_LINE('Due Date: ' || TO_CHAR(loan_rec.EndDate, 'DD-MON-YYYY'));
        DBMS_OUTPUT.PUT_LINE('Days Remaining: ' || v_days_remaining);
        
        -- Priority based on days remaining
        IF v_days_remaining <= 7 THEN
            DBMS_OUTPUT.PUT_LINE('URGENT: Payment due within a week!');
        ELSIF v_days_remaining <= 15 THEN
            DBMS_OUTPUT.PUT_LINE('WARNING: Payment due soon!');
        ELSE
            DBMS_OUTPUT.PUT_LINE('NOTICE: Payment due within 30 days');
        END IF;
        
        DBMS_OUTPUT.PUT_LINE('Please contact the bank to arrange payment.');
        DBMS_OUTPUT.PUT_LINE('----------------------------------------');
    END LOOP;
    
    IF v_reminder_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No loans are due within the next 30 days.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Total reminders sent: ' || v_reminder_count);
    END IF;
END;
/

-- =====================================================
-- VERIFICATION QUERIES
-- =====================================================

-- Check updated loan interest rates
SELECT l.LoanID, c.Name, c.DOB, 
       FLOOR(MONTHS_BETWEEN(SYSDATE, c.DOB) / 12) as Age,
       l.InterestRate
FROM Loans l
JOIN Customers c ON l.CustomerID = c.CustomerID
ORDER BY c.DOB;

-- Check VIP status
SELECT CustomerID, Name, Balance, IsVIP,
       CASE WHEN IsVIP = 'Y' THEN 'VIP Customer' 
            ELSE 'Regular Customer' END as Status
FROM Customers
ORDER BY Balance DESC;

-- Check loans due in next 30 days
SELECT l.LoanID, c.Name, l.LoanAmount, l.EndDate,
       FLOOR(l.EndDate - SYSDATE) as DaysRemaining
FROM Loans l
JOIN Customers c ON l.CustomerID = c.CustomerID
WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30
ORDER BY l.EndDate;