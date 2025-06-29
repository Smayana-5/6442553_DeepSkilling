-- Exercise 3: Stored Procedures

-- Scenario 1: Process monthly interest for all savings accounts
-- Applies 1% interest rate to all savings accounts

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest
AS
    v_updated_count NUMBER := 0;
BEGIN
    -- Update balance for all savings accounts by applying 1% interest
    UPDATE Accounts 
    SET Balance = Balance * 1.01,
        LastModified = SYSDATE
    WHERE UPPER(AccountType) = 'SAVINGS';
    
    v_updated_count := SQL%ROWCOUNT;
    
    -- Commit the transaction
    COMMIT;
    
    -- Display result
    DBMS_OUTPUT.PUT_LINE('Monthly interest processed successfully.');
    DBMS_OUTPUT.PUT_LINE('Number of savings accounts updated: ' || v_updated_count);
    
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error processing monthly interest: ' || SQLERRM);
        RAISE;
END ProcessMonthlyInterest;
/

-- Scenario 2: Update employee bonus based on department
-- Note: Since Employees table is empty, this procedure is ready for when data exists

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus(
    p_department IN VARCHAR2,
    p_bonus_percentage IN NUMBER
)
AS
    v_updated_count NUMBER := 0;
BEGIN
    -- Validate input parameters
    IF p_bonus_percentage < 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Bonus percentage cannot be negative');
    END IF;
    
    IF p_department IS NULL THEN
        RAISE_APPLICATION_ERROR(-20002, 'Department cannot be null');
    END IF;
    
    -- Update salary by adding bonus percentage
    UPDATE Employees 
    SET Salary = Salary * (1 + p_bonus_percentage / 100)
    WHERE UPPER(Department) = UPPER(p_department);
    
    v_updated_count := SQL%ROWCOUNT;
    
    -- Commit the transaction
    COMMIT;
    
    -- Display result
    DBMS_OUTPUT.PUT_LINE('Employee bonus updated successfully.');
    DBMS_OUTPUT.PUT_LINE('Department: ' || p_department);
    DBMS_OUTPUT.PUT_LINE('Bonus percentage: ' || p_bonus_percentage || '%');
    DBMS_OUTPUT.PUT_LINE('Number of employees updated: ' || v_updated_count);
    
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error updating employee bonus: ' || SQLERRM);
        RAISE;
END UpdateEmployeeBonus;
/

-- Scenario 3: Transfer funds between accounts
-- Transfers specified amount from source to destination account

CREATE OR REPLACE PROCEDURE TransferFunds(
    p_source_account_id IN NUMBER,
    p_dest_account_id IN NUMBER,
    p_amount IN NUMBER
)
AS
    v_source_balance NUMBER;
    v_dest_balance NUMBER;
    v_source_exists NUMBER := 0;
    v_dest_exists NUMBER := 0;
BEGIN
    -- Validate input parameters
    IF p_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Transfer amount must be positive');
    END IF;
    
    IF p_source_account_id = p_dest_account_id THEN
        RAISE_APPLICATION_ERROR(-20004, 'Source and destination accounts cannot be the same');
    END IF;
    
    -- Check if source account exists and get current balance
    BEGIN
        SELECT Balance INTO v_source_balance 
        FROM Accounts 
        WHERE AccountID = p_source_account_id;
        v_source_exists := 1;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20005, 'Source account not found');
    END;
    
    -- Check if destination account exists
    BEGIN
        SELECT Balance INTO v_dest_balance 
        FROM Accounts 
        WHERE AccountID = p_dest_account_id;
        v_dest_exists := 1;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20006, 'Destination account not found');
    END;
    
    -- Check if source account has sufficient balance
    IF v_source_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20007, 'Insufficient balance in source account. Available: ' || v_source_balance);
    END IF;
    
    -- Perform the transfer
    -- Deduct from source account
    UPDATE Accounts 
    SET Balance = Balance - p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_source_account_id;
    
    -- Add to destination account
    UPDATE Accounts 
    SET Balance = Balance + p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_dest_account_id;
    
    -- Insert transaction records (if Transactions table has data)
    BEGIN
        -- Get next transaction ID
        INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
        VALUES (
            (SELECT NVL(MAX(TransactionID), 0) + 1 FROM Transactions), 
            p_source_account_id, 
            SYSDATE, 
            p_amount, 
            'Withdrawal'
        );
        
        INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
        VALUES (
            (SELECT NVL(MAX(TransactionID), 0) + 1 FROM Transactions), 
            p_dest_account_id, 
            SYSDATE, 
            p_amount, 
            'Deposit'
        );
    EXCEPTION
        WHEN OTHERS THEN
            -- If transaction logging fails, still complete the transfer
            DBMS_OUTPUT.PUT_LINE('Warning: Could not log transaction details');
    END;
    
    -- Commit the transaction
    COMMIT;
    
    -- Display success message
    DBMS_OUTPUT.PUT_LINE('Fund transfer completed successfully.');
    DBMS_OUTPUT.PUT_LINE('Amount transferred: $' || p_amount);
    DBMS_OUTPUT.PUT_LINE('From Account ID: ' || p_source_account_id);
    DBMS_OUTPUT.PUT_LINE('To Account ID: ' || p_dest_account_id);
    DBMS_OUTPUT.PUT_LINE('New source balance: $' || (v_source_balance - p_amount));
    DBMS_OUTPUT.PUT_LINE('New destination balance: $' || (v_dest_balance + p_amount));
    
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error during fund transfer: ' || SQLERRM);
        RAISE;
END TransferFunds;
/

-- Test scripts to demonstrate the procedures
-- Enable DBMS_OUTPUT to see results
SET SERVEROUTPUT ON;

-- Test 1: Process Monthly Interest
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing ProcessMonthlyInterest ===');
    ProcessMonthlyInterest;
END;
/

-- Test 2: Update Employee Bonus (will show 0 updates since table is empty)
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing UpdateEmployeeBonus ===');
    UpdateEmployeeBonus('IT', 10); -- 10% bonus for IT department
END;
/

-- Test 3: Transfer Funds (using sample data account IDs)
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing TransferFunds ===');
    TransferFunds(1, 2, 100); -- Transfer $100 from Account 1 to Account 2
END;
/

-- Query to verify the results
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Current Account Balances ===');
END;
/

SELECT AccountID, CustomerID, AccountType, Balance, LastModified 
FROM Accounts 
ORDER BY AccountID;