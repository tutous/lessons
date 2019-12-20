/* ----- COMPANY ----- */

DELETE FROM COMPANY;

INSERT INTO COMPANY (ID,NAME) VALUES
  (1, 'Firma 1'),
  (2, 'Firma 2');

/* ----- LOCATION ----- */

DELETE FROM LOCATION;

INSERT INTO LOCATION (ID, COMPANY_ID, CITY, STREET) VALUES
  (1, 1, '12345 Stadt 1', 'Strasse 1'),
  (2, 1, '23456 Stadt 2', 'Strasse 2'),
  (3, 2, '34567 Stadt 3', 'Strasse 3'),
  (4, 2, '45678 Stadt 4', 'Strasse 4');
  
/* ----- DEPARTMENT ----- */

DELETE FROM DEPARTMENT;

INSERT INTO DEPARTMENT (ID, COMPANY_ID, NAME) VALUES 
  (1, 1, 'DEP 1'),
  (2, 1, 'DEP 2'),
  (3, 2, 'DEP 3'),
  (4, 2, 'DEP 4');

/* ----- User ----- */

DELETE FROM USER;

INSERT INTO USER (ID, LAST_NAME, /* LOCATION_ID, DEPARTMENT_ID, */ FIRST_NAME, EMAIL, ENABLED, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON) VALUES 
  (1, /* 1, 1, */ 'Everton', 'Cliff', 'cliff@everton.de',TRUE,'SYSTEM',sysdate,null,null),
  (2, /* 1, 2,*/ 'Pritt', 'Peter', 'peter@pritt.de',TRUE,'SYSTEM',sysdate,null,null),
  (3, /* 2, 1, */ 'Conelli', 'Sorn', 'sorn@conelli.de',TRUE,'SYSTEM',sysdate,null,null);
  
/* ----- Role ----- */
DELETE FROM ROLE;

INSERT INTO ROLE (ID, NAME ) VALUES
  (1, 'DC_SUPERVISOR');
  
INSERT INTO ROLE (ID, NAME ) VALUES
  (2, 'DC_ASSIGNEE');
  
/* ----- User Role ----- */
DELETE FROM USER_ROLE;

INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES
  (1, 1),
  (2, 1),
  (3, 1);  