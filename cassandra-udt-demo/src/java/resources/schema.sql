CREATE KEYSPACE wdxxl WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 1 };

CREATE TYPE wdxxl.address (
  street text,
  city text,
  zip_code int,
  phones set<text>
);

CREATE TYPE wdxxl.fullname (
  firstname text,
  lastname text
);

CREATE TABLE wdxxl.users (
  id uuid PRIMARY KEY,
  name frozen <fullname>,
  direct_reports set<frozen <fullname>>,     // a collection set
  addresses map<text, frozen <address>>     // a collection map
);

INSERT INTO wdxxl.users (id, name) VALUES (62c36092-82a1-3a00-93d1-46196ee77204, {firstname: 'Marie-Claude', lastname: 'Josset'});
UPDATE wdxxl.users SET addresses = addresses + {'home': { street: '191 Rue St. Charles', city: 'Paris', zip_code: 75015, phones: {'33 6 78 90 12 34'}}} WHERE id=62c36092-82a1-3a00-93d1-46196ee77204;
SELECT name FROM wdxxl.users WHERE id=62c36092-82a1-3a00-93d1-46196ee77204;
SELECT name.lastname FROM wdxxl.users WHERE id=62c36092-82a1-3a00-93d1-46196ee77204;
CREATE INDEX on wdxxl.users (name);
SELECT id FROM wdxxl.users WHERE name = {firstname: 'Marie-Claude', lastname: 'Josset'};
UPDATE wdxxl.users SET direct_reports = { ( 'Naoko', 'Murai'), ( 'Sompom', 'Peh') } WHERE id=62c36092-82a1-3a00-93d1-46196ee77204;
INSERT INTO wdxxl.users (id, direct_reports) VALUES ( 7db1a490-5878-11e2-bcfd-0800200c9a66, { ('Jeiranan', 'Thongnopneua') } );
SELECT direct_reports FROM wdxxl.users;
