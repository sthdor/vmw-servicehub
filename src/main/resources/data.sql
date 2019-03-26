USE vmw01_service_subscription;

INSERT INTO user(name)VALUES('u1');
INSERT INTO user(name)VALUES('u2');
INSERT INTO user(name)VALUES('u3');

INSERT INTO service(name, url)VALUES('search', 'repo/service/fulltextserach');
INSERT INTO service(name, url)VALUES('lcache', 'repo/service/localcache');
INSERT INTO service(name, url)VALUES('dcache', 'repo/service/distributedcache');
INSERT INTO service(name, url)VALUES('auth', 'repo/service/authorization');
INSERT INTO service(name, url)VALUES('risk', 'repo/service/frauddetect');

