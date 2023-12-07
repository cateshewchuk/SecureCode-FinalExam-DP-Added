INSERT INTO USER2 (userid, username, password) VALUES (100, 'jmensink', 'baseball');
INSERT INTO USER2 (userid, username, password) VALUES (101, 'kpopke', 'shorebreak');
INSERT INTO USER2 (userid, username, password) VALUES (102, 'dgilmour', 'guitar');
INSERT INTO USER2 (userid, username, password) VALUES (103, 'agoodman', 'soccer');

INSERT INTO ITEM (itemid, userid, name, description) VALUES (1, 100, 'Baseball', 'Mint condition - signed in 1991 by Nord Feldman.');
INSERT INTO ITEM (itemid, userid, name, description) VALUES (2, 100, '1920 Florin', 'Mint condition - English coin from 1920.');
INSERT INTO ITEM (itemid, userid, name, description) VALUES (3, 101, '7 foot 4 inch Surfboard', 'Brand: WRV - three fins.');
INSERT INTO ITEM (itemid, userid, name, description) VALUES (4, 101, '8 foot 2 inch Surfboard', 'Brand: 17th Street - one fin.');
INSERT INTO ITEM (itemid, userid, name, description) VALUES (5, 102, 'Black Strat', '1969 Fender Stratocaster.');
INSERT INTO ITEM (itemid, userid, name, description) VALUES (6, 103, 'Canon XL1', 'Poor condition - but still works.');
INSERT INTO ITEM (itemid, userid, name, description) VALUES (7, 103, 'Arri SR16mm', 'Good condition.');

ALTER TABLE USER2 ALTER COLUMN userid RESTART WITH 110;
ALTER TABLE ITEM ALTER COLUMN itemid RESTART WITH 10;

