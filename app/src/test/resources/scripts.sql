
INSERT INTO urls (name, created_at) VALUES ('https://example.com', '2024-05-02');
INSERT INTO urls (name, created_at) VALUES ('https://yandex.ru', '2023-06-08');

INSERT INTO url_checks (url_id, status_code, created_at) VALUES (1, 200, '2020-05-02');
INSERT INTO url_checks (url_id, status_code, created_at) VALUES (2, 300, '2010-05-02');
INSERT INTO url_checks (url_id, status_code, created_at) VALUES (1, 201, '2021-05-02');
INSERT INTO url_checks (url_id, status_code, created_at) VALUES (2, 400, '2011-05-02');
INSERT INTO url_checks (url_id, status_code, created_at) VALUES (2, 500, '2012-05-02');
INSERT INTO url_checks (url_id, status_code, created_at) VALUES (1, 202, '2022-05-02');
INSERT INTO url_checks (url_id, status_code, created_at) VALUES (1, 203, '2023-05-02');

SELECT url_id, status_code, created_at
FROM url_checks
WHERE url_id = 1
ORDER BY url_id, created_at DESC LIMIT 1;

SELECT c.url_id, c.status_code, MAX(c.created_at)
OVER (PARTITION BY c.url_id ORDER BY c.created_at DESC)
FROM url_checks AS c

// Выборка с последней записью для каждого адреса
SELECT DISTINCT ON (c.url_id)
	c.url_id, c.status_code, c.created_at
FROM url_checks AS c
ORDER BY c.url_id, c.created_at DESC;

// Рабочий вариант
WITH last_checks AS (
    SELECT DISTINCT ON (url_id)
        url_id, status_code, created_at
    FROM url_checks AS c
    ORDER BY url_id, created_at DESC
)
SELECT u.id, u.name, lc.created_at, lc.status_code
FROM urls AS u
LEFT JOIN last_checks AS lc
ON u.id = lc.url_id
ORDER BY u.id DESC