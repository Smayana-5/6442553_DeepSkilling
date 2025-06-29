-- Exercise 2: Error Handling

-- First, create an error log table for logging errors
-- Drop existing table and sequence if they exist
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ErrorLog CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE ErrorLog_Seq';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

-- Create ErrorLog table with identity column
CREATE TABLE ErrorLog (
    LogID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ErrorDate DATE DEFAULT SYSDATE,
    ProcedureName VARCHAR2(100),
    ErrorMessage VARCHAR2(4000),
    UserName VARCHAR2(100) DEFAULT USER
);

-- Scenario 1: Safe fund transfer with comprehensive error handling
CREATE OR REPLACE PROCEDURE SafeTransferFunds(
    p_source_account_id IN NUMBER,
    p_dest_account_id IN NUMBER,
    p_amount IN NUMBER
)
AS
    v_source_balance NUMBER;
    v_dest_balance NUMBER;
    v_error_msg VARCHAR2(4000);
    
    -- Custom exceptions
    insufficient_funds EXCEPTION;
    invalid_amount EXCEPTION;
    same_account EXCEPTION;
    account_not_found EXCEPTION;
    
BEGIN
    -- Input validation
    IF p_amount <= 0 THEN
        RAISE invalid_amount;
    END IF;
    
    IF p_source_account_id = p_dest_account_id THEN
        RAISE same_account;
    END IF;
    
    -- Check source account exists and get balance
    BEGIN
        SELECT Balance INTO v_source_balance 
        FROM Accounts 
        WHERE AccountID = p_source_account_id
        FOR UPDATE; -- Lock the row to prevent concurrent modifications
        
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE account_not_found;
    END;
    
    -- Check destination account exists
    BEGIN
        SELECT Balance INTO v_dest_balance 
        FROM Accounts 
        WHERE AccountID = p_dest_account_id
        FOR UPDATE; -- Lock the row
        
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            v_error_msg := 'Destination account ID ' || p_dest_account_id || ' not found';
            RAISE account_not_found;
    END;
    
    -- Check sufficient funds
    IF v_source_balance < p_amount THEN
        RAISE insufficient_funds;
    END IF;
    
    -- Perform the transfer
    UPDATE Accounts 
    SET Balance = Balance - p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_source_account_id;
    
    UPDATE Accounts 
    SET Balance = Balance + p_amount,
        LastModified = SYSDATE
    WHERE AccountID = p_dest_account_id;
    
    -- Log transaction records
    BEGIN
        INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
        VALUES (
            (SELECT NVL(MAX(TransactionID), 0) + 1 FROM Transactions), 
            p_source_account_id, 
            SYSDATE, 
            p_amount, 
            'Transfer-Out'
        );
        
        INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
        VALUES (
            (SELECT NVL(MAX(TransactionID), 0) + 1 FROM Transactions), 
            p_dest_account_id, 
            SYSDATE, 
            p_amount, 
            'Transfer-In'
        );
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Warning: Transaction logging failed but transfer completed');
    END;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transfer completed successfully: $' || p_amount || 
                        ' from Account ' || p_source_account_id || 
                        ' to Account ' || p_dest_account_id);

EXCEPTION
    WHEN invalid_amount THEN
        ROLLBACK;
        v_error_msg := 'Invalid amount: Transfer amount must be positive';
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('SafeTransferFunds', v_error_msg);
        COMMIT; -- Commit the error log
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN same_account THEN
        ROLLBACK;
        v_error_msg := 'Invalid transfer: Source and destination accounts cannot be the same';
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('SafeTransferFunds', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN account_not_found THEN
        ROLLBACK;
        v_error_msg := NVL(v_error_msg, 'Source account ID ' || p_source_account_id || ' not found');
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('SafeTransferFunds', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN insufficient_funds THEN
        ROLLBACK;
        v_error_msg := 'Insufficient funds: Available balance 
END SafeTransferFunds;
/

-- Scenario 2: Update employee salary with error handling
CREATE OR REPLACE PROCEDURE UpdateSalary(
    p_employee_id IN NUMBER,
    p_percentage_increase IN NUMBER
)
AS
    v_current_salary NUMBER;
    v_new_salary NUMBER;
    v_error_msg VARCHAR2(4000);
    
    -- Custom exceptions
    invalid_percentage EXCEPTION;
    employee_not_found EXCEPTION;
    
BEGIN
    -- Input validation
    IF p_percentage_increase < 0 THEN
        RAISE invalid_percentage;
    END IF;
    
    -- Check if employee exists and get current salary
    BEGIN
        SELECT Salary INTO v_current_salary 
        FROM Employees 
        WHERE EmployeeID = p_employee_id;
        
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE employee_not_found;
    END;
    
    -- Calculate new salary
    v_new_salary := v_current_salary * (1 + p_percentage_increase / 100);
    
    -- Update salary
    UPDATE Employees 
    SET Salary = v_new_salary
    WHERE EmployeeID = p_employee_id;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Salary updated successfully for Employee ID: ' || p_employee_id);
    DBMS_OUTPUT.PUT_LINE('Previous salary: $' || v_current_salary);
    DBMS_OUTPUT.PUT_LINE('New salary: $' || v_new_salary);
    DBMS_OUTPUT.PUT_LINE('Increase: ' || p_percentage_increase || '%');

EXCEPTION
    WHEN invalid_percentage THEN
        ROLLBACK;
        v_error_msg := 'Invalid percentage: Salary increase percentage cannot be negative';
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('UpdateSalary', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN employee_not_found THEN
        ROLLBACK;
        v_error_msg := 'Employee not found: Employee ID ' || p_employee_id || ' does not exist';
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('UpdateSalary', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN OTHERS THEN
        ROLLBACK;
        v_error_msg := 'Unexpected error in UpdateSalary: ' || SQLERRM;
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('UpdateSalary', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        RAISE;
END UpdateSalary;
/

-- Scenario 3: Add new customer with duplicate handling
CREATE OR REPLACE PROCEDURE AddNewCustomer(
    p_customer_id IN NUMBER,
    p_name IN VARCHAR2,
    p_dob IN DATE,
    p_initial_balance IN NUMBER DEFAULT 0
)
AS
    v_existing_count NUMBER;
    v_error_msg VARCHAR2(4000);
    
    -- Custom exceptions
    duplicate_customer EXCEPTION;
    invalid_data EXCEPTION;
    
BEGIN
    -- Input validation
    IF p_name IS NULL OR LENGTH(TRIM(p_name)) = 0 THEN
        RAISE invalid_data;
    END IF;
    
    IF p_initial_balance < 0 THEN
        RAISE invalid_data;
    END IF;
    
    -- Check for duplicate customer ID
    SELECT COUNT(*) INTO v_existing_count 
    FROM Customers 
    WHERE CustomerID = p_customer_id;
    
    IF v_existing_count > 0 THEN
        RAISE duplicate_customer;
    END IF;
    
    -- Insert new customer
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (p_customer_id, p_name, p_dob, p_initial_balance, SYSDATE);
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Customer added successfully:');
    DBMS_OUTPUT.PUT_LINE('Customer ID: ' || p_customer_id);
    DBMS_OUTPUT.PUT_LINE('Name: ' || p_name);
    DBMS_OUTPUT.PUT_LINE('Date of Birth: ' || TO_CHAR(p_dob, 'DD-MON-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Initial Balance: $' || p_initial_balance);

EXCEPTION
    WHEN duplicate_customer THEN
        ROLLBACK;
        v_error_msg := 'Duplicate customer: Customer ID ' || p_customer_id || ' already exists';
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('AddNewCustomer', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN invalid_data THEN
        ROLLBACK;
        v_error_msg := 'Invalid data: Customer name cannot be empty and balance cannot be negative';
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('AddNewCustomer', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN OTHERS THEN
        ROLLBACK;
        v_error_msg := 'Unexpected error in AddNewCustomer: ' || SQLERRM;
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('AddNewCustomer', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        RAISE;
END AddNewCustomer;
/

-- Test scripts for all procedures
SET SERVEROUTPUT ON;

-- Test 1: SafeTransferFunds - Valid transfer
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing SafeTransferFunds - Valid Transfer ===');
    SafeTransferFunds(1, 2, 50);
END;
/

-- Test 2: SafeTransferFunds - Insufficient funds
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing SafeTransferFunds - Insufficient Funds ===');
    SafeTransferFunds(1, 2, 10000); -- Trying to transfer more than available
END;
/

-- Test 3: SafeTransferFunds - Invalid account
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing SafeTransferFunds - Invalid Account ===');
    SafeTransferFunds(999, 2, 100); -- Non-existent source account
END;
/

-- Test 4: UpdateSalary - Employee not found (since Employees table is empty)
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing UpdateSalary - Employee Not Found ===');
    UpdateSalary(999, 10); -- Non-existent employee
END;
/

-- Test 5: AddNewCustomer - Valid customer
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing AddNewCustomer - Valid Customer ===');
    AddNewCustomer(3, 'Alice Williams', TO_DATE('1985-12-25', 'YYYY-MM-DD'), 2000);
END;
/

-- Test 6: AddNewCustomer - Duplicate customer
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing AddNewCustomer - Duplicate Customer ===');
    AddNewCustomer(1, 'Duplicate John', TO_DATE('1990-01-01', 'YYYY-MM-DD'), 1000); -- Customer ID 1 already exists
END;
/

-- Test 7: AddNewCustomer - Invalid data
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing AddNewCustomer - Invalid Data ===');
    AddNewCustomer(4, '', TO_DATE('1990-01-01', 'YYYY-MM-DD'), -500); -- Empty name and negative balance
END;
/

-- View error logs
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Error Log Entries ===');
END;
/

SELECT LogID, ErrorDate, ProcedureName, ErrorMessage 
FROM ErrorLog 
ORDER BY LogID; || v_source_balance || 
                      ', requested 
END SafeTransferFunds;
/

-- Scenario 2: Update employee salary with error handling
CREATE OR REPLACE PROCEDURE UpdateSalary(
    p_employee_id IN NUMBER,
    p_percentage_increase IN NUMBER
)
AS
    v_current_salary NUMBER;
    v_new_salary NUMBER;
    v_error_msg VARCHAR2(4000);
    
    -- Custom exceptions
    invalid_percentage EXCEPTION;
    employee_not_found EXCEPTION;
    
BEGIN
    -- Input validation
    IF p_percentage_increase < 0 THEN
        RAISE invalid_percentage;
    END IF;
    
    -- Check if employee exists and get current salary
    BEGIN
        SELECT Salary INTO v_current_salary 
        FROM Employees 
        WHERE EmployeeID = p_employee_id;
        
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE employee_not_found;
    END;
    
    -- Calculate new salary
    v_new_salary := v_current_salary * (1 + p_percentage_increase / 100);
    
    -- Update salary
    UPDATE Employees 
    SET Salary = v_new_salary
    WHERE EmployeeID = p_employee_id;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Salary updated successfully for Employee ID: ' || p_employee_id);
    DBMS_OUTPUT.PUT_LINE('Previous salary: $' || v_current_salary);
    DBMS_OUTPUT.PUT_LINE('New salary: $' || v_new_salary);
    DBMS_OUTPUT.PUT_LINE('Increase: ' || p_percentage_increase || '%');

EXCEPTION
    WHEN invalid_percentage THEN
        ROLLBACK;
        v_error_msg := 'Invalid percentage: Salary increase percentage cannot be negative';
        INSERT INTO ErrorLog (LogID, ProcedureName, ErrorMessage)
        VALUES (ErrorLog_Seq.NEXTVAL, 'UpdateSalary', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN employee_not_found THEN
        ROLLBACK;
        v_error_msg := 'Employee not found: Employee ID ' || p_employee_id || ' does not exist';
        INSERT INTO ErrorLog (LogID, ProcedureName, ErrorMessage)
        VALUES (ErrorLog_Seq.NEXTVAL, 'UpdateSalary', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN OTHERS THEN
        ROLLBACK;
        v_error_msg := 'Unexpected error in UpdateSalary: ' || SQLERRM;
        INSERT INTO ErrorLog (LogID, ProcedureName, ErrorMessage)
        VALUES (ErrorLog_Seq.NEXTVAL, 'UpdateSalary', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        RAISE;
END UpdateSalary;
/

-- Scenario 3: Add new customer with duplicate handling
CREATE OR REPLACE PROCEDURE AddNewCustomer(
    p_customer_id IN NUMBER,
    p_name IN VARCHAR2,
    p_dob IN DATE,
    p_initial_balance IN NUMBER DEFAULT 0
)
AS
    v_existing_count NUMBER;
    v_error_msg VARCHAR2(4000);
    
    -- Custom exceptions
    duplicate_customer EXCEPTION;
    invalid_data EXCEPTION;
    
BEGIN
    -- Input validation
    IF p_name IS NULL OR LENGTH(TRIM(p_name)) = 0 THEN
        RAISE invalid_data;
    END IF;
    
    IF p_initial_balance < 0 THEN
        RAISE invalid_data;
    END IF;
    
    -- Check for duplicate customer ID
    SELECT COUNT(*) INTO v_existing_count 
    FROM Customers 
    WHERE CustomerID = p_customer_id;
    
    IF v_existing_count > 0 THEN
        RAISE duplicate_customer;
    END IF;
    
    -- Insert new customer
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (p_customer_id, p_name, p_dob, p_initial_balance, SYSDATE);
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Customer added successfully:');
    DBMS_OUTPUT.PUT_LINE('Customer ID: ' || p_customer_id);
    DBMS_OUTPUT.PUT_LINE('Name: ' || p_name);
    DBMS_OUTPUT.PUT_LINE('Date of Birth: ' || TO_CHAR(p_dob, 'DD-MON-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Initial Balance: $' || p_initial_balance);

EXCEPTION
    WHEN duplicate_customer THEN
        ROLLBACK;
        v_error_msg := 'Duplicate customer: Customer ID ' || p_customer_id || ' already exists';
        INSERT INTO ErrorLog (LogID, ProcedureName, ErrorMessage)
        VALUES (ErrorLog_Seq.NEXTVAL, 'AddNewCustomer', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN invalid_data THEN
        ROLLBACK;
        v_error_msg := 'Invalid data: Customer name cannot be empty and balance cannot be negative';
        INSERT INTO ErrorLog (LogID, ProcedureName, ErrorMessage)
        VALUES (ErrorLog_Seq.NEXTVAL, 'AddNewCustomer', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN OTHERS THEN
        ROLLBACK;
        v_error_msg := 'Unexpected error in AddNewCustomer: ' || SQLERRM;
        INSERT INTO ErrorLog (LogID, ProcedureName, ErrorMessage)
        VALUES (ErrorLog_Seq.NEXTVAL, 'AddNewCustomer', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        RAISE;
END AddNewCustomer;
/

-- Test scripts for all procedures
SET SERVEROUTPUT ON;

-- Test 1: SafeTransferFunds - Valid transfer
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing SafeTransferFunds - Valid Transfer ===');
    SafeTransferFunds(1, 2, 50);
END;
/

-- Test 2: SafeTransferFunds - Insufficient funds
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing SafeTransferFunds - Insufficient Funds ===');
    SafeTransferFunds(1, 2, 10000); -- Trying to transfer more than available
END;
/

-- Test 3: SafeTransferFunds - Invalid account
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing SafeTransferFunds - Invalid Account ===');
    SafeTransferFunds(999, 2, 100); -- Non-existent source account
END;
/

-- Test 4: UpdateSalary - Employee not found (since Employees table is empty)
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing UpdateSalary - Employee Not Found ===');
    UpdateSalary(999, 10); -- Non-existent employee
END;
/

-- Test 5: AddNewCustomer - Valid customer
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing AddNewCustomer - Valid Customer ===');
    AddNewCustomer(3, 'Alice Williams', TO_DATE('1985-12-25', 'YYYY-MM-DD'), 2000);
END;
/

-- Test 6: AddNewCustomer - Duplicate customer
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing AddNewCustomer - Duplicate Customer ===');
    AddNewCustomer(1, 'Duplicate John', TO_DATE('1990-01-01', 'YYYY-MM-DD'), 1000); -- Customer ID 1 already exists
END;
/

-- Test 7: AddNewCustomer - Invalid data
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing AddNewCustomer - Invalid Data ===');
    AddNewCustomer(4, '', TO_DATE('1990-01-01', 'YYYY-MM-DD'), -500); -- Empty name and negative balance
END;
/

-- View error logs
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Error Log Entries ===');
END;
/

SELECT LogID, ErrorDate, ProcedureName, ErrorMessage 
FROM ErrorLog 
ORDER BY LogID; || p_amount;
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('SafeTransferFunds', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN OTHERS THEN
        ROLLBACK;
        v_error_msg := 'Unexpected error: ' || SQLERRM;
        INSERT INTO ErrorLog (ProcedureName, ErrorMessage)
        VALUES ('SafeTransferFunds', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        RAISE;
END SafeTransferFunds;
/

-- Scenario 2: Update employee salary with error handling
CREATE OR REPLACE PROCEDURE UpdateSalary(
    p_employee_id IN NUMBER,
    p_percentage_increase IN NUMBER
)
AS
    v_current_salary NUMBER;
    v_new_salary NUMBER;
    v_error_msg VARCHAR2(4000);
    
    -- Custom exceptions
    invalid_percentage EXCEPTION;
    employee_not_found EXCEPTION;
    
BEGIN
    -- Input validation
    IF p_percentage_increase < 0 THEN
        RAISE invalid_percentage;
    END IF;
    
    -- Check if employee exists and get current salary
    BEGIN
        SELECT Salary INTO v_current_salary 
        FROM Employees 
        WHERE EmployeeID = p_employee_id;
        
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE employee_not_found;
    END;
    
    -- Calculate new salary
    v_new_salary := v_current_salary * (1 + p_percentage_increase / 100);
    
    -- Update salary
    UPDATE Employees 
    SET Salary = v_new_salary
    WHERE EmployeeID = p_employee_id;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Salary updated successfully for Employee ID: ' || p_employee_id);
    DBMS_OUTPUT.PUT_LINE('Previous salary: $' || v_current_salary);
    DBMS_OUTPUT.PUT_LINE('New salary: $' || v_new_salary);
    DBMS_OUTPUT.PUT_LINE('Increase: ' || p_percentage_increase || '%');

EXCEPTION
    WHEN invalid_percentage THEN
        ROLLBACK;
        v_error_msg := 'Invalid percentage: Salary increase percentage cannot be negative';
        INSERT INTO ErrorLog (LogID, ProcedureName, ErrorMessage)
        VALUES (ErrorLog_Seq.NEXTVAL, 'UpdateSalary', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN employee_not_found THEN
        ROLLBACK;
        v_error_msg := 'Employee not found: Employee ID ' || p_employee_id || ' does not exist';
        INSERT INTO ErrorLog (LogID, ProcedureName, ErrorMessage)
        VALUES (ErrorLog_Seq.NEXTVAL, 'UpdateSalary', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN OTHERS THEN
        ROLLBACK;
        v_error_msg := 'Unexpected error in UpdateSalary: ' || SQLERRM;
        INSERT INTO ErrorLog (LogID, ProcedureName, ErrorMessage)
        VALUES (ErrorLog_Seq.NEXTVAL, 'UpdateSalary', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        RAISE;
END UpdateSalary;
/

-- Scenario 3: Add new customer with duplicate handling
CREATE OR REPLACE PROCEDURE AddNewCustomer(
    p_customer_id IN NUMBER,
    p_name IN VARCHAR2,
    p_dob IN DATE,
    p_initial_balance IN NUMBER DEFAULT 0
)
AS
    v_existing_count NUMBER;
    v_error_msg VARCHAR2(4000);
    
    -- Custom exceptions
    duplicate_customer EXCEPTION;
    invalid_data EXCEPTION;
    
BEGIN
    -- Input validation
    IF p_name IS NULL OR LENGTH(TRIM(p_name)) = 0 THEN
        RAISE invalid_data;
    END IF;
    
    IF p_initial_balance < 0 THEN
        RAISE invalid_data;
    END IF;
    
    -- Check for duplicate customer ID
    SELECT COUNT(*) INTO v_existing_count 
    FROM Customers 
    WHERE CustomerID = p_customer_id;
    
    IF v_existing_count > 0 THEN
        RAISE duplicate_customer;
    END IF;
    
    -- Insert new customer
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (p_customer_id, p_name, p_dob, p_initial_balance, SYSDATE);
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Customer added successfully:');
    DBMS_OUTPUT.PUT_LINE('Customer ID: ' || p_customer_id);
    DBMS_OUTPUT.PUT_LINE('Name: ' || p_name);
    DBMS_OUTPUT.PUT_LINE('Date of Birth: ' || TO_CHAR(p_dob, 'DD-MON-YYYY'));
    DBMS_OUTPUT.PUT_LINE('Initial Balance: $' || p_initial_balance);

EXCEPTION
    WHEN duplicate_customer THEN
        ROLLBACK;
        v_error_msg := 'Duplicate customer: Customer ID ' || p_customer_id || ' already exists';
        INSERT INTO ErrorLog (LogID, ProcedureName, ErrorMessage)
        VALUES (ErrorLog_Seq.NEXTVAL, 'AddNewCustomer', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN invalid_data THEN
        ROLLBACK;
        v_error_msg := 'Invalid data: Customer name cannot be empty and balance cannot be negative';
        INSERT INTO ErrorLog (LogID, ProcedureName, ErrorMessage)
        VALUES (ErrorLog_Seq.NEXTVAL, 'AddNewCustomer', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        
    WHEN OTHERS THEN
        ROLLBACK;
        v_error_msg := 'Unexpected error in AddNewCustomer: ' || SQLERRM;
        INSERT INTO ErrorLog (LogID, ProcedureName, ErrorMessage)
        VALUES (ErrorLog_Seq.NEXTVAL, 'AddNewCustomer', v_error_msg);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || v_error_msg);
        RAISE;
END AddNewCustomer;
/

-- Test scripts for all procedures
SET SERVEROUTPUT ON;

-- Test 1: SafeTransferFunds - Valid transfer
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing SafeTransferFunds - Valid Transfer ===');
    SafeTransferFunds(1, 2, 50);
END;
/

-- Test 2: SafeTransferFunds - Insufficient funds
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing SafeTransferFunds - Insufficient Funds ===');
    SafeTransferFunds(1, 2, 10000); -- Trying to transfer more than available
END;
/

-- Test 3: SafeTransferFunds - Invalid account
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing SafeTransferFunds - Invalid Account ===');
    SafeTransferFunds(999, 2, 100); -- Non-existent source account
END;
/

-- Test 4: UpdateSalary - Employee not found (since Employees table is empty)
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing UpdateSalary - Employee Not Found ===');
    UpdateSalary(999, 10); -- Non-existent employee
END;
/

-- Test 5: AddNewCustomer - Valid customer
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing AddNewCustomer - Valid Customer ===');
    AddNewCustomer(3, 'Alice Williams', TO_DATE('1985-12-25', 'YYYY-MM-DD'), 2000);
END;
/

-- Test 6: AddNewCustomer - Duplicate customer
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing AddNewCustomer - Duplicate Customer ===');
    AddNewCustomer(1, 'Duplicate John', TO_DATE('1990-01-01', 'YYYY-MM-DD'), 1000); -- Customer ID 1 already exists
END;
/

-- Test 7: AddNewCustomer - Invalid data
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Testing AddNewCustomer - Invalid Data ===');
    AddNewCustomer(4, '', TO_DATE('1990-01-01', 'YYYY-MM-DD'), -500); -- Empty name and negative balance
END;
/

-- View error logs
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Error Log Entries ===');
END;
/

SELECT LogID, ErrorDate, ProcedureName, ErrorMessage 
FROM ErrorLog 
ORDER BY LogID;