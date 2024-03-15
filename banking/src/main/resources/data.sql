drop ALIAS if exists TO_DATE; 
CREATE ALIAS TO_DATE as ' 
import java.text.*; 
@CODE 
java.util.Date toDate(String originalDate, String dateFormat) throws Exception { 
    return new SimpleDateFormat("dd/MM/yyyy").parse(originalDate); 
} 
'; 


INSERT INTO ACCOUNT(account_Id,customer_Id,current_Balance,account_Type,opening_date,owner_Name)
VALUES (1000000001,'CUSTOMER101',80000.00, 'Savings',TO_DATE('02/03/2024', 'DD/MM/YYYY'), 'Kamil');

INSERT INTO ACCOUNT(account_Id,customer_Id,current_Balance,account_Type,opening_date,owner_Name)
VALUES (1000000002, 'CUSTOMER102', 2000.00, 'Current', TO_DATE('02/03/2024', 'DD/MM/YYYY'),'Tabish');


INSERT INTO TRANSACTION(id, source_Account_Id,source_Owner_Name,target_Account_Id,target_Owner_Name,amount,initiation_Date,reference)
VALUES (1, 1000000001,'Kamil', 1000000002, 'Tabish', 100.00, '2024-03-04 10:30', 'transfer');



insert into appuser (userid,username,password,role) values ('EMPLOYEE101','employee','employee','EMPLOYEE');
insert into appuser (userid,username,password,role) values ('CUSTOMER101','Kamil','cust','CUSTOMER');
insert into appuser (userid,username,password,role) values ('CUSTOMER102','Tabish','cust','CUSTOMER');


insert into CUSTOMER_ENTITY (USERID, ADDRESS,DATE_OF_BIRTH, PAN, PASSWORD, USERNAME) values('CUSTOMER101','Bangalore','1995-08-04','ABCDE1234A','cust','Kamil');
insert into CUSTOMER_ENTITY (USERID, ADDRESS,DATE_OF_BIRTH, PAN, PASSWORD, USERNAME) values('CUSTOMER102','Delhi','1995-09-22','ABCDE1234C','cust','Tabish');


